package com.xgt.service.train;

import com.xgt.common.PageQueryEntity;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.EduQuestionAnswerDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.exam.ExamDao;
import com.xgt.dao.train.PlanDao;
import com.xgt.dao.train.ProgramDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dto.EduPlanDto;
import com.xgt.dto.RandomQuestionParam;
import com.xgt.entity.EduExamQuestionRel;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.train.*;
import com.xgt.entity.video.ChapterContent;
import com.xgt.enums.EnumDifficultyDegree;
import com.xgt.enums.EnumQuestionType;
import com.xgt.service.UploadService;
import com.xgt.service.exam.ExamService;
import com.xgt.service.video.ChapterContentService;
import com.xgt.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.xgt.constant.SystemConstant.*;
import static com.xgt.constant.SystemConstant.TypeCode.PROGRAM_TYPE;
import static com.xgt.constant.SystemConstant.ExamType.TRAIN;
import static com.xgt.util.Base64Util.getImgBase64;

import java.math.BigDecimal;

/**
 *  培训方案 service 层
 * @author Joanne
 * @Description
 * @create 2018-06-26 17:50
 **/
@Service
public class ProgramService {
    private final static Logger logger = LoggerFactory.getLogger(ProgramService.class);

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private ProgramDao programDao ;

    @Autowired
    private ExamDao examDao ;

    @Autowired
    private UploadService uploadService ;

    @Autowired
    private EduQuestionDao eduQuestionDao ;

    @Autowired
    private PlanDao planDao ;

    @Autowired
    private ChapterContentDao chapterContentDao ;

    @Autowired
    private EduQuestionAnswerDao eduQuestionAnswerDao ;


    /**
     * @Description 培训系统PC版 - 获取方案信息
     * @author Joanne
     * @create 2018/6/27 15:42
     */
    public Map<String,Object> queryProgramInfo(EduTrainProgram eduTrainProgram) {
        Map<String,Object> programInfo = new HashMap<>();
        //判断是查询详情，还是查询列表
        if(eduTrainProgram.getId()!=null){
            programInfo = queryDetail(eduTrainProgram);
        }else {
            programInfo = queryList(eduTrainProgram);
        }
        return programInfo ;
    }

    /**
     * @Description 查询方案列表
     * @author Joanne
     * @create 2018/7/16 19:23
     */
    public Map<String,Object> queryList(EduTrainProgram eduTrainProgram){
        Map<String,Object> programInfo = new HashMap<>();
        List<EduTrainProgram> eduTrainPrograms = null ;
        Integer totalPrograms = programDao.countProgram(eduTrainProgram);
        eduTrainPrograms = programDao.queryProgramInfo(eduTrainProgram);
        for (EduTrainProgram eduTrainProg:eduTrainPrograms){
            eduTrainProg.setProgramTypeTxt(DictionaryDao.getDictionaryText(PROGRAM_TYPE+eduTrainProg.getProgramType()));
        }
        programInfo.put("total",totalPrograms);
        programInfo.put("data",eduTrainPrograms);
        return programInfo ;
    }

    /**
     * @Description 查询方案详情
     * @author Joanne
     * @create 2018/7/16 19:24
     */
    public Map<String,Object> queryDetail(EduTrainProgram eduTrainProgram){
        Map<String,Object> programInfo = new HashMap<>();
        List<EduTrainProgram> eduTrainPrograms = null ;
        //查询方案
        eduTrainPrograms = programDao.queryProgramInfoById(eduTrainProgram.getId(),eduTrainProgram.getCompanyId());
        if(eduTrainPrograms.size()!=0){
            eduTrainPrograms.get(0).setProgramTypeTxt(PROGRAM_TYPE+eduTrainPrograms.get(0).getProgramType());
        }
        //查询课程
        List<ChapterContent> courses = chapterContentDao.queryContentByProgramId(eduTrainProgram.getId());
//        EduExamTrain
        eduTrainProgram.setChapterContents(courses);
        programInfo.put("data",eduTrainPrograms);
        return programInfo ;
    }

