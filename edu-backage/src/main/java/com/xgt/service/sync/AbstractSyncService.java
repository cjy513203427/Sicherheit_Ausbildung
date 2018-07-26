package com.xgt.service.sync;

import com.xgt.dao.sync.SyncDao;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 同步抽象类
 *
 * @author liuao
 * @date 2018/7/18 11:17
 */
@Service
public abstract class AbstractSyncService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSyncService.class);
    @Autowired
    private SyncDao syncDao;

    /**
     * 安全教育 远程服务地址url
     *
     * @author liuao
     * @date 2018/7/18 20:31
     */
    @Value("${safety.edu.remote.url}")
    public String SAFETY_EDU_REMOTE_URL;

    // 阿里云文件地址前缀
    @Value("${aliyunOSS.imageAddress}")
    public String ALIYUNOSS_IMAGEADDRESS;

    //   安全教育 文件下载到本地路径
    @Value("${safety.edu.local.path}")
    public String SAFETY_EDU_LOCAL_PATH;


    /**
     * 获取同步的上一个id
     *
     * @author liuao
     * @date 2018/7/18 11:20
     */
    protected Integer queryPreviousId(String syncType) {
        return syncDao.queryPreviousId(syncType);
    }

    /**
     * 异常或者结束时，保存或者更新本次的同步记录
     *
     * @author liuao
     * @date 2018/7/18 11:39
     */
    protected void saveOrUpdateSyncRecord(Integer startId, Integer endId, String syncType) {
        boolean exsitFlag = syncDao.queryExsitBySyncType(syncType);
        // 如果存在该同步类型的 同步记录，则只更新endId ; 不存在，则插入该类型的同步记录
        if (exsitFlag) {
            syncDao.updateEndIdBySyncType(syncType, endId);
        } else {
            syncDao.saveSyncRecord(syncType, startId, endId);
        }
    }

    /**
     * 执行同步，同步中的业务逻辑，数据之间的同步先后关系，都在这里编写
     *
     * @author liuao
     * @date 2018/7/18 11:36
     */
    public void excuteSync(String syncType) {

        logger.info(".......start .excuteSync  ..syncType:{}..", syncType);

        // 获取开始id
        Integer previousId = queryPreviousId(syncType);

        // 每次都会执行方法，判断是否有需要同步的数据
        Pair<Boolean, Object> resultPair = exsitNeedSync(previousId);
        while (resultPair.getLeft()) {
            // 设置最已同步的id 为前一个id
            Integer syncEndId = previousId;
            try {
                // 同步数据，并返回结束时,本次同步数据的id,
                // 没有异常，则表示同步成功
                syncEndId = syncData(resultPair.getRight());
                logger.info(".......同步数据id:{}.. .excuteSync  ..syncType:{}..", syncEndId, syncType);
                // 同步成功 最新同步成功的id , 则变成上一个id
                previousId = syncEndId;

                resultPair = exsitNeedSync(previousId);
            } catch (Exception e) {
                logger.info(".excuteSync..excetption..previousId:{}...syncType.:{}", previousId, syncType);
                logger.error("excuteSync..excetption.", e);

            } finally {
                // 保存同步记录，
                // 每次同步成功一条数据，都需要更新最新已同步的id
                saveOrUpdateSyncRecord(previousId, syncEndId, syncType);
            }
        }
        logger.info(".......end .excuteSync  ..syncType:{}..", syncType);


    }

    /**
     * 根据同步开始id，同步数据, 返回结束时的id, 不同模块，实现不一样，这里抽象出来
     *
     * @author liuao
     * @date 2018/7/18 11:32
     */
    protected abstract Integer syncData(Object data) throws  Exception;

    /**
     * 根据同步id ，查询是否有需要同步的数据,true 表示有， false 表示没有
     *
     * @author liuao
     * @date 2018/7/18 16:21
     */
    protected abstract Pair<Boolean, Object> exsitNeedSync(Integer previousId);


}
