package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.dto.EduPlanDetailsDto;
import com.xgt.dto.TrainInfoDto;
import com.xgt.service.train.TrainDetailsService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * Created by HeLiu on 2018/7/2.
 */
@Controller
@RequestMapping("/trainDetails")
public class TrainDetailsController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(TrainDetailsController.class);

    @Autowired
    private TrainDetailsService trainDetailsService;

    /**
     * @Description 培训系统PC版-查询单位信息接口
     * @author HeLiu
     * @date 2018/7/2 11:37
     */
    @RequestMapping(value = "queryUnitInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryUnitInfo() {
        try {
            TrainInfoDto result = trainDetailsService.queryUnitInfo(getCompanyId());
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryTrainInfo** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-查询全部单培训详情接口
     * @author HeLiu
     * @date 2018/7/2 11:37
     */
    @RequestMapping(value = "queryAllUnitTrainDetails", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryAllUnitTrainDetails(String siteName) {
        try {
            Map<String, Object> result = trainDetailsService.queryAllUnitTrainDetails(getCompanyId(), siteName);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryAllUnitTrainDetails** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-查询单位培训详情接口（单位，单位下学员）
     * @author HeLiu
     * @date 2018/7/3 9:35
     */
    @RequestMapping(value = "queryUnitAndLabouersTrain", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryUnitAndLabouersTrain(Integer siteId, String siteName, String labourerName) {
        try {
            Map<String, Object> result = trainDetailsService.queryUnitAndLabouersTrain(getCompanyId(), siteId, siteName, labourerName);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryUnitAndLabouersTrain** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-查询学员培训详情接口
     * @author HeLiu
     * @date 2018/7/3 16:13
     */
    @RequestMapping(value = "queryLabourerDetails", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryLabourerDetails(Integer labourerId) {
        try {
            List<EduPlanDetailsDto> result = trainDetailsService.queryLabourerDetails(labourerId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryLabourerDetails** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
