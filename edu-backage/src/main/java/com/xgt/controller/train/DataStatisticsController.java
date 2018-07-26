package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.dto.DataStatisticsDto;
import com.xgt.service.train.DataStatisticsService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * @author HeLiu
 * @Description 数据统计Controller层
 * @date 2018/7/7 17:14
 */
@Controller
@RequestMapping("/data")
public class DataStatisticsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DataStatisticsController.class);

    @Autowired
    private DataStatisticsService dataStatisticsService;

    /**
     * @Description 培训系统PC版-查询数据统计接口
     * @author HeLiu
     * @date 2018/7/9 10:18
     */
    @RequestMapping(value = "queryDataStatistics", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryDataStatistics(String startTime, String endTime) {
        try {
            List<DataStatisticsDto> result = dataStatisticsService.queryDataStatistics(getCompanyId(), startTime, endTime);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryDataStatistics** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }
}
