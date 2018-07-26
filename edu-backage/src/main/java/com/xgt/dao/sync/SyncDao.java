package com.xgt.dao.sync;

import com.xgt.base.DaoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.xgt.constant.SystemConstant.SPOT;

/**
 *  同步视频 dao
 * @author liuao
 * @date 2018/7/18 10:38
 */
@Repository
public class SyncDao {

    private static final String  name_space = "syncRecord" + SPOT;

    @Autowired
    private DaoClient daoClient;


    /**
     *  根据同步类型，查询出，原数据表中，开始同步的id
     * @author liuao
     * @date 2018/7/18 14:18
     */
    public Integer queryPreviousId(String syncType){
        String sqlId = name_space + "queryPreviousId";
        Map<String,Object> param = new HashMap<>();
        param.put("syncType", syncType);
        return daoClient.queryForObject(sqlId, param, Integer.class);
    }

    /**
     *  判断该种类型的同步信息是否存在
     * @author liuao
     * @date 2018/7/18 15:16
     */
    public boolean queryExsitBySyncType(String syncType){
        String sqlId = name_space + "queryExsitBySyncType";
        Map<String,Object> param = new HashMap<>();
        param.put("syncType", syncType);
        int count =  daoClient.queryForObject(sqlId, param, Integer.class);
        return count > 0 ? true : false ;
    }

    /**
     *  根据同步类型更新同步结束时的 id
     * @author liuao
     * @date 2018/7/18 15:24
     */
    public int updateEndIdBySyncType(String syncType, Integer endId){
        String sqlId = name_space + "updateEndIdBySyncType";
        Map<String,Object> param = new HashMap<>();
        param.put("syncType", syncType);
        param.put("endId", endId);
        return daoClient.excute(sqlId, param);
    }

    /**
     *  保存 同步记录
     * @author liuao
     * @date 2018/7/18 15:28
     */
    public int saveSyncRecord(String syncType, Integer startId ,Integer endId){
        String sqlId = name_space + "saveSyncRecord";
        Map<String,Object> param = new HashMap<>();
        param.put("syncType", syncType);
        param.put("startId", startId);
        param.put("endId", endId);
        return daoClient.excute(sqlId, param);
    }

}
