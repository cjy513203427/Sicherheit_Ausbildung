package com.xgt.controller.exam;

import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.exam.ExamMock;
import com.xgt.service.exam.ExamService;
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

@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    /**
     * 查询模拟考试列表接口
     *
     * @return
     */
    @RequestMapping(value = "/queryExamMockList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryExamMockList(ExamMock examMock) {
        Map<String, Object> result = null;
        try {
            result = examService.queryExamMockList(examMock);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryExamMockList方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 新增模拟考试接口
     *
     * @param examMock
     * @return
     */
    @RequestMapping(value = "/createExamMock", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> createExamMock(ExamMock examMock) {
        try {
            examMock.setCreateUserId(getLoginUserId());
            examService.createExamMock(examMock);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————createExamMock方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 查询当前模拟试卷的题目列表
     *
     * @param examMock
     * @return
     */
    @RequestMapping(value = "/queryThisMockQuestions", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryThisMockQuestions(ExamMock examMock) {
        List<ExamMock> result = null;
        try {
            result = examService.queryThisMockQuestions(examMock, SystemConstant.LoginType.LOGIN_TYPE_USER);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryThisMockQuestions方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * 新增模拟试卷的题目接口
     *
     * @param examMock
     * @return
     */
    @RequestMapping(value = "/addExamQuestions", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addExamQuestions(ExamMock examMock, String questionIds, String examType) {
        try {
            examMock.setCreateUserId(getLoginUserId());
            examService.addExamQuestions(examMock, questionIds, examType);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————addExamMockQuestions方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 删除模拟试卷接口
     *
     * @return
     */
    @RequestMapping(value = "/deleteExamMock", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteExamMock(Integer examId) {
        try {
            examService.deleteExamMock(examId, SystemConstant.ExamType.MOCK);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————deleteExamMock方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 删除模拟试卷的题目接口
     *
     * @param examId
     * @param questionIds
     * @return
     */
    @RequestMapping(value = "/deleteMockDownQuestion", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteMockDownQuestion(Integer examId, String questionIds) {
        try {
            examService.deleteMockDownQuestion(examId, questionIds, SystemConstant.ExamType.MOCK);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————deleteMockDownQuestion方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 修改模拟试卷接口
     *
     * @param examMock
     * @return
     */
    @RequestMapping(value = "/updateExamMock", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateExamMock(ExamMock examMock) {
        try {
            examService.updateExamMock(examMock);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————updateExamMock方法异常", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
