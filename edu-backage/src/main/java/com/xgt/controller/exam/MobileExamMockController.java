package com.xgt.controller.exam;

import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.exam.ExamLabourerRel;
import com.xgt.entity.exam.ExamMock;
import com.xgt.service.exam.ExamService;
import com.xgt.service.exam.MobileExamMockService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;


@Controller
@RequestMapping("/mobile_mock")
public class MobileExamMockController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MobileExamMockController.class);

    @Autowired
    private MobileExamMockService mobileExamMockService;

    @Autowired
    private ExamService examService;

    /**
     * 手机端模拟考试列表查询接口
     *
     * @return
     */
    @RequestMapping(value = "/queryMobileMockList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryMobileMockList() {
        try {
            List<ExamMock> result = mobileExamMockService.queryMobileMockList(getLabourerUserId(), SystemConstant.ExamType.MOCK);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryMobileMockList方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 手机端模拟考试试题查询 ,模拟考试上一题、下一题查询
     * upOrDownStatus 1: 上一题、2：下一题
     *
     * @return
     */
    @RequestMapping(value = "/queryMobileMockQuestions", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryMobileMockQuestions(Integer examId, String upOrDownStatus, Integer questionId) {
        try {

            EduQuestion result = mobileExamMockService.queryMobileMockQuestions(getLabourerUserId(), examId,
                    SystemConstant.ExamType.MOCK, upOrDownStatus, questionId);
            return ResultUtil.createSuccessResult(result);

        } catch (Exception e) {
            logger.error("——————queryMobileMockQuestions方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 手机端答题卡查询接口（模拟）
     *
     * @param examId
     * @return
     */
    @RequestMapping(value = "/queryAnswerCard", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryAnswerCard(Integer examId) {
        try {
            Map<String, Object> result = mobileExamMockService.queryAnswerCard(getLabourerUserId(), examId, SystemConstant.ExamType.MOCK);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryAnswerCard方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 保存答案接口
     *
     * @param examId
     * @param questionId
     * @param myAnswer
     * @return
     */
    @RequestMapping(value = "/saveAnswer", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveAnswer(Integer examId, Integer questionId, String myAnswer) {
        try {
            Map result = mobileExamMockService.saveAnswer(getLabourerUserId(), examId, questionId, SystemConstant.ExamType.MOCK, myAnswer);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————saveAnswer方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 交卷接口
     *
     * @param examId
     * @return
     */
    @RequestMapping(value = "/handInExamMock", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> handInExamMock(Integer examId) {
        try {
            return mobileExamMockService.handInExamMock(getLabourerUserId(), examId, SystemConstant.ExamType.MOCK);
        } catch (Exception e) {
            logger.error("——————saveExamMock方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 查询答案及解析
     *
     * @param examId
     * @return
     */
    @RequestMapping(value = "/queryAnswerAndParsing", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryAnswerAndParsing(Integer examId, String upOrDownStatus, Integer questionId) {
        try {
            EduQuestion result = mobileExamMockService.queryAnswerAndParsing(getLabourerUserId(), examId, SystemConstant.ExamType.MOCK, upOrDownStatus, questionId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryAnswerAndParsing方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 手机端单个模拟考试试题详情查询接口
     *
     * @param examId
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/queryMockQuestionById", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryMockQuestionById(Integer examId, Integer questionId) {
        try {
            EduQuestion result = mobileExamMockService.queryMockQuestionById(examId, SystemConstant.ExamType.MOCK,
                    questionId, getLabourerUserId());
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryMockQuestionById方法异常方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }
}
