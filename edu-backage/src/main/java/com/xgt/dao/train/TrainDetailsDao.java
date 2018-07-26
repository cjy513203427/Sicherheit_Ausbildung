package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.dto.EduPlanDetailsDto;
import com.xgt.dto.TrainInfoDto;
import com.xgt.dto.TrainLabourerDto;
import com.xgt.dto.UnitTrainDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  培训详情
 * Created by HeLiu on 2018/7/2.
 */
@Repository
public class TrainDetailsDao {

    private final static Logger logger = LoggerFactory.getLogger(TrainDetailsDao.class);

    private final static String name_sapce = "trainDetails.";

    @Autowired
    private DaoClient daoClient;

    /**
     * @Description 培训系统PC版-查询公司名称
     * @author HeLiu
     * @date 2018/7/2 14:27
     */
    public TrainInfoDto queryCompanyName(Integer companyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        String sqlId = name_sapce + "queryCompanyName";
        return daoClient.queryForObject(sqlId, map, TrainInfoDto.class);
    }

    /**
     * @Description 培训系统PC版-查询单位名称
     * @author HeLiu
     * @date 2018/7/2 14:39
     */
    public List<Map<String, Object>> querySiteNames(Integer companyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        String sqlId = name_sapce + "querySiteNames";
        return daoClient.queryForList(sqlId, map);
    }

    /**
     * @Description 培训系统PC版-查询全部单培训详情
     * @author HeLiu
     * @date 2018/7/2 15:56
     */
    public List<UnitTrainDetailsDto> queryAllUnitTrainDetails(Integer companyId, String siteName) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("siteName", siteName);
        String sqlId = name_sapce + "queryAllUnitTrainDetails";
        return daoClient.queryForList(sqlId, map, UnitTrainDetailsDto.class);
    }

    /**
     * @Description 培训系统PC版-查询全部单培训详情数量
     * @author HeLiu
     * @date 2018/7/2 16:08
     */
    public Integer queryAllUnitTrainDetailsCount(Integer companyId, String siteName) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("siteName", siteName);
        String sqlId = name_sapce + "queryAllUnitTrainDetailsCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * @Description 培训系统PC版-查询已受训人数和参加考试人数（按时间段）
     * @author HeLiu
     * @date 2018/7/2 16:33
     */
    public Map<String, Object> queryPassAndExamCount(String siteCode, String examType, String passStatus, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("siteCode", siteCode);
        map.put("examType", examType);
        map.put("passStatus", passStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        String sqlId = name_sapce + "queryPassAndExamCount";
        return daoClient.queryForMap(sqlId, map);
    }

    /**
     * @Description 培训系统PC版-查询单个单位培训详情
     * @author HeLiu
     * @date 2018/7/3 10:14
     */
    public UnitTrainDetailsDto queryOneUnitTrainDetails(Integer companyId, Integer siteId, String siteName) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("siteId", siteId);
        map.put("siteName", siteName);
        String sqlId = name_sapce + "queryOneUnitTrainDetails";
        return daoClient.queryForObject(sqlId, map, UnitTrainDetailsDto.class);
    }

    /**
     * @Description 培训系统PC版-查询单位对应的学员列表
     * @author HeLiu
     * @date 2018/7/3 14:45
     */
    public List<TrainLabourerDto> queryTrainLabourers(Integer siteId, String labourerName, String examType, String passStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("siteId", siteId);
        map.put("labourerName", labourerName);
        map.put("examType", examType);
        map.put("passStatus", passStatus);
        String sqlId = name_sapce + "queryTrainLabourers";
        return daoClient.queryForList(sqlId, map, TrainLabourerDto.class);
    }

    /**
     * @Description 培训系统PC版-查询学员培训详情
     * @author HeLiu
     * @date 2018/7/3 18:02
     */
    public List<EduPlanDetailsDto> queryLabourerDetails(Integer labourerId) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        String sqlId = name_sapce + "queryLabourerDetails";
        return daoClient.queryForList(sqlId, map, EduPlanDetailsDto.class);
    }
}
