package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.dto.EduPlanDetailsDto;
import com.xgt.dto.EduPlanDto;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.service.train.PlanService;
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
 * @Description 培训计划Controller层
 * @date 2018/6/29 12:20
 */
@Controller
@RequestMapping("/plan")
public class PlanController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    private PlanService planService;

    /**
     * @Description 培训系统PC版-查询培训记录列表接口
     * @author HeLiu
     * @date 2018/6/29 14:18
     */
    @RequestMapping(value = "queryPlanList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryPlanList(EduPlanDto eduPlanDto) {
        try {
            eduPlanDto.setCompanyId(getCompanyId());
            Map<String, Object> result = planService.queryPlanList(eduPlanDto);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryPlanList** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-查询培训记录详情接口
     * @author HeLiu
     * @date 2018/6/29 18:14
     */
    @RequestMapping(value = "queryPlanDetails", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryPlanDetails(Integer programId, Integer planId, String planStatus) {
        try {
            List<EduPlanDetailsDto> result = planService.queryPlanDetails(programId, planId, planStatus);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryPlanDetails** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版 - 方案计划修改查询详情
     * @author Joanne
     * @create 2018/7/16 20:23
     */
    @RequestMapping(value = "queryPlanById", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryPlanById(Integer planId) {
        try {
            EduTrainPlan eduTrainPlan = planService.queryPlanById(planId);
            return ResultUtil.createSuccessResult(eduTrainPlan);
        } catch (Exception e) {
            logger.error("查询计划失败......", e);
            return ResultUtil.createFailResult("queryPlan...wrong....." + e);
        }
    }

    /**
     * @Description 新增培训计划
     * @author Joanne
     * @create 2018/7/12 17:21
     */
    @RequestMapping(value = "addPlan", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addPlan(EduTrainPlan eduTrainPlan, String ids) {
        try {
            planService.addPlan(eduTrainPlan, ids);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("添加培训计划失败", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 修改培训计划
     * @author Joanne
     * @create 2018/7/13 18:12
     */
    @RequestMapping(value = "updatePlan", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updatePlan(EduTrainPlan eduTrainPlan, String ids) {
        try {
            planService.updatePlan(eduTrainPlan, ids);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("修改培训计划失败", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 删除计划
     * @author Joanne
     * @create 2018/7/16 21:22
     */
    @RequestMapping(value = "deletePlan", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deletePlan(Integer id) {
        try {
            planService.deletePlan(id);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("修改培训计划失败", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-导出培训记录接口（Word）
     * @author HeLiu
     * @date 2018/6/29 18:14
     */
    @RequestMapping(value = "exportTrainRecord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public void exportTrainRecord(Integer programId, Integer planId) {
        try {
            planService.exportTrainRecord(getCompanyId(), programId, planId);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **exportTrainRecord** method", e);
        }
    }

    /**
     * 导出学员培训情况Word
     * @param labourerId
     * @param companyId
     * @return
     */
    @RequestMapping(value = "exportBuildLabourer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map exportBuildLabourer(Integer labourerId,Integer companyId) {
        try {
            planService.exportBuildLabourer(labourerId,companyId);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("Mistakes are happening for **exportBuildLabourer** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * @Description 培训系统PC版- 查询培训记录（为修改查询）
     * @author Joanne
     * @create 2018/7/18 14:57
     */
    @RequestMapping(value = "queryPlanInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryPlanInfo(Integer planId) {
        try {
            Map map = planService.queryPlanInfo(planId);
            return ResultUtil.createSuccessResult(map);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **exportTrainRecord** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }
}
