package com.xgt.controller;

import com.xgt.entity.EduQuestionAnswer;
import com.xgt.service.EduQuestionAnswerService;
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
 * @Date 2018/6/6 11:46.
 */
@Controller
@RequestMapping("/eduQuestionAnswer")
public class EduQuestionAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionAnswerController.class);
    @Autowired
    private EduQuestionAnswerService eduQuestionAnswerService;

    /**
     * 检索答案
     * @param eduQuestionAnswer
     * @return
     */
    @RequestMapping(value = "/queryEduQuestionAnswer",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        try{
            Map map = eduQuestionAnswerService.queryEduQuestionAnswer(eduQuestionAnswer);
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("queryEduQuestionAnswer..........异常 EduQuestionAnswer="+eduQuestionAnswer.toString() , e);
            return ResultUtil.createFailResult("queryEduQuestionAnswer..........异常 EduQuestionAnswer ="+eduQuestionAnswer.toString());
        }
    }

    /**
     * 修改答案
     * @param eduQuestionAnswer
     * @return
     */
    @RequestMapping(value = "/modifyEduQuestionAnswer",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBankAnswer:modifyEduQuestionAnswer")
    public Map<String,Object> modifyEduQuestion(EduQuestionAnswer eduQuestionAnswer){
        try{
            Integer effectedId = eduQuestionAnswerService.modifyEduQuestionAnswer(eduQuestionAnswer);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("modifyEduQuestionAnswer..........异常 EduQuestionAnswer=" +eduQuestionAnswer.toString(), e);
            return ResultUtil.createFailResult("modifyEduQuestionAnswer..........异常 EduQuestionAnswer ="+eduQuestionAnswer.toString());
        }
    }

    /**
     * 添加答案
     * @param eduQuestionAnswer
     * @return
     */
    @RequestMapping(value = "/addEduQuestionAnswer",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBankAnswer:addEduQuestionAnswer")
    public Map<String,Object> addEduQuestion(EduQuestionAnswer eduQuestionAnswer){
        try{
            Integer effectedId = eduQuestionAnswerService.addEduQuestionAnswer(eduQuestionAnswer);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestionAnswer..........异常 EduQuestionAnswer=" +eduQuestionAnswer.toString(), e);
            return ResultUtil.createFailResult("addEduQuestionAnswer..........异常 EduQuestionAnswer ="+eduQuestionAnswer.toString());
        }
    }

    /**
     * 删除答案，真删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteEduQuestionAnswer",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBankAnswer:deleteEduQuestionAnswer")
    public Map<String,Object> deleteEduQuestion(Integer id){
        try{
            Integer effectedId = eduQuestionAnswerService.deleteEduQuestionAnswer(id);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestionAnswer..........异常 EduQuestionAnswer=" +id.toString(), e);
            return ResultUtil.createFailResult("addEduQuestionAnswer..........异常 EduQuestionAnswer ="+id.toString());
        }
    }
}
