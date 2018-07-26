package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionExercise;
import com.xgt.enums.EnumCorrectFlag;
import com.xgt.service.EduQuestionExerciseService;
import com.xgt.util.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/7 14:01.
 */
@Controller
@RequestMapping("/eduQuestionExercise")
public class EduQuestionExerciseController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionExerciseController.class);

    @Autowired
    private EduQuestionExerciseService eduQuestionExerciseService ;

    /**
     * 检索练习
     * 错题本
     * @param eduQuestionExercise
     * @return
     */
    @RequestMapping(value = "/queryEduQuestionExercise",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryEduQuestionExercise(EduQuestionExercise eduQuestionExercise){
        try{
            Map map = eduQuestionExerciseService.queryEduQuestionExercise(eduQuestionExercise);
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("queryEduQuestionExercise..........异常 EduQuestionExercise=" +eduQuestionExercise.toString(), e);
            return ResultUtil.createFailResult("queryEduQuestionExercise..........异常 EduQuestionExercise ="+eduQuestionExercise.toString());
        }
    }



    /**
     *  手机端 - 获取题库列表
     * 获取练习题目详情
     * 总题目数
     * 已回答数
     * 正确率
     * 参数： questionId,questionBankId
     * @return
     */
    @RequestMapping(value = "/getDetails",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> getDetails(EduQuestionExercise eduQuestionExercise){
        try{
            eduQuestionExercise.setPostType(getLoginLabourer().getPostType());
            eduQuestionExercise.setLabourId(getLabourerUserId());
            Map map = eduQuestionExerciseService.getDetails(getLoginLabourer().getPostType(), getLabourerUserId());
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("getDetails..........异常 EduQuestionExercise="+eduQuestionExercise.toString() , e);
            return ResultUtil.createFailResult("getDetails..........异常 EduQuestionExercise ="+eduQuestionExercise.toString());
        }
    }


    /**
     *  手机端 -  章节练习，上一题、下一题 & 保存练习答题记录
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:06
     */
    @RequestMapping(value = "/queryNextOrPrevQuestion",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryNextOrPrevQuestion(Integer questionBankId, Integer questionId,
                                                String nextPrevFlag, String myAnswer){
        logger.info(".......queryNextOrPrevQuestion........questionBankId:{},questionId:{},nextPrevFlag:{},myAnswer:{}",
                questionBankId, questionId, nextPrevFlag, myAnswer);
        try{
            Integer labourId = getLabourerUserId();

            Map result = eduQuestionExerciseService.queryNextOrPrevQuestion(questionBankId,
                    questionId, nextPrevFlag, myAnswer, labourId);

            return ResultUtil.createSuccessResult(result);
        }catch(Exception e){
            logger.error("queryNextOrPrevQuestion..........异常：", e);
            return ResultUtil.createFailResult("queryNextOrPrevQuestion..........异常 "+ e.getMessage());
        }
    }


    /**
     *  手机端 -  查看错题，上一题、下一题
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:06
     */
    @RequestMapping(value = "/queryNextOrPrevQuestionForWrong",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryNextOrPrevQuestionForWrong(Integer questionId, String nextPrevFlag){
        logger.info(".......queryNextOrPrevQuestionForWrong........questionId:{},nextPrevFlag:{}",
                 questionId, nextPrevFlag );
        try{
            Integer labourId = getLabourerUserId();

            EduQuestion result = eduQuestionExerciseService.queryNextOrPrevQuestionForWrong(
                    questionId, nextPrevFlag, labourId);

            return ResultUtil.createSuccessResult(result);
        }catch(Exception e){
            logger.error("queryNextOrPrevQuestionForWrong..........异常：", e);
            return ResultUtil.createFailResult("queryNextOrPrevQuestionForWrong..........异常 "+ e.getMessage());
        }
    }
}
