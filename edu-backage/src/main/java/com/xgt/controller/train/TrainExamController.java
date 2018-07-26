package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.exam.ExamMock;
import com.xgt.service.exam.ExamService;
import com.xgt.service.exam.MobileExamMockService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

@Controller
@RequestMapping("/trainExam")
public class TrainExamController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TrainExamController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private MobileExamMockService mobileExamMockService;

    /**
     *
     * 点击开始考试，生成入场人员的答题卷，和考试结果信息
     * @return
     */
    @RequestMapping(value = "/createAnswerCardAndResult", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> createAnswerCardAndResult(Integer programId,
                                                         @RequestParam("labourIdArr[]") Integer [] labourIds) {
        try {
            boolean result = examService.createAnswerCardAndResult(programId, labourIds);

            if(result){
                return ResultUtil.createSuccessResult(result);
            }else{
                return ResultUtil.createFailResult("已参加过本次考试！");
            }

        } catch (Exception e) {
            logger.error("——————queryExamMockList方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * 保存培训考试答案接口
     *
     * @param questionId
     * @param myAnswer
     * @return
     */
    @RequestMapping(value = "/saveTrainExanAnswer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveAnswer(Integer planId, String answerToolNum,
                                          Integer questionId, String myAnswer) {
        try {
            examService.saveTrainExanAnswer(planId, questionId, answerToolNum, myAnswer);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————saveAnswer方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     *  培训考试交卷接口
     *
     * @return
     */
    @RequestMapping(value = "/handInExamTrain", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> handInExamTrain(Integer planId) {
        try {
            examService.handInExamTrain(planId);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————handInExamTrain：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }



}
