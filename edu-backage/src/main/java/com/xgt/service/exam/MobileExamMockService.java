package com.xgt.service.exam;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.EduQuestionAnswerDao;
import com.xgt.dao.EduQuestionExerciseDao;
import com.xgt.dao.exam.ExamDao;
import com.xgt.dao.exam.MobileExamMockDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.exam.AnswerCard;
import com.xgt.entity.exam.ExamMock;
import com.xgt.entity.exam.ExamLabourerRel;
import com.xgt.util.DateUtil;
import com.xgt.util.ResultUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

import static com.xgt.constant.SystemConstant.COMMA;

@Service
public class MobileExamMockService {

    private static final Logger logger = LoggerFactory.getLogger(MobileExamMockService.class);

    @Autowired
    private MobileExamMockDao mobileExamMockDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EduQuestionAnswerDao eduQuestionAnswerDao;
    @Autowired
    private EduQuestionExerciseDao eduQuestionExerciseDao ;

    @Autowired
    private ExamDao examDao;

    /**
     * 手机端模拟考试查询
     *
     * @param labourerUserId
     * @param examType
     * @return
     */
    public List<ExamMock> queryMobileMockList(Integer labourerUserId, String examType) {
        //手机端模拟试卷信息列表查询
        List<ExamMock> examMocks = mobileExamMockDao.queryExamMockList(labourerUserId);

        //建筑工地人员模拟考试信息查询
        List<ExamLabourerRel> examMockLabourers = mobileExamMockDao.queryExamMockLabourers(labourerUserId, examType);

        //通过
        List<Integer> pass = new ArrayList<>();
        //未通过
        List<Integer> noPass = new ArrayList<>();
        //存放
        Map<Integer, Object> map = new HashMap<>();

        //遍历集合获取不同状态的集合
        for (ExamLabourerRel examLabourerRel : examMockLabourers) {
            Integer examId = examLabourerRel.getExamId();
            map.put(examId, examLabourerRel.getExamScore());
            if (SystemConstant.passStatus.PASS.equals(examLabourerRel.getPassStatus())) {
                pass.add(examId);
            } else if (SystemConstant.passStatus.NOPASS.equals(examLabourerRel.getPassStatus())) {
                noPass.add(examId);
            }
        }

        //遍历集合打标记（是否通过）
        for (ExamMock examMock : examMocks) {
            Integer examId = examMock.getId();
            if (pass.contains(examId)) {
                examMock.setPassStatus(SystemConstant.passStatus.PASS);
                examMock.setExamScore(MapUtils.getInteger(map, examId));
            } else if (noPass.contains(examId)) {
                examMock.setPassStatus(SystemConstant.passStatus.NOPASS);
                examMock.setExamScore(MapUtils.getInteger(map, examId));
            } else {
                examMock.setPassStatus(SystemConstant.passStatus.NOTHING);
            }
        }
        return examMocks;
    }

