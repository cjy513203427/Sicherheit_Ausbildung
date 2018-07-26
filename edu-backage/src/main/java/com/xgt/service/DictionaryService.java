package com.xgt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgt.dao.DictionaryDao;
import com.xgt.entity.Dictionary;
import com.xgt.util.JSONUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description
 * @create 2018-03-30 19:06
 **/
@Service
public class DictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    private DictionaryDao dictionaryDao ;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * @Description 查询字典列表及总数，便于前台分页
     * @author Joanne
     * @create 2018/4/11 10:22
     */
    public Map<String,Object> selectDictionaryList(Dictionary dictionary) {
        Integer total ;
        List<Dictionary> list = null;
        //查询字典总数
        total = dictionaryDao.selectDictionaryCount(dictionary);
        if (total!=null && total>0) {
            //获取字典列表
            list =dictionaryDao.selectDictionaryList(dictionary);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, Dictionary.class));
        map.put("total", total);
        return map;
    }

    public void addDictionary(Dictionary dictionary) {
        dictionaryDao.addDictionary(dictionary);
    }

    public void modifyDictionary(Dictionary dictionary) {
        dictionaryDao.modifyDictionary(dictionary);
    }

    /**
     * @Description  根据字典类型(字典编码)获取字典信息
     * @author Joanne
     * @create 2018/5/13 19:39
     */
    public List<Dictionary> queryDicInfo(String dicType) {
        return dictionaryDao.selectDictionaryListByType(dicType);
    }


    public Dictionary queryDicInfoByCodeAndValue(String code, String value) {
        return dictionaryDao.queryDicInfoByCodeAndValue(code,value);
    }

    public boolean savePreInfoDic(String data) {
        JSONArray preArr = JSONArray.parseArray(data);
        if(CollectionUtils.isEmpty(preArr)){
            return false ;
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    //todo 初始前的时候有的值为空需处理
                    for (int i=0 ; i<preArr.size() ; i++){
                        Dictionary dictionary = new Dictionary();
                        JSONObject dicJson =(JSONObject)preArr.get(i);
                        String param = dicJson.get("realname").toString();
                        String param1 = dicJson.get("param1").toString();
                        dictionary.setParam1(param.getBytes("GBK").length==param.length()?param:param1);
                        dictionary.setParam2(dicJson.get("param2").toString());
                        dictionary.setId((Integer) dicJson.get("id"));
                        dictionary.setSort((Integer) dicJson.get("sort"));
                        dictionaryDao.modifyDictionary(dictionary);
                    }
                }catch (Exception e){
                    logger.error("......modify dic wrong.......",e);
                    return false;
                }
                return true;
            }
        });
        return true ;
    }

    public List<Dictionary> getProvinceList() {
        return dictionaryDao.getProvinceList();
    }

    public List<Dictionary> getCityList(String provinceText) {
        return dictionaryDao.getCityList(provinceText);
    }
}
