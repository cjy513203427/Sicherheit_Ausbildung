package com.xgt.controller.train;

import com.xgt.common.BaseController;
import com.xgt.common.PageQueryEntity;
import com.xgt.entity.train.EduExamTrain;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.entity.train.EduTrainProgram;
import com.xgt.entity.train.EduTraprogramExamCfg;
import com.xgt.service.train.ProgramService;
import com.xgt.util.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-26 17:34
 **/
@Controller
@RequestMapping("program")
public class ProgramController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);

    @Autowired
    private ProgramService programService ;

    /**
     * @Description 培训系统PC版 - 根据课程id获取题目
     * @author Joanne
     * @create 2018/6/27 16:25
     */
    @RequestMapping(value = "queryQuestionByCourseId" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryQuestionByCourseId(String courseIds, //课程id
                                                      String testName ,
                                                      PageQueryEntity pageQueryEntity //分页信息
    ){
        try {
            Map<String,Object> questionInfo = programService.queryQuestionInfo(courseIds,pageQueryEntity,testName);
            return ResultUtil.createSuccessResult(questionInfo);
        }catch (Exception e){
            logger.error("查询题目失败......",e);
            return ResultUtil.createFailResult("scanQuestion...wrong....."+e) ;
        }
    }

    /**
     * @Description 培训系统PC版 - 新增方案
     * @author Joanne
     * @create 2018/6/26 18:28
     */
    @RequestMapping(value = "addProgram" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryContentList(EduTrainProgram eduTrainProgram, // 方案信息
                                               EduExamTrain eduExamTrain,  // 方案试卷信息
                                               String testIds,  // 手动组卷时选择的题目id
                                               String data ,//视频集锦id和视频内容（即课程id）的JSON串
                                               MultipartFile multipartFile, // 方案封面
                                               Boolean autoflag, // 自动组卷标识 true：自动组卷
                                               EduTraprogramExamCfg eduTraprogramExamCfg // 自动组卷配置信息
    ){
        try {
//            eduTrainProgram.setCompanyId(getCompanyId());
            eduTrainProgram.setCompanyId(2);
            programService.addProgram(eduTrainProgram,eduExamTrain,data,
                    testIds,multipartFile, autoflag, eduTraprogramExamCfg);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("新增方案失败......",e);
            return ResultUtil.createFailResult("addProgram...wrong....." + e) ;
        }
    }

    /**
     * @Description 培训系统PC版 - 方案修改
     * @author Joanne
     * @create 2018/7/12 15:40
     */
    @RequestMapping(value = "updateProgram" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateProgram(EduTrainProgram eduTrainProgram, // 方案信息
                                               MultipartFile multipartFile // 方案封面
    ){
        try {
            programService.updateProgram(eduTrainProgram,multipartFile);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("新增方案失败......",e);
            return ResultUtil.createFailResult("addProgram...wrong....." + e) ;
        }
    }


    /**
     * @Description 培训系统PC版 - 浏览本公司考试方案
     * @author Joanne
     * @create 2018/6/27 15:42
     */
    @RequestMapping(value = "scanProgram" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> scanProgram(EduTrainProgram eduTrainProgram){
        try {
//            eduTrainProgram.setCompanyId(getCompanyId());
                        eduTrainProgram.setCompanyId(1);
            Map<String,Object> programInfo = programService.queryProgramInfo(eduTrainProgram);
            return ResultUtil.createSuccessResult(programInfo);
        }catch (Exception e){
            logger.error("查询方案失败......",e);
            return ResultUtil.createFailResult("scanProgram...wrong....."+e) ;
        }
    }

    /**
     * @Description 删除方案
     * @author Joanne
     * @create 2018/7/11 14:22
     */
    @RequestMapping(value = "deleteProgram" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("program:delete")
    public Map<String,Object> deleteProgram(Integer programId){
        try {
            programService.deleteProgram(programId);
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("删除方案失败......",e);
            return ResultUtil.createFailResult("deleteProgram...wrong....."+e) ;
        }
    }

}