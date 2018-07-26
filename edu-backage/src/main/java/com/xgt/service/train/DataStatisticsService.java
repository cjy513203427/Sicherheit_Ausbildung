package com.xgt.service.train;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.train.DataStatisticsDao;
import com.xgt.dao.train.TrainDetailsDao;
import com.xgt.dto.DataStatisticsDto;
import com.xgt.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HeLiu
 * @Description 数据统计Service层
 * @date 2018/7/9 9:44
 */
@Service
public class DataStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(DataStatisticsService.class);

    @Autowired
    private DataStatisticsDao dataStatisticsDao;

    @Autowired
    private TrainDetailsDao trainDetailsDao;

    /**
     * @Description 培训系统PC版-查询数据统计
     * @author HeLiu
     * @date 2018/7/9 10:20
     */
    public List<DataStatisticsDto> queryDataStatistics(Integer companyId, String startTime, String endTime) {
        List<DataStatisticsDto> list = dataStatisticsDao.queryDataStatistics(companyId, SystemConstant.State.ACTIVE_STATE);
        if (CollectionUtils.isNotEmpty(list)) {
            for (DataStatisticsDto dataStatisticsDto : list) {
                //总数量
                Integer totalCount = dataStatisticsDto.getTotalCount();
                String code = dataStatisticsDto.getCode();
                Integer trainCount = 0;
                Integer examCount = 0;
                String trainLv = "0%";
                String passLv = "0%";
                Integer totalTime = 0;
                Integer trainLabourerCount = 0;
                Integer percapitaTime = 0;
                Integer percapitaCount = 0;
                if (totalCount != null && totalCount > 0) {
                    //查询培训数量和参加考试人数（按时间E段）
                    Map<String, Object> mapCount = trainDetailsDao.queryPassAndExamCount(code, SystemConstant.
                            ExamType.TRAIN, SystemConstant.passStatus.PASS, startTime, endTime);
                    trainCount = MapUtils.getInteger(mapCount, "passCount");
                    examCount = MapUtils.getInteger(mapCount, "examCount");
                    //计算培训率
                    trainLv = StringUtil.getNum(trainCount, totalCount);
                    if (examCount != null && examCount > 0) {
                        //计算合格率
                        passLv = StringUtil.getNum(trainCount, examCount);
                    }
                    //查询总学时和培训人次
                    Map<String, Object> timeAndTrainMap = dataStatisticsDao.queryTimeAndTrain(companyId, code, SystemConstant.ExamType.TRAIN);
                    totalTime = MapUtils.getInteger(timeAndTrainMap, "totalTime");
                    trainLabourerCount = MapUtils.getInteger(timeAndTrainMap, "trainLabourerCount");
                    if (trainCount != null && trainCount > 0) {
                        //计算人均学时
                        percapitaTime = totalTime / trainCount;
                    }
                    //计算人均培训次数
                    percapitaCount = trainLabourerCount / totalCount;
                }
                dataStatisticsDto.setTrainCount(trainCount);
                dataStatisticsDto.setTrainLv(trainLv);
                dataStatisticsDto.setPassLv(passLv);
                dataStatisticsDto.setTotalTime(totalTime);
                dataStatisticsDto.setPercapitaTime(percapitaTime);
                dataStatisticsDto.setTrainLabourerCount(trainLabourerCount);
                dataStatisticsDto.setPercapitaCount(percapitaCount);
            }
        }
        return list;
    }
}
