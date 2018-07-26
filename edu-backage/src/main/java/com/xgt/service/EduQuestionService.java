package com.xgt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.NotSelectEduQuestion;
import com.xgt.entity.exam.ExamMock;
import com.xgt.entity.train.EduTraprogramExamCfg;
import com.xgt.entity.train.QuestionStatistics;
import com.xgt.enums.EnumCorrectFlag;
import com.xgt.enums.EnumDifficultyDegree;
import com.xgt.enums.EnumQuestionType;
import com.xgt.util.FileToolUtil;
import com.xgt.util.JSONUtil;
import com.xgt.util.ReadExcelUtils;
import com.xgt.util.StringUtil;
import com.xgt.util.wechat.DeleteFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.xgt.constant.SystemConstant.CHARSET_NAME;
import static com.xgt.constant.SystemConstant.UPLOADED_FILE_PATH;
import static com.xgt.constant.SystemConstant.VIDEO_QUESTION_PATH;

/**
 * @author cjy
 * @Date 2018/6/1 19:29.
 */
@Service
public class EduQuestionService {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionService.class);
    @Autowired
    private EduQuestionDao eduQuestionDao;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private EduQuestionAnswerService eduQuestionAnswerService ;

    @Autowired
    private ChapterContentDao chapterContentDao ;

    @Autowired
    private UploadService uploadService ;

    /**
     * 前台接口
     * 题目信息和当前题目的答案
     * @param eduQuestion
     * @return
     */
    public Map<String,Object> queryEduQuestionApi(EduQuestion eduQuestion) {
        List<EduQuestion> list = eduQuestionDao.queryEduQuestion(eduQuestion);
        logger.info("..............:{}", JSONArray.toJSONString(list));

        Integer total = eduQuestionDao.countEduQuestion(eduQuestion);
        List<EduQuestionAnswer> answerList = null;
        for(EduQuestion eduQuestion1:list){
            answerList = eduQuestionDao.queryEduQuestionAnswer(eduQuestion1.getId());
            eduQuestion1.setAnswers(answerList);

        }

        Map map = new HashMap();
        map.put("list",list);
        logger.info("..............:{}", JSONArray.toJSONString(list));
        JSONObject.toJSONString(list);
        map.put("total",total);
        return map;
    }

    /**
     * 后台方法
     * 检索题目和题目的总数
     * @param eduQuestion
     * @return
     */
    public Map<String,Object> queryEduQuestion(EduQuestion eduQuestion) {
        List<EduQuestion> list = eduQuestionDao.queryEduQuestion(eduQuestion);
        logger.info("..............:{}", JSONArray.toJSONString(list));
        Integer total = eduQuestionDao.countEduQuestion(eduQuestion);
        Map map = new HashMap();
        map.put("list",list);
        logger.info("..............:{}", JSONArray.toJSONString(list));
        JSONObject.toJSONString(list);
        map.put("total",total);
        return map;
    }

    /**
     * 修改题目
     * @param eduQuestion
     * @return
     */
    public Integer modifyEduQuestion(EduQuestion eduQuestion){
        return eduQuestionDao.modifyEduQuestion(eduQuestion);
    }

    /**
     * 添加题目
     * @param eduQuestion
     * @return
     */
    public Integer addEduQuestion(EduQuestion eduQuestion){
        return eduQuestionDao.addEduQuestion(eduQuestion);
    }

    /**
     * 删除题目
     * @param id
     * @return
     */
    public Integer deleteEduQuestion(Integer id){
        return eduQuestionDao.deleteEduQuestion(id);
    }

    /**
     * 查询所有题目列表（除了已添加）
     *
     * @param notSelectEduQuestion
     * @return
     */
    public Map<String, Object> queryExceptThisQuestions(NotSelectEduQuestion notSelectEduQuestion) {
        Integer total = eduQuestionDao.exceptThisQuestionsCount(notSelectEduQuestion);
        List<ExamMock> list = null;
        if (total != null && total > 0) {
            list = eduQuestionDao.queryExceptThisQuestions(notSelectEduQuestion);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, ExamMock.class));
        map.put("total", total);
        return map;
    }

    public void importVideoContentExcel(MultipartFile file, String fileName, Integer courseId, Integer loginUserId)throws IOException {
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
                        Integer column = 0 ;
                        // 题目信息对象
                        Map<Integer,Object> questionObj = map.get(i);

                        // 初始化题目信息
                        EduQuestion eduQuestion =  initEduQuestion(questionObj, loginUserId);

                        // 同一事物保存题目和答案
                        transactionTemplate.execute(new TransactionCallback<Boolean>() {
                            @Override
                            public Boolean doInTransaction(TransactionStatus status) {

                                // 保存题目信息
                                Integer questionId = saveQuestionAndQuestionOption(eduQuestion, questionObj);

                                //保存题目与视频的关系
                                chapterContentDao.saveVideoQuestionRel(courseId,questionId);
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
            }
        }
    }

    /**
     *  初始化 题目信息
     * @author liuao
     * @date 2018/7/13 16:57
     */
    public static EduQuestion initEduQuestion(Map<Integer,Object> questionObj ,Integer createUserId){
        EduQuestion eduQuestion = new EduQuestion();

        // 课程名称
        String lessonName = String.valueOf(questionObj.get(0));
        eduQuestion.setChapterContentTitle(lessonName);

        // 设置题目类型（1：单选、2：判断、3：多选）
        String questionType = String.valueOf(questionObj.get(1)) ;
        eduQuestion.setQuestionType(EnumQuestionType.enumOfByDesc(questionType).code);

        // 难易程度 1：简单、2：一般、3：困难
        String difficultyDegree = String.valueOf(questionObj.get(2));
        eduQuestion.setDifficultyDegree(EnumDifficultyDegree.enumOfByDesc(difficultyDegree).code);

        //题干
        String questionTitle = String.valueOf(questionObj.get(3));
        eduQuestion.setTitle(questionTitle);

        // 答案
        String trueAnswer = String.valueOf(questionObj.get(4));
        trueAnswer = StringUtil.str2SortStr(trueAnswer);
        eduQuestion.setTrueAnswer(trueAnswer);

        // 答案解析
        eduQuestion.setAnswerAnalysis(String.valueOf(questionObj.get(5)));// 答案解析

        eduQuestion.setCreateUserId(createUserId);// 创建人
        return eduQuestion;
    }

    /**
     *  保存题目信息和题目的
     * @author liuao
     * @date 2018/7/13 17:22
     */
    public  Integer saveQuestionAndQuestionOption(EduQuestion eduQuestion, Map<Integer, Object> questionObj){

        // 保存题目信息
        Integer questionId = addEduQuestion(eduQuestion);

        // 获取题目的选项
        List<EduQuestionAnswer> questionAnswerList = new ArrayList<>();
        for(int columIdx = 6 ;columIdx <= 10; columIdx++ ){
            // 初始化题目选项信息
            Object questionOption = questionObj.get(columIdx);
            if(null == questionOption ){
                continue;
            }
            String qoption  = String.valueOf(questionOption);
            if(StringUtils.isBlank(qoption)){
                continue;
            }

            EduQuestionAnswer questionAnswer = analysisEduQuestionAnswer(qoption,
                    questionId, eduQuestion.getTrueAnswer());
            if(null != questionAnswer){
                questionAnswerList.add(questionAnswer);
            }
        }

        // 批量保存题目的答案信息
        eduQuestionAnswerService.batchAddEduQuestionAnswer(questionAnswerList);

        return questionId;
    }


    /**
     *  初始化答案信息
     * @author liuao
     * @date 2018/6/9 18:10
     */
    public static EduQuestionAnswer analysisEduQuestionAnswer(String eduQuestionAnswerOption,Integer questionId, String trueAnswer){

        trueAnswer = trueAnswer.replace(SystemConstant.COMMA,StringUtils.EMPTY);

        eduQuestionAnswerOption = eduQuestionAnswerOption.replaceAll(" ",StringUtils.EMPTY);

        char [] trueAnswerChar = trueAnswer.toCharArray();
        List<String> trueAnswerList = StringUtil.charArr2StringList(trueAnswerChar);

        if(StringUtils.isNotBlank(eduQuestionAnswerOption)) {

            int firstIdx = eduQuestionAnswerOption.indexOf(SystemConstant.SPOT);
            int answerLen = eduQuestionAnswerOption.length();

            // 选项 ABC...
            String optionCode = eduQuestionAnswerOption.substring(0, 1);
            // 选项内容
            String optionContent = eduQuestionAnswerOption.substring(2);
            String correctFlag = EnumCorrectFlag.WRONG.code;
            if(trueAnswerList.contains(optionCode)){
                correctFlag = EnumCorrectFlag.RIGHT.code;
            }

            EduQuestionAnswer eduQuestionAnswer = new EduQuestionAnswer();
            eduQuestionAnswer.setOptionCode(optionCode);// 设置答案选项（eg : A \ B\ C ...）
            eduQuestionAnswer.setOptionContent(optionContent);// 选项内容
            eduQuestionAnswer.setCorrectFlag(correctFlag);// 正确标识
            eduQuestionAnswer.setQuestionId(questionId);
            return eduQuestionAnswer;
        }
        return null ;
    }

    /**
     * @Description 创建视频题目
     * @author Joanne
     * @create 2018/7/12 12:21
     */
    public void createVideoQus(EduQuestion eduQuestion, MultipartFile multipartFile, Integer courseId) {
        //上传图片至oss
        String filePath = uploadService.uploadToOSS(multipartFile,VIDEO_QUESTION_PATH);
        if (filePath!=null){
            eduQuestion.setQuestionVideoPath(filePath);
            transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus transactionStatus) {
                    //保存题目
                    Integer questionId = eduQuestionDao.addEduQuestion(eduQuestion);
                    //保存题目与视频的关系
                    chapterContentDao.saveVideoQuestionRel(courseId,questionId);
                    return true;
                }
            });
        }
    }

    /**
     * @Description 查询组装自动组卷配置
     * @author Joanne
     * @create 2018/7/12 10:46
     */
    public EduTraprogramExamCfg queryEduTraprogramExamCfg(String ids) {
        List<QuestionStatistics> questions = eduQuestionDao.statisticQuestionByCourseIds(ids);
        EduTraprogramExamCfg eduTraprogramExamCfg = new EduTraprogramExamCfg() ;
        questions.forEach(q->{
            Integer questionAmount = q.getQuestionAmount();
            switch (EnumDifficultyDegree.enumOfByCode(q.getDifficultyDegree())){
                case SIMPLE : switch (EnumQuestionType.enumOfByCode(q.getQuestionType())){
                    case SINGLE: eduTraprogramExamCfg.setSingleSimpleTotal(questionAmount);break;
                    case MULTI_SELECTION: eduTraprogramExamCfg.setMultiSimpleTotal(questionAmount);break;
                };break;
                case MEDIUM : switch (EnumQuestionType.enumOfByCode(q.getQuestionType())){
                    case SINGLE: eduTraprogramExamCfg.setSingleMediumTotal(questionAmount);break;
                    case MULTI_SELECTION: eduTraprogramExamCfg.setMultiMediumTotal(questionAmount);break;
                };break;
                case DIFFICULTY : switch (EnumQuestionType.enumOfByCode(q.getQuestionType())){
                    case SINGLE: eduTraprogramExamCfg.setSingleDifficultyTotal(questionAmount);break;
                    case MULTI_SELECTION: eduTraprogramExamCfg.setMultiDifficultyTotal(questionAmount);break;
                };break;
            }

        });
        eduTraprogramExamCfg.setQuestionAmount(eduTraprogramExamCfg.getSingleSimpleTotal()
                                              + eduTraprogramExamCfg.getSingleMediumTotal()
                                              + eduTraprogramExamCfg.getSingleDifficultyTotal()
                                              + eduTraprogramExamCfg.getMultiSimpleTotal()
                                              + eduTraprogramExamCfg.getMultiMediumTotal()
                                              + eduTraprogramExamCfg.getMultiDifficultyTotal());
        return eduTraprogramExamCfg ;
    }

    /**
     * @Description 上传试题视屏
     * @author Joanne
     * @create 2018/7/17 19:04
     */
    public void uploadVideo(Integer testId, MultipartFile file,String oldPath) {
        //删除原有视频
        if(StringUtils.isNotBlank(oldPath)){
            uploadService.deleteOssFile(oldPath);
        }
        String filePath = uploadService.uploadToOSS(file,VIDEO_QUESTION_PATH);
        EduQuestion eduQuestion = new EduQuestion();
        eduQuestion.setId(testId);
        eduQuestion.setQuestionVideoPath(filePath);
        eduQuestionDao.modifyEduQuestion(eduQuestion);
    }
}
