package com.xgt.dao.exam;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.exam.AnswerCard;
import com.xgt.entity.exam.ExamLabourerRel;
import com.xgt.entity.exam.ExamMock;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MobileExamMockDao {

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "mobileExamMock.";

    /**
     * 手机端模拟试卷信息列表查询
     *
     * @param labourerUserId
     * @return
     */
    public List<ExamMock> queryExamMockList(Integer labourerUserId) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerUserId", labourerUserId);
        String sqlId = name_space + "queryExamMockList";
        return daoClient.queryForList(sqlId, map, ExamMock.class);
    }

    /**
     * 建筑工地人员模拟考试信息查询
     *
     * @param labourerUserId
     * @param examType
     * @return
     */
    public List<ExamLabourerRel> queryExamMockLabourers(Integer labourerUserId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerUserId", labourerUserId);
        map.put("examType", examType);
        String sqlId = name_space + "queryExamMockLabourers";
        return daoClient.queryForList(sqlId, map, ExamLabourerRel.class);
    }

    /**
     * 手机端模拟考试试题查询,模拟考试，查询上一题，下一题
     *
     * @param examId
     * @param examType
     * @return
     */
    public EduQuestion queryMobileMockQuestions(Integer examId, String examType, String upOrDownStatus, Integer questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("upOrDownStatus", upOrDownStatus);
        map.put("questionId", questionId);
        String sqlId = name_space + "queryMobileMockQuestions";
        return daoClient.queryForObject(sqlId, map, EduQuestion.class);
    }

    /**
     * 手机端查询试题选项
     *
     * @param id
     * @return
     */
    public List<EduQuestionAnswer> queryQuestionAnswer(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", id);
        String sqlId = name_space + "queryQuestionAnswer";
        return daoClient.queryForList(sqlId, map, EduQuestionAnswer.class);
    }

    /**
     * 答题卡是否存在
     *
     * @param examId
     * @param examType
     * @return
     */
    public Integer queryAnswerCardCount(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryAnswerCardCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * 创建答题卡
     *
     * @param labourerId
     * @param examId
     * @param examType
     */
    public void createAnswerCard(Integer labourerId, Integer examId, String examType, List<Integer> questionIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("questionIds", questionIds);
        String sqlId = name_space + "createAnswerCard";
        daoClient.excute(sqlId, map);
    }

    /**
     * 获取题目ID
     *
     * @param examId
     * @param examType
     * @return
     */
    public List<Integer> queryQuestionIds(Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryQuestionIds";
        return daoClient.queryForList(sqlId, map, Integer.class);
    }

    /**
     * 手机端答题卡查询接口
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public List<AnswerCard> queryAnswerCard(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryAnswerCard";
        return daoClient.queryForList(sqlId, map, AnswerCard.class);
    }

    /**
     * 创建考试人员考试结果
     *
     * @param labourerId
     * @param examId
     * @param examType
     */
    public void createExamLabourerRel(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "createExamLabourerRel";
        daoClient.excute(sqlId, map);
    }

    /**
     * 保存答案，（答题卡是已生成的，这里是把答案更新到数据库）
     *
     * @param labourerId
     * @param examId
     * @param questionId
     * @param examType
     * @param myAnswer
     * @param answerStatus
     */
    public void saveAnswer(Integer labourerId, Integer examId, Integer questionId,
                           String examType, String myAnswer, String answerStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("questionId", questionId);
        map.put("answerStatus", answerStatus);
        map.put("myAnswer", myAnswer);
        map.put("examType", examType);
        String sqlId = name_space + "saveAnswer";
        daoClient.excute(sqlId, map);
    }

    /**
     * 正确题数
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @param answerStatus
     * @return
     */
    public Integer trueQuestionCount(Integer labourerId, Integer examId, String examType, String answerStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("answerStatus", answerStatus);
        String sqlId = name_space + "trueQuestionCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
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
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryConsumeTime";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * 保存交卷信息
     *
     * @param examLabourerRel
     */
    public void handInExamMock(ExamLabourerRel examLabourerRel) {
        String sqlId = name_space + "handInExamMock";
        daoClient.excute(sqlId, examLabourerRel);
    }

    /**
     * 查询考试结果
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public ExamLabourerRel queryExamLabourerRel(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryExamLabourerRel";
        return daoClient.queryForObject(sqlId, map, ExamLabourerRel.class);
    }

    /**
     * 查询我的答案
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @param questionId
     * @return
     */
    public String queryMyAnswer(Integer labourerId, Integer examId, String examType, Integer questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("questionId", questionId);
        String sqlId = name_space + "queryMyAnswer";
        return daoClient.queryForObject(sqlId, map, String.class);
    }

    /**
     * 考试结果是否存在
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public Integer queryRelCount(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryRelCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * 手机端单个模拟考试试题详情查询
     *
     * @param examId
     * @param examType
     * @param questionId
     * @return
     */
    public EduQuestion queryMockQuestionById(Integer examId, String examType, Integer questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("questionId", questionId);
        String sqlId = name_space + "queryMockQuestionById";
        return daoClient.queryForObject(sqlId, map, EduQuestion.class);
    }

    /**
     * 清除答题卡
     *
     * @param labourerId
     * @param examId
     * @param examType
     */
    public void cleanAnswerCard(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "cleanAnswerCard";
        daoClient.excute(sqlId, map);
    }

    /**
     * 清除考试结果
     *
     * @param labourerId
     * @param examId
     * @param examType
     */
    public void cleanExamLabourerRel(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "cleanExamLabourerRel";
        daoClient.excute(sqlId, map);
    }

    /**
     * 考试剩余时间
     *
     * @param labourerId
     * @param examId
     * @param examType
     * @return
     */
    public Integer queryHasedTime(Integer labourerId, Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("labourerId", labourerId);
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryHasedTime";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }
}