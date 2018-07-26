package com.xgt.config.onebyone;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 *  分布式锁
 * 一个接一个业务处理模版默认实现<br>  
 *   
 * <p>  
 * 用一个业务处理表记录，在处理前对锁状态进行判断 ，判断逻辑参见{@link #beforeInvoke}方法<br>  
 *   
 * 业务处理表： 业务类型 PK|业务ID PK|方法|创建时间<br>  
 *   
 *   
 */  
@Service("redisOneByOneTemplate")
public class RedisOneByOneTemplate  {

    @Autowired
    private StringRedisTemplate redisTemplate ;



    /** logger */  
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisOneByOneTemplate.class);
      
    private static final String CACHE_PREFIX_KEY = "EPP:ONEBYONE:CACHE:";
      
    private static final Long REDIS_OPT_RES_FAIL = 0L;  
      
    /**  
     * 并发时循环等待的最大次数  
     */  
    private static final int REPEAT_MAX_COUNT = 5;  
  


    /**  
     * {@inheritDoc}  
     */  
    public <T> T execute(OneByOne oneByOne, CallBack<T> callBack) {  
        oneByOne.setDescription(oneByOne.getBizType() + "-" + oneByOne.getBizId() + "-" + oneByOne.getMethod());  
  
        try {  
            this.beforeInvoke(oneByOne);  
  
            return callBack.invoke();  
        } finally {  
            this.afterInvoke(oneByOne);  
        }  
    }  
      
    /**  
     * 查询所有的存在的OneByOne锁  
     * @return OneByOne锁列表  
     */  
//    @Override
    public List<String> queryAllOneByOne() {
        SetOperations<String, String> setOperation = redisTemplate.opsForSet();
        Set<String> values = setOperation.members(CACHE_PREFIX_KEY);
        return new ArrayList<String>(values);
    }  
      
    /**  
     * 清除所有的OneByOne锁  
     */  
//    @Override
    public void clearOneByOne(List<String> values) {
        SetOperations<String, String> setOperation = redisTemplate.opsForSet();
        if (CollectionUtils.isNotEmpty(values)) {
            for (String value : values) {
                setOperation.remove(CACHE_PREFIX_KEY, value);
            }  
        }  
    }  
  
    /**  
     * 回调前置  
     *   
     * @param oneByOne 一个接一个处理记录  
     */  
    private void beforeInvoke(final OneByOne oneByOne) {
        SetOperations<String, String> setOperation = redisTemplate.opsForSet();
        int count = 0;  
        do {  
            try {  
                oneByOne.setInsertSuccess(true);  
                  
                // 插入处理记录  
                Long addRes = setOperation.add(CACHE_PREFIX_KEY,
                        oneByOne.getBizType() + "|" + oneByOne.getBizId());  
                if (REDIS_OPT_RES_FAIL == addRes) {  
                    oneByOne.setInsertSuccess(false);  
                    LOGGER.info(oneByOne.getDescription() + "插入处理记录发生错误！");  
                }   
                  
            } catch (Throwable t) {  
                oneByOne.setInsertSuccess(false);  
                LOGGER.error(oneByOne.getDescription() + "插入处理记录发生异常！t:{}", t);  
            }  
              
            if (!oneByOne.isInsertSuccess()) {  
                  
                LOGGER.info(oneByOne.getDescription() + "插入处理记录失败！");  
                  
                // 重试次数累加  
                count++;  
                if (count >= REPEAT_MAX_COUNT) {  
                    // 如果插入失败，抛出AppException  
                    throw new RuntimeException( oneByOne.getDescription() + "业务正在处理中");
                }  
                  
                // 等待一段时间  
                try {  
                    // 每次等待时间拉长  
                    Thread.sleep(2000);  
                } catch (InterruptedException e) {  
                    LOGGER.info(oneByOne.getDescription() + " occur InterruptedException while wait.");  
                }  
            }  
        }  
        while (!oneByOne.isInsertSuccess());  
          
    }  
  
    /**  
     * 回调后置  
     *   
     * @param oneByOne 一个接一个处理记录  
     */  
    private void afterInvoke(final OneByOne oneByOne) {  
        // 插入失败，不删除处理记录  
        if (!oneByOne.isInsertSuccess()) {  
            return;  
        }
        SetOperations<String, String> setOperation = redisTemplate.opsForSet();
        try {  
            // 删除处理记录  
            Long res = setOperation.remove(CACHE_PREFIX_KEY,
                    oneByOne.getBizType() + "|" + oneByOne.getBizId());  
            if (res > 0) {  
                LOGGER.debug("Remove oneByOne success, the bizType is: {}, the bizId is: {}",   
                        oneByOne.getBizType(), oneByOne.getBizId());  
            } else {  
                LOGGER.debug("No value exist in redis, the bizType is: {}, the bizId is: {}",   
                        oneByOne.getBizType(), oneByOne.getBizId());  
            }  
              
        } catch (Throwable t) {  
            LOGGER.error(oneByOne.getDescription() + "删除处理记录失败！t:{}", t);  
        }  
    }  
}  