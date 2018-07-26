package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.entity.EduQuestionBank;
import com.xgt.service.EduQuestionBankService;
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
 * @Date 2018/6/1 21:13.
 */
@Controller
@RequestMapping("/eduQuestionBank")
public class EduQuestionBankController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionBankController.class);

    @Autowired
    private EduQuestionBankService eduQuestionBankService ;

    /**
     * 检索题库
     * @param eduQuestionBank
     * @return
     */
    @RequestMapping(value = "/queryEduQuestionBank",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryEduQuestionBank(EduQuestionBank eduQuestionBank){
        try{
            Map map = eduQuestionBankService.queryEduQuestionBank(eduQuestionBank);
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("queryEduQuestionBank..........异常 EduQuestionBank=" +eduQuestionBank.toString(), e);
            return ResultUtil.createFailResult("queryEduQuestionBank..........异常 EduQuestionBank ="+eduQuestionBank.toString());
        }
    }

    /**
     * 修改题库
     * @param eduQuestionBank
     * @return
     */
    @RequestMapping(value = "/modifyEduQuestionBank",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBank:modifyEduQuestionBank")
    public Map<String,Object> modifyEduQuestionBank(EduQuestionBank eduQuestionBank){
        try{
            eduQuestionBank.setCreateUserId(getLoginUserId());
            Integer effectedId = eduQuestionBankService.modifyEduQuestionBank(eduQuestionBank);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("modifyEduQuestionBank..........异常 EduQuestionBank=" +eduQuestionBank.toString(), e);
            return ResultUtil.createFailResult("modifyEduQuestionBank..........异常 EduQuestionBank ="+eduQuestionBank.toString());
        }
    }

    /**
     * 添加题库
     * @param eduQuestionBank
     * @return
     */
    @RequestMapping(value = "/addEduQuestionBank",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBank:addEduQuestionBank")
    public Map<String,Object> addEduQuestionBank(EduQuestionBank eduQuestionBank){
        try{
            eduQuestionBank.setCreateUserId(getLoginUserId());
            Integer effectedId = eduQuestionBankService.addEduQuestionBank(eduQuestionBank);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestionBank..........异常 EduQuestionBank=" +eduQuestionBank.toString(), e);
            return ResultUtil.createFailResult("addEduQuestionBank..........异常 EduQuestionBank ="+eduQuestionBank.toString());
        }
    }

    /**
     * 删除题库，假删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteEduQuestionBank",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestionBank:deleteEduQuestionBank")
    public Map<String,Object> deleteEduQuestionBank(Integer id){
        try{
            Integer effectedId = eduQuestionBankService.deleteEduQuestionBank(id);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestionBank..........异常 EduQuestionBank=" +id.toString(), e);
            return ResultUtil.createFailResult("addEduQuestionBank..........异常 EduQuestionBank ="+id.toString());
        }
    }

}
