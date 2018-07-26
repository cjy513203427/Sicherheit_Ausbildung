package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.dto.DataStatisticsDto;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeLiu
 * @Description 数据统计Dao层
 * @date 2018/7/9 9:45
 */
@Repository
public class DataStatisticsDao {

    private static final Logger logger = LoggerFactory.getLogger(DataStatisticsDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "dataStatistics.";

    /**
     * @Description 培训系统PC版-查询数据统计
     * @author HeLiu
     * @date 2018/7/9 11:16
     */
    public List<DataStatisticsDto> queryDataStatistics(Integer companyId, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("status", status);
        String sqlId = name_space + "queryDataStatistics";
        return daoClient.queryForList(sqlId, map, DataStatisticsDto.class);
    }

    /**
     * @Description 培训系统PC版-查询总学时和培训人次
     * @author HeLiu
     * @date 2018/7/9 17:10
     */
    public Map<String, Object> queryTimeAndTrain(Integer companyId, String siteCode, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("siteCode", siteCode);
        map.put("examType", examType);
        String sqlId = name_space + "queryTimeAndTrain";
        return daoClient.queryForMap(sqlId, map);
    }
}