    /**
     * 手机端模拟考试试题查询
     *
     * @param examId
     * @param upOrDownStatus
     * @param examType
     * @param questionId
     * @return
     */
    public  EduQuestion  queryMobileMockQuestions(Integer labourerId, Integer examId, String examType,
                                                      String upOrDownStatus, Integer questionId) {

        //第一次查询的时候要判断答题卡和考试结果是否存在
        //是：不创建
        //否：一次性创建
        //创建考试人员考试结果

        Integer cardCount = mobileExamMockDao.queryAnswerCardCount(labourerId, examId, examType);
        Integer relCount = mobileExamMockDao.queryRelCount(labourerId, examId, examType);

        //获取题目ID
        List<Integer> questionIds = mobileExamMockDao.queryQuestionIds(examId, examType);

        if (cardCount <= 0 && relCount <= 0) {
            transactionTemplate.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                    //创建答题卡
                    mobileExamMockDao.createAnswerCard(labourerId, examId, examType, questionIds);
                    //创建考试人员考试结果
                    mobileExamMockDao.createExamLabourerRel(labourerId, examId, examType);
                    return true;
                }
            });
        } else {
            if (questionId == null) {
                //如果已经存在答题卡和考试结果
                //清除答题卡和考试结果
                transactionTemplate.execute(new TransactionCallback<Object>() {
                    @Override
                    public Object doInTransaction(TransactionStatus transactionStatus) {
                        mobileExamMockDao.cleanAnswerCard(labourerId, examId, examType);
                        mobileExamMockDao.cleanExamLabourerRel(labourerId, examId, examType);
                        return true;
                    }
                });
            }
        }

        //如果questionId为空设置默认值
        if (questionId == null) {
            questionId = -1;
        }

        EduQuestion eduQuestion = mobileExamMockDao.queryMobileMockQuestions(examId, examType, upOrDownStatus, questionId);

        // 初始化题目的选项，我的答案、考试剩余时间
        eduQuestion = initMyAnswerAndOption(eduQuestion, labourerId, examId, examType);

        return eduQuestion;
    }

    /**
     * 手机端答题卡查询接口
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public Map<String, Object> queryAnswerCard(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> result = new HashMap<>();
        //答题卡
        List<AnswerCard> answerCards = mobileExamMockDao.queryAnswerCard(labourerId, examId, examType);
        //考试结果
        ExamLabourerRel examLabourerRel = mobileExamMockDao.queryExamLabourerRel(labourerId, examId, examType);
        Integer trueQuestionCount = mobileExamMockDao.trueQuestionCount(labourerId, examId, examType, SystemConstant.correctFlag.TRUE);
        examLabourerRel.setTrueQuestionCount(trueQuestionCount);

        //秒转换分：秒（00:00）
        Integer consumeTime = examLabourerRel.getConsumeTime();
        if (consumeTime == null) {
            consumeTime = 0;
        }
        String mmss = DateUtil.getMmss(consumeTime);
        examLabourerRel.setStrConsumeTime(mmss);


        //考试剩余时间
        Integer hasedTime = mobileExamMockDao.queryHasedTime(labourerId, examId, examType);
        hasedTime = hasedTime.compareTo(0) < 0 ? 0 : hasedTime ;

        result.put("answerCards", answerCards);// 答案卡
        result.put("examLabourerRel", examLabourerRel);// 考试结果
        result.put("hasedTime", hasedTime);

        return result;
    }

    /**
     * 保存答案
     *
     * @param labourerId
     * @param examId
     * @param questionId
     * @param myAnswer
     * @param examType
     */
    public Map saveAnswer(Integer labourerId, Integer examId, Integer questionId,
                           String examType, String myAnswer) {

        //实际考试耗时（秒）
        Integer consumeTime = queryConsumeTime(labourerId, examId, SystemConstant.ExamType.MOCK);
        ExamMock examMock = examDao.queryExamMockById(examId);
        //考试时长（秒）
        Integer examTime = examMock.getConsumeMinute() * 60;

        //判断考试是否超时返回超时信息
        if (consumeTime.compareTo(examTime) > 0) {
            logger.info("考试时间已超时！");
            return ResultUtil.createFailResult("考试时间已超时！");
        }

        //考试结果
        ExamLabourerRel examLabourerRel = mobileExamMockDao.queryExamLabourerRel(labourerId, examId, examType);
        logger.info(".......交卷时间：{}", examLabourerRel.getCompleteTime());
        // 完成时间存在，表示“已交卷”
        if(StringUtils.isNotBlank(examLabourerRel.getCompleteTime())){
            logger.info("已交卷的试卷，无法更改答案！");
            return ResultUtil.createFailResult("已交卷的试卷，无法更改答案！");
        }

        //查询出题目对应的正确答案
        String correctAnswer = eduQuestionExerciseDao.getRightAnswer(questionId);
        logger.info("...........myanswer:{},....correctAnswer:{}",myAnswer, correctAnswer);
        //我的答案与正确答案进行对比
        String answerStatus = null;
        if (myAnswer.equals(correctAnswer)) {
            answerStatus = SystemConstant.correctFlag.TRUE;
        } else {
            answerStatus = SystemConstant.correctFlag.FALSE;
        }

        mobileExamMockDao.saveAnswer(labourerId, examId, questionId, examType,
                myAnswer, answerStatus);

        return ResultUtil.createSuccessResult("答题成功！");
    }

    /**
     * 进行数据组装（"A,B,..."）
     *
     * @param list
     * @return
     */
    public StringBuilder getAnswerStr(List<EduQuestionAnswer> list) {
        StringBuilder trueAnswer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i).getOptionCode();
            if (i < list.size() - 1) {
                trueAnswer.append(code + ",");
            } else {
                trueAnswer.append(code);
            }
        }
        return trueAnswer;
    }

    /**
     * 实际考试耗时（秒）
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public Integer queryConsumeTime(Integer labourerId, Integer examId, String examType) {
        return mobileExamMockDao.queryConsumeTime(labourerId, examId, examType);
    }

    /**
     * 交卷接口
     *
     * @param labourerId
     * @param examId
     * @param examType
     */
    public Map handInExamMock(Integer labourerId, Integer examId, String examType) {
        //考试结果
        ExamLabourerRel examLabourerRel = mobileExamMockDao.queryExamLabourerRel(labourerId, examId, examType);
        // 完成时间存在，表示“已交卷”
        if(StringUtils.isNotBlank(examLabourerRel.getCompleteTime())){
            logger.info("已交卷的试卷，无法重复交卷！");
            return ResultUtil.createFailResult("已交卷的试卷，无法重复交卷！");
        }

        //获取试卷详情
        ExamMock examMock = examDao.queryExamMockById(examId);
        //总分
        Integer totalScore = examMock.getTotalScore();
        //及格分
        Integer passScore = examMock.getPassScore();
        //正确题数
        Integer trueQuestionCount = mobileExamMockDao.trueQuestionCount(labourerId, examId, examType, SystemConstant.correctFlag.TRUE);
        //考试得分
        Integer examScore = trueQuestionCount * (totalScore / examMock.getQuestionNumber());
        //实际考试耗时（秒）
        Integer consumeTime = mobileExamMockDao.queryConsumeTime(labourerId, examId, examType);
        //考试时长（秒）
        Integer examTime = examMock.getConsumeMinute() * 60;
        //是否通过
        String passStatus = null;
        if (examScore.compareTo(passScore) >= 0 && consumeTime.compareTo(examTime) <= 0) {
            passStatus = SystemConstant.passStatus.PASS;
        } else {
            passStatus = SystemConstant.passStatus.NOPASS;
        }

        //保存交卷信息
        ExamLabourerRel newExamLabourerRel = new ExamLabourerRel();
        newExamLabourerRel.setExamId(examId);
        newExamLabourerRel.setExamType(examType);
        newExamLabourerRel.setLabourerId(labourerId);
        newExamLabourerRel.setExamScore(examScore);
        newExamLabourerRel.setPassStatus(passStatus);
        newExamLabourerRel.setConsumeTime(consumeTime);
        mobileExamMockDao.handInExamMock(newExamLabourerRel);

        return ResultUtil.createSuccessResult("交卷成功");
    }

    /**
     * 查询答案及解析
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public EduQuestion queryAnswerAndParsing(Integer labourerId, Integer examId, String examType, String upOrDownStatus, Integer questionId) {
        //如果questionId为空设置默认值
        if (questionId == null) {
            questionId = -1;
            upOrDownStatus = SystemConstant.NextPrevFlag.NEXT_FLAG;
        }

        EduQuestion eduQuestion = mobileExamMockDao.queryMobileMockQuestions(examId, examType, upOrDownStatus, questionId);
        // 初始化题目的选项，我的答案、正确答案 、 考试剩余时间
        eduQuestion = initMyAnswerAndOption(eduQuestion, labourerId, examId, examType);

        return eduQuestion;
    }

    /**
     * 手机端 - 根据题目id ,查询单个模拟考试试题详情查询
     *
     * @param examId
     * @param examType
     * @param questionId
     * @return
     */
    public EduQuestion queryMockQuestionById(Integer examId, String examType, Integer questionId, Integer labourerId) {
        //查询模拟考试试题单个
        EduQuestion eduQuestion = mobileExamMockDao.queryMockQuestionById(examId, examType, questionId);

        // 初始化题目的选项，我的答案、正确答案 、 考试剩余时间
        eduQuestion = initMyAnswerAndOption(eduQuestion, labourerId, examId, examType);

        return eduQuestion;
    }
    /**
     *  初始化题目的选项，我的答案、考试剩余时间
     * @author liuao
     * @date 2018/6/14 11:31
     */
    private EduQuestion initMyAnswerAndOption(EduQuestion eduQuestion, Integer labourerId,
                                              Integer examId, String examType){
        //查询选项
        List<EduQuestionAnswer> eduQuestionAnswer = mobileExamMockDao.queryQuestionAnswer(eduQuestion.getId());
        eduQuestion.setAnswers(eduQuestionAnswer);

        //我的答案
        String myAnswer = mobileExamMockDao.queryMyAnswer(labourerId, examId, examType, eduQuestion.getId());
        List<String> myAnswerList = null;
        if (StringUtils.isNotBlank(myAnswer)) {
            myAnswerList = java.util.Arrays.asList(myAnswer.split(COMMA));
        }
        eduQuestion.setMyAnswerList(myAnswerList);
        eduQuestion.setMyAnswer(myAnswer);


        //正确答案
        String correctAnswer = eduQuestionExerciseDao.getRightAnswer(eduQuestion.getId());
        eduQuestion.setTrueAnswer(correctAnswer);

        //考试剩余时间
        Integer hasedTime = mobileExamMockDao.queryHasedTime(labourerId, examId, examType);
        hasedTime = hasedTime.compareTo(0) < 0 ? 0 : hasedTime ;
        eduQuestion.setHasedTime(hasedTime);

        logger.info("......queryAnswerAndParsing.....myanswer:{},....correctAnswer:{}",myAnswer, correctAnswer);

        return eduQuestion ;
    }
}
