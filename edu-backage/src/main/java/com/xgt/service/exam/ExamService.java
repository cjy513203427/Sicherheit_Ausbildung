package com.xgt.service.exam;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.exam.ExamDao;
import com.xgt.dao.exam.MobileExamMockDao;
import com.xgt.dao.train.ExamTrainDao;
import com.xgt.dao.train.InTheTrainDao;
import com.xgt.dao.train.PlanDao;
import com.xgt.dao.train.ProgramDao;
import com.xgt.entity.EduExamQuestionRel;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.exam.AnswertoolRel;
import com.xgt.entity.exam.ExamLabourerRel;
import com.xgt.entity.exam.ExamMock;
import com.xgt.entity.train.EduExamTrain;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.entity.train.EduTrainProgram;
import com.xgt.util.JSONUtil;
import com.xgt.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.xgt.constant.SystemConstant.COMMA;

/**
 * @author liuao
 * @date 2018/6/5 17:10
 */
@Service
public class ExamService {
    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    @Autowired
    private ExamDao examDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private MobileExamMockDao mobileExamMockDao;

    @Autowired
    private ExamTrainDao examTrainDao;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private InTheTrainDao inTheTrainDao;

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private EduQuestionDao questionDao;


    /**
     * 查询模拟考试列表（分页）
     *
     * @param examMock
     * @author liuao
     * @date 2018/6/5 19:04
     */
    public Map<String, Object> queryExamMockList(ExamMock examMock) {
        Integer total = examDao.queryExamMockListCount(examMock);
        List<ExamMock> list = null;
        if (total != null && total > 0) {
            list = examDao.queryExamMockList(examMock);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, ExamMock.class));
        map.put("total", total);
        return map;
    }

    /**
     * 新增模拟考试接口
     *
     * @param examMock
     */
    public void createExamMock(ExamMock examMock) {
        examDao.createExamMock(examMock);
    }

    /**
     * 删除模拟试卷
     *
     * @param examId
     * @param examType
     */
    public void deleteExamMock(Integer examId, String examType) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //删除试卷
                examDao.deleteExamMock(examId);
                //删除试卷下的题目
                examDao.deleteMockQuestion(examId, examType);
                return true;
            }
        });
    }

    /**
     * 删除试卷的题目（模拟 正式）
     *
     * @param examId
     * @param questionIds
     * @param examType
     */
    public void deleteMockDownQuestion(Integer examId, String questionIds, String examType) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //数据组装
                StringBuilder idsStr = getInStr(questionIds);
                examDao.deleteMockDownQuestion(examId, idsStr, examType);
                return true;
            }
        });
    }

    /**
     * 数据组装接口（"1,2,3" - "'1','2','3'"）
     *
     * @param questionIds
     * @return
     */
    public StringBuilder getInStr(String questionIds) {
        StringBuilder idsStr = new StringBuilder();
        List<String> questionIdList = Arrays.asList(questionIds.split(","));
        for (int i = 0; i < questionIdList.size(); i++) {
            if (i < questionIdList.size() - 1) {
                idsStr.append("'" + questionIdList.get(i) + "',");
            } else {
                idsStr.append("'" + questionIdList.get(i) + "'");
            }
        }
        return idsStr;
    }

    /**
     * 新增试卷的题目
     *
     * @param examMock
     * @param questionIds
     * @param examType
     */
    public void addExamQuestions(ExamMock examMock, String questionIds, String examType) {

        Integer examId = examMock.getId();
        String[] questionIdArr = questionIds.split(COMMA);

        ExamMock oldExamMock = examDao.queryExamMockById(examId);
        // 试卷规定总题数
        Integer questionCount = oldExamMock.getQuestionNumber();
        // 试卷已有数量
        Integer hadCount = examDao.queryQuestionCount(examId, examType);
        // 增加题目时，题目数量
        Integer questionIdCount = questionIdArr.length;
        // 题量已满
        if (questionCount.compareTo(hadCount) <= 0) {
            logger.info("......题量已满....无法添加");
            throw new RuntimeException("题量已满....无法添加");
        }

        // 比较题量是否达标
        if (questionCount.compareTo(questionIdCount) < 0) {
            logger.info("......题量过多....questionCount：{}...questionIdCount:{}...",
                    questionCount, questionIdCount);
            throw new RuntimeException("......题量过多....questionCount：+" + questionCount
                    + "+..questionIdCount:" + questionIdCount);
        }


        List<EduExamQuestionRel> questionRelList = initExamQuestionRel(questionIdArr);

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                //将字符串截取成集合
                //添加模拟试卷题目
                examDao.addQuestions(examMock.getId(), examType, questionRelList);
                return true;
            }
        });
    }

    /**
     * 查询当前模拟试卷的题目列表
     *
     * @param examMock
     * @param examType
     * @return
     */
    public List<ExamMock> queryThisMockQuestions(ExamMock examMock, String examType) {
        return examDao.queryThisMockQuestions(examMock, examType);
    }

    /**
     * 查询模拟考试详情
     *
     * @param examId
     * @return
     */
    public ExamMock queryExamMockById(Integer examId) {
        return examDao.queryExamMockById(examId);
    }

    /**
     * 修改模拟试卷
     *
     * @param examMock
     */
    public void updateExamMock(ExamMock examMock) {
        examDao.updateExamMock(examMock);
    }

    /**
     * 初始化试卷、题目关系表
     *
     * @author liuao
     * @date 2018/6/29 10:29
     */
    public static List<EduExamQuestionRel> initExamQuestionRel(String[] questionIdarr) {

        List<EduExamQuestionRel> examQuestionRelList = new ArrayList<>();
        if (null == questionIdarr || questionIdarr.length == 0) {
            return examQuestionRelList;
        }
        for (String questionId : questionIdarr) {
            EduExamQuestionRel examQuestionRel = new EduExamQuestionRel();
            examQuestionRel.setQuestionId(Integer.valueOf(questionId));
            examQuestionRel.setQuestionScore(null);
            examQuestionRelList.add(examQuestionRel);
        }
        return examQuestionRelList;
    }

    /**
     * cs端 - 点击开始考试，生成入场人员的答题卷，和考试结果信息
     * <p>
     * 思路 ： 查询是否有人参加过本次考试，,存在，则提示返回开始考试失败
     *
     * @return
     */
    public boolean createAnswerCardAndResult(Integer programId, Integer[] labourIds) {


        EduExamTrain examTrain = examTrainDao.queryTrainExamByProgramId(programId);
        Integer examId = examTrain.getId();

        // 查询出试卷下的所有题目id
        List<Integer> questionIds = examTrainDao.queryQuestionIdListByExamId(examId, SystemConstant.ExamType.TRAIN);

        boolean exsitFlag = examTrainDao.queryLabourInExamExsit(examId, labourIds);

        // 判断是否参加过本次培训考试，参加过，无需重复考试
        if (exsitFlag) {
            logger.info("考试人员中，已有人参加过本次考试，不能重复考试");
            return false;
        }

        Set<Integer>  labourIdSet =  new HashSet<Integer>(Arrays.asList(labourIds));

        for (Integer labourId : labourIdSet) {
            transactionTemplate.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                    //创建答题卡
                    mobileExamMockDao.createAnswerCard(labourId, examId, SystemConstant.ExamType.TRAIN,
                            questionIds);
                    //创建考试人员考试结果
                    mobileExamMockDao.createExamLabourerRel(labourId, examId, SystemConstant.ExamType.TRAIN);
                    return true;
                }
            });
        }

        return true;
    }

    /**
     * 保存培训考试的答案
     * 思路：
     * 根据计划id , 答题器编号，查询出 工人id
     *
     * @author liuao
     * @date 2018/7/17 17:56
     */
    public void saveTrainExanAnswer(Integer planId, Integer questionId, String answerToolNum, String myAnswer) {

        Integer labourId = inTheTrainDao.queryLabourIdByToolNumAndPlanId(answerToolNum, planId);


        EduTrainPlan trainPlan = planDao.queryPlanById(planId);
        Integer programId = trainPlan.getProgramId();
        // 查询出试卷id
        Integer examId = programDao.queryProgramTestPaperId(programId);

        EduQuestion question = questionDao.queryQuestionById(questionId);
        // 查询出正确答案
        String trueAnswer = question.getTrueAnswer();

        // 提交的答案
        myAnswer = StringUtil.str2SortStr(myAnswer);
        // 正确标识
        String answerStatus = trueAnswer.equals(myAnswer) ?
                SystemConstant.correctFlag.TRUE : SystemConstant.correctFlag.FALSE;

        // 更新答案到数据库
        mobileExamMockDao.saveAnswer(labourId, examId, questionId,
                SystemConstant.ExamType.TRAIN, myAnswer, answerStatus);

    }

    /**
     * 培训考试交卷接口
     * 思路 ：
     * 1、根据计划id ,到答题器对应表（edu_answertool_rel）中，查询出本次计划考试，有哪些人需要交卷
     * 2、根据查询出的人员id 和试卷id 到答题卡表 统计正确的题数
     * 3、到edu_exam_question_rel （试卷题目关系表）查询出每一题的分值， 没有对应分值的，则手动计算分值（总分/总题数）
     * 4、根据正确的题数，统计出考试得分
     *
     * @return
     */
    public void handInExamTrain(Integer planId) {
        //查询本次计划需要交卷的人
        List<AnswertoolRel> answertoolRels = examDao.queryThisTrainLabourers(planId);
        if (CollectionUtils.isNotEmpty(answertoolRels)) {

            ExamMock examMock = null;
            Integer passScore = null;
            for (AnswertoolRel answertoolRel : answertoolRels) {
                Integer examId = answertoolRel.getExamId();
                Integer labourId = answertoolRel.getLabourId();
                if (examMock == null && passScore == null) {
                    //获取试卷详情
                    examMock = examDao.queryExamMockById(examId);
                    //及格分
                    passScore = examMock.getPassScore();
                }
                //判断是自动组卷还会手动组卷
                //查询考试成绩（如果不为空说明是自动组卷直接保存考试结果，如果为空说明是手动组卷需要自己计算结果）
                Integer examScore = examDao.queryExamScore(examId, labourId, SystemConstant.ExamType.TRAIN, SystemConstant.correctFlag.TRUE);
                if (examScore != null && examScore > 0) {
                    //自动组卷保存考试结果
                } else {
                    //手动组卷保存考试结果
                    //总分
                    Integer totalScore = examMock.getTotalScore();
                    //根据人员id和试卷id统计正确的题数
                    Integer trueQuestionCount = mobileExamMockDao.trueQuestionCount(labourId, examId,
                            SystemConstant.ExamType.TRAIN, SystemConstant.correctFlag.TRUE);
                    //考试得分
                    examScore = trueQuestionCount * (totalScore / examMock.getQuestionNumber());
                }
                //是否通过
                String passStatus = null;
                if (examScore.compareTo(passScore) >= 0) {
                    passStatus = SystemConstant.passStatus.PASS;
                } else {
                    passStatus = SystemConstant.passStatus.NOPASS;
                }

                //保存交卷信息
                ExamLabourerRel newExamLabourerRel = new ExamLabourerRel();
                newExamLabourerRel.setExamId(examId);
                newExamLabourerRel.setExamType(SystemConstant.ExamType.TRAIN);
                newExamLabourerRel.setLabourerId(labourId);
                newExamLabourerRel.setExamScore(examScore);
                newExamLabourerRel.setPassStatus(passStatus);
                mobileExamMockDao.handInExamMock(newExamLabourerRel);
            }
        }
    }

}