    /**
     * @Description 培训系统PC版 - 新增方案
     * @author Joanne
     * @create 2018/6/27 9:37
     */
    public void addProgram(EduTrainProgram eduTrainProgram,EduExamTrain eduExamTrain ,
                           String data,String testIds, MultipartFile multipartFile,
                           Boolean autoFlag,EduTraprogramExamCfg eduTraprogramExamCfg)throws Exception {


        //保存封面图
        String coverPath = uploadService.uploadToLocal(multipartFile,TRAIN_FOLDER);
        String base64PicFile = null ;
        if(coverPath!=null){
            eduTrainProgram.setCoverPath(coverPath);
            logger.info("文件转换为base64，文件路径:...{}.....",coverPath);
            base64PicFile = getImgBase64(DISC + coverPath);
        }else {
            eduTrainProgram.setCoverPath(DEFAULT_PIC);
            logger.info("文件转换为base64，文件路径:...{}.....",DEFAULT_PIC);
            base64PicFile = getImgBase64(DISC + DEFAULT_PIC);
        }

        eduTrainProgram.setCoverBase64(base64PicFile);

        List<EduExamQuestionRel> examQuestionRelList ;
        if(!autoFlag){
            // 手动组卷
            examQuestionRelList = ExamService.initExamQuestionRel(testIds.split(COMMA));
        }else{
            // 自动组卷
            examQuestionRelList = initExamQuestionRelForAuto(eduTraprogramExamCfg);
        }

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //新建方案
                Integer programId = programDao.saveProgram(eduTrainProgram);

                //新建方案试卷
                eduExamTrain.setTrainProgramId(programId);
                Integer programTestPaperId = programDao.saveProgramTest(eduExamTrain);

                //保存试卷题目关系
                examDao.addQuestions(programTestPaperId, TRAIN, examQuestionRelList);
                //解析视频集锦与视频内容id JSON串
                List<LinkedHashMap> eduVideoIdAndCourseIdList = JSONUtil.parseJSONArrayStringToList(data);

                for (Map obj:eduVideoIdAndCourseIdList){
                    //保存课程方案
                    programDao.saveTrainLesson(programId,(Integer)obj.get("eduVideoId"), Arrays.asList(((String)obj.get("courseIds")).split(COMMA)));
                }
                // 如果是自动组卷，则保存自动组卷配值
                if(autoFlag){
                    eduTraprogramExamCfg.setTrainProgramId(programId);
                    programDao.savetraProgramExamCfg(eduTraprogramExamCfg);
                }
                return true;
            }
        });
    }

    /**
     * @Description 培训系统PC版 - 根据课程id获取题目
     * @author Joanne
     * @create 2018/6/27 19:06
     */
    public Map<String,Object> queryQuestionInfo(String courseIds,PageQueryEntity pageQueryEntity,String testName) {
        //根据课程id获取题目list
        List<EduQuestion> eduQuestions = eduQuestionDao.queryEduQuestionByCourseIds(courseIds,pageQueryEntity.getPageOffset(),pageQueryEntity.getPageSize(),testName);
        eduQuestions.forEach(eduQuestion -> {
            EduQuestionAnswer eduQuestionAnswer = new EduQuestionAnswer();
            eduQuestionAnswer.setQuestionId(eduQuestion.getId());
            List<EduQuestionAnswer> myAnswers = eduQuestionAnswerDao.queryEduQuestionAnswer(eduQuestionAnswer);
            String answerString = "";
            for (EduQuestionAnswer myAnswer:myAnswers){
                answerString += myAnswer.getOptionCode() + myAnswer.getOptionContent() + OBLIQUE;
            }
            eduQuestion.setAnswerString(answerString.substring(0,answerString.lastIndexOf(OBLIQUE)));
        });
        Integer totalQuestion = eduQuestionDao.countQuestionByCourseIds(courseIds);
        Map<String,Object> questionInfo = new HashMap<>();
        questionInfo.put("total",totalQuestion);
        questionInfo.put("list",eduQuestions);
        return  questionInfo ;
    }

    /**
     *  计算各题型、不同难易程度的分数
     * @author liuao
     * @date 2018/6/27 9:40
     */
    public Map<String,BigDecimal> calculationQuestionScore(EduTraprogramExamCfg eduTraprogramExamCfg){

        // 单选 - 简单 - 总题数
        Integer singleSimpleTotal = eduTraprogramExamCfg.getSingleSimpleTotal();
        // 单选-简单-选题数
        Integer singleSimpleSelect = eduTraprogramExamCfg.getSingleSimpleSelect();
        // 单选 - 简单 - 分数比重
        Integer singleSimpleRatio = eduTraprogramExamCfg.getSingleSimpleRatio();

        // 单选 - 中等- 总题数
        Integer singleMediumTotal = eduTraprogramExamCfg.getSingleMediumTotal();
        // 单选 - 中等 - 选题数
        Integer singleMediumSelect = eduTraprogramExamCfg.getSingleMediumSelect();
        // 单选- 中等 - 分数比重
        Integer singleMediumRatio = eduTraprogramExamCfg.getSingleMediumRatio();

        // 单选-困难- 总题数
        Integer singleDifficultyTotal = eduTraprogramExamCfg.getSingleDifficultyTotal();
        // 单选-困难-选题数
        Integer singleDifficultySelect = eduTraprogramExamCfg.getSingleDifficultySelect();
        // 单选-困难-分数权重
        Integer singleDifficultyRatio = eduTraprogramExamCfg.getSingleDifficultyRatio();


        // 多选 - 简单 - 总题数
        Integer multiSimpleTotal = eduTraprogramExamCfg.getMultiSimpleTotal();
        // 多选-简单-选题数
        Integer multiSimpleSelect = eduTraprogramExamCfg.getMultiSimpleSelect();
        // 多选 - 简单 - 分数比重
        Integer multiSimpleRatio = eduTraprogramExamCfg.getMultiSimpleRatio();

        // 多选 - 中等- 总题数
        Integer multiMediumTotal = eduTraprogramExamCfg.getMultiMediumTotal();
        // 多选 - 中等 - 选题数
        Integer multiMediumSelect = eduTraprogramExamCfg.getMultiMediumSelect();
        // 多选- 中等 - 分数比重
        Integer multiMediumRatio = eduTraprogramExamCfg.getMultiMediumRatio();

        // 多选-困难- 总题数
        Integer multiDifficultyTotal = eduTraprogramExamCfg.getMultiDifficultyTotal();
        // 多选-困难-选题数
        Integer multiDifficultySelect = eduTraprogramExamCfg.getMultiDifficultySelect();
        // 多选-困难-分数权重
        Integer multiDifficultyRatio = eduTraprogramExamCfg.getMultiDifficultyRatio();

        // 总选题数
        BigDecimal totalSelect = BigDecimal.ZERO;
        BigDecimal totalRatio = BigDecimal.ZERO;

        totalSelect = totalSelect.add(BigDecimal.valueOf(singleSimpleSelect)).
                add(BigDecimal.valueOf(singleMediumSelect)).add(BigDecimal.valueOf(singleDifficultySelect));

        // 总的权重
        totalRatio = totalRatio.add(BigDecimal.valueOf(singleSimpleRatio)).
                add(BigDecimal.valueOf(singleMediumRatio)).
                add(BigDecimal.valueOf(singleDifficultyRatio)).
                add(BigDecimal.valueOf(multiSimpleRatio)).
                add(BigDecimal.valueOf(multiMediumRatio)).
                add(BigDecimal.valueOf(multiDifficultyRatio));

        // 单位权重分值
        BigDecimal unitScore = TOTAL_SCORE.divide(totalRatio, 0,BigDecimal.ROUND_HALF_UP);

        // 单选-简单-每题分数
        BigDecimal singleSimpleScore = unitScore.multiply(BigDecimal.valueOf(singleSimpleRatio)).
                divide(BigDecimal.valueOf(singleSimpleSelect),0, BigDecimal.ROUND_HALF_UP);
        // 单选-中等-每题分数
        BigDecimal singleMediumScore = unitScore.multiply(BigDecimal.valueOf(singleMediumRatio)).
                divide(BigDecimal.valueOf(singleMediumSelect),0, BigDecimal.ROUND_HALF_UP);
        // 单选-困难-每题分数
        BigDecimal singleDifficultyScore = unitScore.multiply(BigDecimal.valueOf(singleDifficultyRatio)).
                divide(BigDecimal.valueOf(singleDifficultySelect),0, BigDecimal.ROUND_HALF_UP);

        // 多选-简单-每题分数
        BigDecimal multiSimpleScore = unitScore.multiply(BigDecimal.valueOf(multiSimpleRatio)).
                divide(BigDecimal.valueOf(multiSimpleSelect),0, BigDecimal.ROUND_HALF_UP);
        // 多选-中等-每题分数
        BigDecimal multiMediumScore = unitScore.multiply(BigDecimal.valueOf(multiMediumRatio)).
                divide(BigDecimal.valueOf(multiMediumSelect),0, BigDecimal.ROUND_HALF_UP);
        // 多选-困难-每题分数
        BigDecimal multiDifficultyScore = unitScore.multiply(BigDecimal.valueOf(multiDifficultyRatio)).
                divide(BigDecimal.valueOf(multiDifficultySelect),0, BigDecimal.ROUND_HALF_UP);

        // 分值map ,key 位 题型 + 难易程度
        Map<String,BigDecimal>  scoreMap = new HashMap<>();
        scoreMap.put(EnumQuestionType.SINGLE.code + EnumDifficultyDegree.SIMPLE.code, singleSimpleScore);
        scoreMap.put(EnumQuestionType.SINGLE.code + EnumDifficultyDegree.MEDIUM.code, singleMediumScore);
        scoreMap.put(EnumQuestionType.SINGLE.code + EnumDifficultyDegree.DIFFICULTY.code, singleDifficultyScore);

        scoreMap.put(EnumQuestionType.MULTI_SELECTION.code + EnumDifficultyDegree.SIMPLE.code, multiSimpleScore);
        scoreMap.put(EnumQuestionType.MULTI_SELECTION.code + EnumDifficultyDegree.MEDIUM.code, multiMediumScore);
        scoreMap.put(EnumQuestionType.MULTI_SELECTION.code + EnumDifficultyDegree.DIFFICULTY.code, multiDifficultyScore);

        return scoreMap ;
    }
    /**
     *  根据自动组卷配置，设置 题目id 和 该题的分值
     * @author liuao
     * @date 2018/6/29 11:03
     */
    private List<EduExamQuestionRel> initExamQuestionRelForAuto(EduTraprogramExamCfg eduTraprogramExamCfg) {

        // 单选-简单-选题数
        Integer singleSimpleSelect = eduTraprogramExamCfg.getSingleSimpleSelect();
        // 单选 - 中等 - 选题数
        Integer singleMediumSelect = eduTraprogramExamCfg.getSingleMediumSelect();
        // 单选-困难-选题数
        Integer singleDifficultySelect = eduTraprogramExamCfg.getSingleDifficultySelect();

        // 多选-简单-选题数
        Integer multiSimpleSelect = eduTraprogramExamCfg.getMultiSimpleSelect();
        // 多选 - 中等 - 选题数
        Integer multiMediumSelect = eduTraprogramExamCfg.getMultiMediumSelect();
        // 多选-困难-选题数
        Integer multiDifficultySelect = eduTraprogramExamCfg.getMultiDifficultySelect();

        // 初始化单个随机取题参数
        RandomQuestionParam singleSimpleQuestionParam =
                new RandomQuestionParam(EnumQuestionType.SINGLE.code,
                        EnumDifficultyDegree.SIMPLE.code, singleSimpleSelect);

        RandomQuestionParam singleMediumQuestionParam =
                new RandomQuestionParam(EnumQuestionType.SINGLE.code,
                        EnumDifficultyDegree.MEDIUM.code, singleMediumSelect);

        RandomQuestionParam singleDifficultyQuestionParam =
                new RandomQuestionParam(EnumQuestionType.SINGLE.code,
                        EnumDifficultyDegree.DIFFICULTY.code, singleDifficultySelect);

        RandomQuestionParam multiSimpleQuestionParam =
                new RandomQuestionParam(EnumQuestionType.SINGLE.code,
                        EnumDifficultyDegree.SIMPLE.code, multiSimpleSelect);

        RandomQuestionParam multiMediumQuestionParam =
                new RandomQuestionParam(EnumQuestionType.SINGLE.code,
                        EnumDifficultyDegree.MEDIUM.code, multiMediumSelect);

        RandomQuestionParam multiDifficultyQuestionParam =
                new RandomQuestionParam(EnumQuestionType.MULTI_SELECTION.code,
                        EnumDifficultyDegree.DIFFICULTY.code, multiDifficultySelect);

        // 随机取题参数加入到list
        List<RandomQuestionParam> randomQuestionParamList = new ArrayList<>();
        randomQuestionParamList.add(singleSimpleQuestionParam);
        randomQuestionParamList.add(singleMediumQuestionParam);
        randomQuestionParamList.add(singleDifficultyQuestionParam);
        randomQuestionParamList.add(multiSimpleQuestionParam);
        randomQuestionParamList.add(multiMediumQuestionParam);
        randomQuestionParamList.add(multiDifficultyQuestionParam);

        // 题目id 、题目分支对象 list （用于保存试卷题目关系使用）
        List<EduExamQuestionRel> eduExamQuestionRelList = new ArrayList<>();

        // 获取不同提醒和难易程度对应的分值
        Map<String, BigDecimal> scoreMap = calculationQuestionScore(eduTraprogramExamCfg);

        for (RandomQuestionParam randomQuestionParam : randomQuestionParamList) {
            List<EduQuestion> questionList = eduQuestionDao.queryRandomQuestion(randomQuestionParam);
            for (EduQuestion question : questionList) {
                EduExamQuestionRel eduExamQuestionRel = new EduExamQuestionRel();

                eduExamQuestionRel.setQuestionId(question.getId());
                // 获取分值
                String questionType = question.getQuestionType();
                String difficultyDegree = question.getDifficultyDegree();
                eduExamQuestionRel.setQuestionScore(scoreMap.get(questionType + difficultyDegree));

                eduExamQuestionRelList.add(eduExamQuestionRel);
            }
        }
        return eduExamQuestionRelList;
    }

    /**
     * @Description 删除方案
     * @author Joanne
     * @create 2018/7/11 14:25
     */
    public void deleteProgram(Integer programId) {
        //根据方案ID 查询方案试卷
        Integer programTestPaperId = programDao.queryProgramTestPaperId(programId);
        //查询封面并删除
        String coverPath =  programDao.queryProgramCoverPath(programId);
        if (coverPath!=null&&(!coverPath.equals(DEFAULT_PIC))){
            uploadService.deleteLocalFile(coverPath);
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //删除自动组卷配置
                programDao.deleteCfg(programId);
                //删除课程方案
                programDao.deleteCourseProgram(programId);
                //删除方案试卷关系
                programDao.deleteProgramTestPaperRel(programTestPaperId,TRAIN);
                //删除方案试卷
                programDao.deleteProgramTestPaper(programId);
                //删除方案计划
                planDao.deletePlanByProgramId(programId);
                //删除方案
                programDao.deleteProgram(programId);
                return true;
            }
        });
    }


    /**
     * @Description 方案修改
     * @author Joanne
     * @create 2018/7/12 15:44
     */
    public void updateProgram(EduTrainProgram eduTrainProgram, MultipartFile multipartFile) {
        //上传新封面
        String newPath = uploadService.uploadToLocal(multipartFile,TRAIN_FOLDER);
        if(newPath!=null){
            //根据方案ID查询原封面地址
            String oldCoverPath = programDao.queryProgramCoverPath(eduTrainProgram.getId());
            if(oldCoverPath!=null && (!oldCoverPath.equals(DEFAULT_PIC))){
                //本地删除原图
                uploadService.deleteLocalFile(oldCoverPath);
            }
            //填充新封面
            eduTrainProgram.setCoverPath(newPath);
        }

        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                //更新方案
                programDao.updateProgram(eduTrainProgram);

                if(eduTrainProgram.getProgramName()!=null){
                    EduTrainPlan eduTrainPlan = new EduTrainPlan();
                    eduTrainPlan.setProgramId(eduTrainProgram.getId());
                    eduTrainPlan.setProgramName(eduTrainProgram.getProgramName());
                    //更新计划的方案名称
                    planDao.updatePlan(eduTrainPlan);
                }
                return true;
            }
        });
    }
}
