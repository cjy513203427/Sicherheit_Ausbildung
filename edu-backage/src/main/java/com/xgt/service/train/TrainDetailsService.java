package com.xgt.service.train;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.train.TrainDetailsDao;
import com.xgt.dto.EduPlanDetailsDto;
import com.xgt.dto.TrainInfoDto;
import com.xgt.dto.TrainLabourerDto;
import com.xgt.dto.UnitTrainDetailsDto;
import com.xgt.util.DateUtil;
import com.xgt.util.JSONUtil;
import com.xgt.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HeLiu on 2018/7/2.
 */
@Service
public class TrainDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(TrainDetailsService.class);

    @Autowired
    private TrainDetailsDao trainDetailsDao;

    /**
     * @Description 培训系统PC版-查询单位信息
     * @author HeLiu
     * @date 2018/7/2 11:50
     */
    public TrainInfoDto queryUnitInfo(Integer companyId) {
        TrainInfoDto trainInfoDto = trainDetailsDao.queryCompanyName(companyId);
        List<Map<String, Object>> siteNames = trainDetailsDao.querySiteNames(companyId);
        trainInfoDto.setSiteNames(siteNames);
        return trainInfoDto;
    }

    /**
     * @Description 培训系统PC版-查询全部单培训详情（分页）
     * @author HeLiu
     * @date 2018/7/2 15:54
     */
    public Map<String, Object> queryAllUnitTrainDetails(Integer companyId, String siteName) {
        //查询全部单培训详情数量
        Integer total = trainDetailsDao.queryAllUnitTrainDetailsCount(companyId, siteName);

        List<UnitTrainDetailsDto> list = null;
        if (total != null && total > 0) {
            list = trainDetailsDao.queryAllUnitTrainDetails(companyId, siteName);
            //遍历集合组装数据
            for (UnitTrainDetailsDto unitTrainDetailsDto : list) {
                setMessage(unitTrainDetailsDto);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, UnitTrainDetailsDto.class));
        map.put("total", total);
        return map;
    }

    /**
     * @Description 查询单位详情组装数据
     * @author HeLiu
     * @date 2018/7/3 10:20
     */
    public void setMessage(UnitTrainDetailsDto unitTrainDetailsDto) {
        //总人数
        Integer totalCount = unitTrainDetailsDto.getTotalCount();
        String siteCode = unitTrainDetailsDto.getSiteCode();
        Integer passCount = 0;
        Integer examCount = 0;
        String peiXunLv = "0%";
        String passLv = "0%";
        //防止被除数为零时错误
        if (totalCount != null && totalCount > 0) {
            //查询已受训人数和参加考试人数（按时间段）
            Map<String, Object> mapCount = trainDetailsDao.queryPassAndExamCount(siteCode, SystemConstant.
                    ExamType.TRAIN, SystemConstant.passStatus.PASS, null, null);
            passCount = MapUtils.getInteger(mapCount, "passCount");
            examCount = MapUtils.getInteger(mapCount, "examCount");
            //计算培训率
            peiXunLv = StringUtil.getNum(passCount, totalCount);
            if (examCount != null && examCount > 0) {
                //计算考试合格率
                passLv = StringUtil.getNum(passCount, examCount);
            }
        }
        unitTrainDetailsDto.setHasedTrainCount(passCount);
        unitTrainDetailsDto.setPeiXunLv(peiXunLv);
        unitTrainDetailsDto.setPassLv(passLv);
    }

    /**
     * @Description 培训系统PC版-查询单位培训详情（单位，单位下学员）
     * @author HeLiu
     * @date 2018/7/3 9:59
     */
    public Map<String, Object> queryUnitAndLabouersTrain(Integer companyId, Integer siteId, String siteName, String labourerName) {
        Map<String, Object> map = new HashMap<>();

        //查询单个单位培训详情
        UnitTrainDetailsDto unitTrainDetailsDto = trainDetailsDao.queryOneUnitTrainDetails(companyId, siteId, siteName);
        setMessage(unitTrainDetailsDto);
        //查询单位对应的学员列表
        List<TrainLabourerDto> labourerDtos = trainDetailsDao.queryTrainLabourers(siteId, labourerName, SystemConstant.ExamType.TRAIN, SystemConstant.passStatus.PASS);
        for (TrainLabourerDto trainLabourerDto : labourerDtos) {
            Integer examCount = trainLabourerDto.getExamCount();
            Integer passCount = trainLabourerDto.getPassCount();
            String testPass = "0%";
            if (examCount != null & examCount > 0) {
                testPass = StringUtil.getNum(passCount, examCount);
            }
            //TODO 总培训时长（分钟）；试题学习时长（分钟）
            trainLabourerDto.setTestPass(testPass);
        }
        map.put("unitTrainDetailsDto", unitTrainDetailsDto);
        map.put("labourerDtos", labourerDtos);
        return map;
    }

    /**
     * @Description 培训系统PC版-查询学员培训详情
     * @author HeLiu
     * @date 2018/7/3 17:52
     */
    public List<EduPlanDetailsDto> queryLabourerDetails(Integer labourerId) {
        List<EduPlanDetailsDto> eduPlanDetailsDtos = trainDetailsDao.queryLabourerDetails(labourerId);
        if (CollectionUtils.isNotEmpty(eduPlanDetailsDtos)) {
            for (EduPlanDetailsDto eduPlanDetailsDto : eduPlanDetailsDtos) {
                String start = eduPlanDetailsDto.getStart();
                String end = eduPlanDetailsDto.getEnd();
                String currentTime = DateUtil.getCurrentTime();
                String status = DateUtil.compareDate(currentTime, start, end, "yyyy-MM-dd HH:mm:ss");
                eduPlanDetailsDto.setStatus(status);
            }
        }
        return eduPlanDetailsDtos;
    }
}
