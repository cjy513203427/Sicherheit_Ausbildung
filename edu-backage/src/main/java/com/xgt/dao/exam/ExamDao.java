package com.xgt.dao.exam;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduExamQuestionRel;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.exam.AnswertoolRel;
import com.xgt.entity.exam.ExamMock;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamDao {

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "exam.";


    /**
     * 查询模拟考试列表
     *
     * @param examMock
     * @return
     */
    public List<ExamMock> queryExamMockList(ExamMock examMock) {
        String sqlId = name_space + "queryExamMockList";
        return daoClient.queryForList(sqlId, examMock, ExamMock.class);
    }

    /**
     * 查询模拟考试列表数量
     *
     * @param examMock
     * @return
     */
    public Integer queryExamMockListCount(ExamMock examMock) {
        String sqlId = name_space + "queryExamMockListCount";
        return daoClient.queryForObject(sqlId, examMock, Integer.class);
    }

    /**
     * 添加模拟试卷
     *
     * @param examMock
     * @return examId
     */
    public Integer createExamMock(ExamMock examMock) {
        String sqlId = name_space + "createExamMock";
        return daoClient.insertAndGetId(sqlId, examMock);
    }

    /**
     * 添加试卷题目
     *
     * @param examId
     * @param examType
     * @param questionRelList
     */
    public void addQuestions(Integer examId, String examType, List<EduExamQuestionRel> questionRelList) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        map.put("questionRelList", questionRelList);
        String sqlId = name_space + "addMockQuestions";
        daoClient.batchInsertAndGetId(sqlId, map);
    }

    /**
     * 删除模拟试卷
     *
     * @param examId
     */
    public void deleteExamMock(Integer examId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", examId);
        String sqlId = name_space + "deleteExamMock";
        daoClient.excute(sqlId, map);
    }

    /**
     * 删除题目（根据试卷id，类型）
     *
     * @param examId
     * @param examType
     */
    public void deleteMockQuestion(Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "deleteMockQuestion";
        daoClient.excute(sqlId, map);
    }

    /**
     * 删除试卷的题目（模拟 正式）
     *
     * @param examId
     * @param idsStr
     * @param examType
     */
    public void deleteMockDownQuestion(Integer examId, StringBuilder idsStr, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("idsStr", idsStr);
        map.put("examType", examType);
        String sqlId = name_space + "deleteMockDownQuestion";
        daoClient.excute(sqlId, map);
    }

    /**
     * 查询当前模拟试卷的题目列表
     *
     * @param examMock
     * @param examType
     * @return
     */
    public List<ExamMock> queryThisMockQuestions(ExamMock examMock, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("examMock", examMock);
        map.put("examType", examType);
        String sqlId = name_space + "queryThisMockQuestions";
        return daoClient.queryForList(sqlId, map, ExamMock.class);
    }


    /**
     * 查询模拟考试详情
     *
     * @return
     */
    public ExamMock queryExamMockById(Integer examId) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        String sqlId = name_space + "queryExamMockById";
        return daoClient.queryForObject(sqlId, map, ExamMock.class);
    }


    /**
     * 查询该试卷共有多少到题
     *
     * @author liuao
     * @date 2018/6/14 15:00
     */
    public Integer queryQuestionCount(Integer examId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("examType", examType);
        String sqlId = name_space + "queryQuestionCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * 修改模拟试卷
     *
     * @param examMock
     */
    public void updateExamMock(ExamMock examMock) {
        String sqlId = name_space + "updateExamMock";
        daoClient.excute(sqlId, examMock);
    }

    /**
     * @Description 查询本次计划需要交卷的人
     * @author HeLiu
     * @date 2018/7/18 14:49
     */
    public List<AnswertoolRel> queryThisTrainLabourers(Integer planId) {
        Map<String, Object> map = new HashMap<>();
        map.put("planId", planId);
        String sqlId = name_space + "queryThisTrainLabourers";
        return daoClient.queryForList(sqlId, map, AnswertoolRel.class);
    }

    /**
     * @Description 查询考试成绩
     * @author HeLiu
     * @date 2018/7/18 16:49
     */
    public Integer queryExamScore(Integer examId, Integer labourId, String examType, String answerStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("examId", examId);
        map.put("labourId", labourId);
        map.put("examType", examType);
        map.put("answerStatus", answerStatus);
        String sqlId = name_space + "queryExamScore";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }
}
