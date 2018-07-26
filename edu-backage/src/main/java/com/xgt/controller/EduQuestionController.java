package com.xgt.controller;

import com.alibaba.fastjson.JSONObject;
import com.xgt.common.BaseController;
import com.xgt.common.PageQueryEntity;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.NotSelectEduQuestion;
import com.xgt.service.EduQuestionAnswerService;
import com.xgt.service.EduQuestionService;
import com.xgt.service.train.ProgramService;
import com.xgt.util.FileToolUtil;
import com.xgt.util.ReadExcelUtils;
import com.xgt.util.ResultUtil;
import com.xgt.util.wechat.DeleteFileUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.xgt.constant.SystemConstant.CHARSET_NAME;
import static com.xgt.constant.SystemConstant.UPLOADED_FILE_PATH;
import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * @author cjy
 * @Date 2018/6/1 19:55.
 */
@Controller
@RequestMapping("/eduQuestion")
public class EduQuestionController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionController.class);

    @Autowired
    private EduQuestionService eduQuestionService ;

    @Autowired
    private EduQuestionAnswerService eduQuestionAnswerService ;

    @Autowired
    private ProgramService programService ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    /**
     * 前台接口
     * 检索题目和该题目的答案
     * @param eduQuestion
     * @return
     */
    @RequestMapping(value = "/queryEduQuestionApi",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryEduQuestionApi(EduQuestion eduQuestion){
        try{
            Map map = eduQuestionService.queryEduQuestionApi(eduQuestion);
            logger.info("................:{}", JSONObject.toJSONString(map));
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("queryEdqQuestion..........异常 EduQuestion="+eduQuestion.toString() , e);
            return ResultUtil.createFailResult("queryEduQuestion..........异常 EduQuestion ="+eduQuestion.toString());
        }
    }

    /**
     * 后台方法
     * 检索题目
     * @param eduQuestion
     * @return
     */
    @RequestMapping(value = "/queryEduQuestion",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryEduQuestion(EduQuestion eduQuestion){
        try{
            Map map = eduQuestionService.queryEduQuestion(eduQuestion);
            logger.info("................:{}", JSONObject.toJSONString(map));
            return ResultUtil.createSuccessResult(map);
        }catch(Exception e){
            logger.error("queryEdqQuestion..........异常 EduQuestion="+eduQuestion.toString() , e);
            return ResultUtil.createFailResult("queryEduQuestion..........异常 EduQuestion ="+eduQuestion.toString());
        }
    }

    /**
     * 修改题目
     * @param eduQuestion
     * @return
     */
    @RequestMapping(value = "/modifyEduQuestion",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestion:modifyEduQuestion")
    public Map<String,Object> modifyEduQuestion(EduQuestion eduQuestion){
        try{
            eduQuestion.setCreateUserId(getLoginUserId());
            eduQuestion.setDeleteStatus(1);
            Integer effectedId = eduQuestionService.modifyEduQuestion(eduQuestion);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("modifyEduQuestion..........异常 EduQuestion=" +eduQuestion.toString(), e);
            return ResultUtil.createFailResult("modifyEduQuestion..........异常 EduQuestion ="+eduQuestion.toString());
        }
    }

    /**
     * 添加题目
     * @param eduQuestion
     * @return
     */
    @RequestMapping(value = "/addEduQuestion",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestion:addEduQuestion")
    public Map<String,Object> addEduQuestion(EduQuestion eduQuestion){
        try{
            eduQuestion.setCreateUserId(getLoginUserId());
            Integer effectedId = eduQuestionService.addEduQuestion(eduQuestion);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestion..........异常 EduQuestion=" +eduQuestion.toString(), e);
            return ResultUtil.createFailResult("addEduQuestion..........异常 EduQuestion ="+eduQuestion.toString());
        }
    }


    /**
     * @Description 添加课程对应题目（需上传视频）
     * @author Joanne
     * @create 2018/6/29 10:25
     */
    @RequestMapping(value = "/createVideoQus",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestion:addEduQuestion")
    public Map<String,Object> createVideoQus(EduQuestion eduQuestion,MultipartFile file,Integer courseId){
        try{
            eduQuestion.setCreateUserId(getLoginUserId());
            eduQuestionService.createVideoQus(eduQuestion,file,courseId);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("createVideoQus..........异常 createVideoQus=" +eduQuestion.toString(), e);
            return ResultUtil.createFailResult("createVideoQus..........异常 createVideoQus ="+eduQuestion.toString());
        }
    }

    /**
     * 删除题目，假删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteEduQuestion",produces = "application/json; charset=utf-8")
    @ResponseBody
    @RequiresPermissions("eduQuestion:deleteEduQuestion")
    public Map<String,Object> deleteEduQuestion(Integer id){
        try{
            Integer effectedId = eduQuestionService.deleteEduQuestion(id);
            return ResultUtil.createSuccessResult(effectedId);
        }catch(Exception e){
            logger.error("addEduQuestion..........异常 EduQuestion=" +id.toString(), e);
            return ResultUtil.createFailResult("addEduQuestion..........异常 EduQuestion ="+id.toString());
        }
    }

    /**
     * Excel解析
     * 思路：上传到本地，解析本地的Excel
     * 解析完毕再将Excel删除
     * @param file
     * @param fileName
     * @param questionBankId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/importExcelForEduQuestion")
    @ResponseBody
    @RequiresPermissions("eduQuestion:importExcelForEduQuestion")
    public Map<String,Object> importExcelForEduQuestion(@RequestParam("excelPath") MultipartFile file,@RequestParam("fileName") String fileName
    ,@RequestParam("questionBankId") Integer questionBankId) throws IOException {
        //如果文件不为空，写入上传路径
        if(!file.isEmpty()) {
            //上传文件路径
            //上传文件名
            BASE64Decoder decoder = new BASE64Decoder();
            String filename = new String(decoder.decodeBuffer(fileName),CHARSET_NAME);
            //将上传文件保存到一个目标文件当中
            String tempFilePath = UPLOADED_FILE_PATH + File.separator + filename;
            try {

                File targetFile = new File(tempFilePath);
                if(targetFile.exists()){
                    targetFile.delete();
                }

                FileToolUtil.inputStream2File(file.getInputStream(), tempFilePath);

                try {
                    ReadExcelUtils excelReader = new ReadExcelUtils(tempFilePath);
                    Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();
                    for (int i = 1; i <= map.size(); i++) {
                        // 题目信息对象
                        Map<Integer,Object> questionObj = map.get(i);

                        // 初始化题目信息
                        EduQuestion eduQuestion = EduQuestionService.initEduQuestion(questionObj, getLoginUserId());

                        // 题库id
                        eduQuestion.setQuestionBankId(questionBankId);

                        // 同一事物保存题目和答案
                        transactionTemplate.execute(new TransactionCallback<Boolean>() {
                            @Override
                            public Boolean doInTransaction(TransactionStatus status) {

                                // 保存题目信息、题目的选项
                                eduQuestionService.saveQuestionAndQuestionOption(eduQuestion, questionObj);

                                return true;
                            }
                        });


                    }
                    DeleteFileUtil.delete(UPLOADED_FILE_PATH);
                } catch (FileNotFoundException e) {
                    logger.error("未找到指定路径的文件!",e);
                }catch (Exception e) {
                    logger.error("解析题库文件异常!",e);
                }
            } catch (IOException e) {
                logger.error("解析题库文件异常!",e);
                return ResultUtil.createFailResult("上传失败");
            }
            return ResultUtil.createSuccessResult();
        } else {
            return ResultUtil.createFailResult("上传文件为空");
        }
    }

    /**
     * @Description 导入视频题目
     * @author Joanne
     * @create 2018/6/28 12:41
     */
    @RequestMapping(value = "/importContentQuestion")
    @ResponseBody
    @RequiresPermissions("eduQuestion:importExcelForEduQuestion")
    public Map<String,Object> importContentQuestion(@RequestParam("excelPath") MultipartFile file,
                                                    @RequestParam("fileName") String fileName,
                                                    @RequestParam("courseId") Integer courseId){
        try {
            eduQuestionService.importVideoContentExcel(file,fileName,courseId,getLoginUserId());
            return ResultUtil.createSuccessResult();
        }catch (Exception e){
            logger.error("导入视频题库出错.........",e);
            return ResultUtil.createFailResult("导入视频题库出错"+e);
        }
    }

    /**
     * 查询所有题目列表（除了已添加）
     *
     * @param notSelectEduQuestion
     * @return
     */
    @RequestMapping(value = "/queryNotSelectEduQuestion", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryExceptThisQuestions(NotSelectEduQuestion notSelectEduQuestion) {
        Map<String, Object> result = null;
        try {
            Map<String,Object> params=new HashMap<>();
            result = eduQuestionService.queryExceptThisQuestions(notSelectEduQuestion);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryExceptThisQuestions方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * @Description 根据视频内容id查询题目
     * @author Joanne
     * @create 2018/6/28 11:37
     */
    @RequestMapping(value = "/queryVideoContentQuestion", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryVideoContentQuestion(String courseId, PageQueryEntity pageQueryEntity,String title) {
        try {
            Map<String,Object> questionInfo = programService.queryQuestionInfo(courseId,pageQueryEntity,title);
            return ResultUtil.createSuccessResult(questionInfo);
        } catch (Exception e) {
            logger.error("......查询题目方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    /**
     * @Description 上传试题视频
     * @author Joanne
     * @create 2018/7/17 19:03
     */
    @RequestMapping(value = "/uploadVideo")
    @ResponseBody
    public Map<String,Object> uploadVideo(Integer testId,MultipartFile file,String questionVideoPath){
        try{
            eduQuestionService.uploadVideo(testId,file,questionVideoPath);
            return ResultUtil.createSuccessResult();
        }catch(Exception e){
            logger.error("上传试题视频失败", e);
            return ResultUtil.createFailResult("上传试题..........异常"+e);
        }
    }
}
