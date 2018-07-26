package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.service.train.InTheTrainService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * @author HeLiu
 * @Description 进入培训Controller层
 * @date 2018/7/6 10:33
 */
@Controller
@RequestMapping("/inTheTrain")
public class InTheTrainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(InTheTrainController.class);

    @Autowired
    private InTheTrainService inTheTrainService;

    /**
     * @Description 培训系统PC版-查询进入培训列表接口（培训方案，进行中的培训计划）
     * @author HeLiu
     * @date 2018/7/6 14:37
     */
    @RequestMapping(value = "queryInTheTrainProgramList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryInTheTrainProgramList() {
        try {
            Map<String, Object> result = inTheTrainService.queryInTheTrainProgramList(getCompanyId());
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryInTheTrainProgramList** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * @Description 培训系统PC版-查询考试题目接口（视频题目）
     * 第一次传questionId为空
     * @author HeLiu
     * @date 2018/7/11 10:34
     */
    @RequestMapping(value = "queryTestQuestion", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryTestQuestion(Integer programId, Integer planId, Integer questionId) {
        try {
            Map<String, Object> result = inTheTrainService.queryTestQuestion(programId, planId, questionId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("Mistakes are happening for **queryTestQuestion** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     *  保存人员和答题器编号的,对应关系
     * @author liuao
     * @date 2018/7/16 11:59
     */
    @RequestMapping(value = "saveAnswertoolRel", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveAnswertoolRel(String jsonStr) {
        try {
            inTheTrainService.saveAnswertoolRel(jsonStr);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("Mistakes are happening for **saveAnswertoolRel** method", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
