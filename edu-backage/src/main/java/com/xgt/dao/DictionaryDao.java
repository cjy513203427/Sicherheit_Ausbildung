package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.Dictionary;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description
 * @create 2018-03-30 19:05
 **/
@Repository
public class DictionaryDao implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryDao.class);

    private static Map<String,Object> dictionaryMap = new HashMap<>();

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "dictionary.";



    public List<Dictionary> queryAll(){
        String sqlId = NAMESPACE + "queryAll";
        return daoClient.queryForList(sqlId ,null ,Dictionary.class );
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        logger.info(".............字典初始化 start............");
        List<Dictionary> dictionaryList = queryAll();
        if (CollectionUtils.isNotEmpty(dictionaryList)){
            logger.info(".............字典初始化数量：{}............",dictionaryList.size());
            for (Dictionary dictionary : dictionaryList ) {
                String typeCode = dictionary.getTypeCode();
                String value = dictionary.getValue();
                dictionaryMap.put(typeCode + value, dictionary);

                // 为下拉框查字典
                Object codeDicList =  dictionaryMap.get(typeCode);
                if(null == codeDicList){
                    codeDicList = new ArrayList<>();
                }
                List<Dictionary> dictList = (List<Dictionary>)codeDicList ;

                dictList.add(dictionary);
                dictionaryMap.put(typeCode, dictList);
            }
        }
    }
    /**
     *  根据key 值获取 字典信息
     *  这里的key :   字段表中的 type_code 和  value 两个字段拼接
     * @author liuao
     * @date 2018/4/1 10:54
     */
    public static Dictionary getDictionary(String key ){
        return (Dictionary)dictionaryMap.get(key);
    }

    /**
     *  根据key 值获取 字典值对应的文本
     *  比如  ：字典表中配置的性别 （1：女 2：男）
     *          这里根据key 值，返回 "男" 或  "女"
     * @author liuao
     * @date 2018/4/1 10:55
     */
    public static String getDictionaryText(String key ){
        Dictionary  dictionary = (Dictionary) dictionaryMap.get(key);
        String text = StringUtils.EMPTY;
        if (null != dictionary) {
            text = dictionary.getText();
        }
        return text ;
    }

    public Integer selectDictionaryCount(Dictionary dictionary) {
        String sqlId = NAMESPACE + "selectDictionaryCount" ;
        Long total = daoClient.queryForObject(sqlId,dictionary,Long.class);
        return total.intValue();
    }

    public List<Dictionary> selectDictionaryList(Dictionary dictionary) {
        String sqlId = NAMESPACE + "selectDictionaryList" ;
        return daoClient.queryForList(sqlId,dictionary,Dictionary.class) ;
    }

    /**
     *   查询出某个类型的字典
     * @author liuao
     * @date 2018/4/9 20:27
     */
    public List<Dictionary> selectDictionaryListByType(String typeCode) {
        String sqlId = NAMESPACE + "selectDictionaryListByType" ;
        Map<String,Object> param = new HashMap<>();
        param.put("typeCode",typeCode);
        return daoClient.queryForList(sqlId,param, Dictionary.class) ;
    }

    /**
     * @Description 添加字典
     * @author Joanne
     * @create 2018/4/11 10:24
     */
    public void addDictionary(Dictionary dictionary) {
        String sqlId = NAMESPACE + "addDictionary";
        daoClient.insertAndGetId(sqlId,dictionary);
        try {
            afterPropertiesSet();
        }catch (Exception e){
            logger.info("缓存失败");
        }
    }

    public void modifyDictionary(Dictionary dictionary) {
        String sqlId = NAMESPACE + "modifyDictionary" ;
        daoClient.excute(sqlId,dictionary);
        try {
            afterPropertiesSet();
        }catch (Exception e){
            logger.info("缓存失败");
        }
    }

    /**
     * @Description 根据字典值和字典code查询字典信息
     * @author Joanne
     * @create 2018/5/13 20:17
     */
    public Dictionary queryDicInfoByCodeAndValue(String code, String value) {
        String sqlId = NAMESPACE + "queryDicInfoByCodeAndValue";
        Map params = new HashMap();
        params.put("code",code);
        params.put("value",value);
        return daoClient.queryForObject(sqlId,params,Dictionary.class);
    }

    public List<Dictionary> getProvinceList() {
        String sqlId = NAMESPACE + "getProvinceList";
        return daoClient.queryForList(sqlId,null, Dictionary.class) ;
    }

    public List<Dictionary> getCityList(String provinceText) {
        String sqlId = NAMESPACE + "getCityList";
        Map params = new HashMap();
        params.put("provinceText",provinceText);
        return daoClient.queryForList(sqlId,params, Dictionary.class) ;
    }
}
