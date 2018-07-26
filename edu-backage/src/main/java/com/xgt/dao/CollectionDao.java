package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.BuildLabourer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 5618 on 2018/6/1.
 */
@Repository
public class CollectionDao {

    private static final Logger logger = LoggerFactory.getLogger(CollectionDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "collection.";

    /**
     * 手机端添加收藏
     *
     * @param labourId
     * @param collectionType
     * @param referenceId
     */
    public int addCollection(Integer labourId, String collectionType, Integer referenceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourId", labourId);
        map.put("collectionType", collectionType);
        map.put("referenceId", referenceId);
        String sqlId = name_space + "addCollection";
        return daoClient.insertAndGetId(sqlId, map);
    }

    /**
     * 手机端是否已经收藏
     *
     * @param labourId
     * @param collectionType
     * @param referenceId
     */
    public boolean isExisting(Integer labourId, String collectionType, Integer referenceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourId", labourId);
        map.put("collectionType", collectionType);
        map.put("referenceId", referenceId);
        String sqlId = name_space + "isExisting";
        Integer count = daoClient.queryForObject(sqlId, map, Integer.class);
        return count.compareTo(0) > 0 ? true : false ;
    }

    /**
     * 手机端 - 删除收藏
     * 收藏类型（1:视频、2：图文、3：试题）
     *
     * @param collectionType
     * @param referenceId
     * @return
     */
    public void deleteCollection(Integer labourId, String collectionType, Integer referenceId) {
        String sqlId = name_space + "deleteCollection";

        Map<String, Object> map = new HashMap<>();
        map.put("labourId", labourId);
        map.put("collectionType", collectionType);
        map.put("referenceId", referenceId);

        daoClient.excute(sqlId, map);
    }

    /**
     * @Description 根据外键和收藏类型删除收藏
     * @author Joanne
     * @create 2018/6/15 15:14
     */
    public void deleteCollectionByReferenceIdAndType(String ids, String collectType) {
        String sqlId = name_space + "deleteCollectionByReferenceIdAndType";
        Map<String, Object> map = new HashMap<>();
        map.put("referenceId", ids);
        map.put("collectionType", collectType);
        daoClient.excute(sqlId, map);
    }
}
