package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduQuestionBank;
import com.xgt.entity.EduQuestionExercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/7 11:54.
 */
@Repository
public class EduQuestionExerciseDao {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionExerciseDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "eduQuestionExercise.";

    /**
     * 检索练习
     * 错题本
     * @param eduQuestionExercise
     * @return
     */
    public List<EduQuestionExercise> queryEduQuestionExercise(EduQuestionExercise eduQuestionExercise) {
        String sqlId = NAMESPACE + "queryEduQuestionExercise";
        return daoClient.queryForList(sqlId,eduQuestionExercise, EduQuestionExercise.class);
    }

    /**
     * 统计练习总数
     * @param eduQuestionExercise
     * @return
     */
    public Integer countEduQuestionExercise(EduQuestionExercise eduQuestionExercise){
        String sqlId = NAMESPACE + "countEduQuestionExercise";
        return daoClient.queryForObject(sqlId,eduQuestionExercise, Integer.class);
    }

    /**
     * 添加练习
     * @param eduQuestionExercise
     * @return
     */
    public Integer addEduQuestionExercise(EduQuestionExercise eduQuestionExercise){
        String sqlId = NAMESPACE + "addEduQuestionExercise";
        return daoClient.insertAndGetId(sqlId,eduQuestionExercise);
    }

    /**
     * 统计已回答问题、正确答案数、当前题库题目数
     * @param labourId
     * @return
     */
    public List<Map<String,Object>> countAnswerdQuestion(Integer labourId, Integer questionBankId){
        String sqlId = NAMESPACE + "countAnswerdQuestion";
        Map param = new HashMap();
        param.put("labourId",labourId);
        // 题库id 非必填
        param.put("questionBankId",questionBankId);
        return daoClient.queryForList(sqlId,param);
    }

    /**
     * 获取正确答案
     * @param questionId
     * @return
     */
    public String getRightAnswer(Integer questionId){
        String sqlId = NAMESPACE + "getRightAnswer";
        Map<String,Object> param = new HashMap<>();
        param.put("questionId", questionId);
        return daoClient.queryForObject(sqlId,param, String.class);
    }

    /**
     * 查询题库的名称
     * @param postType
     * @return
     */
    public List<EduQuestionBank> queryEduQuestionBankTitle(String postType){
        String sqlId = NAMESPACE + "queryEduQuestionBankTitle";
        Map<String,Object> param = new HashMap<>();
        param.put("postType", postType);

        return daoClient.queryForList(sqlId,param, EduQuestionBank.class);
    }

    /**
     * 获取题目总正确率
     * 当前工人已答正确题目/当前工人答题总数
     * @return
     */
    public BigDecimal getAllAccuracy(Integer labourId){
        String sqlId = NAMESPACE + "getAllAccuracy";
        Map<String, Object> param = new HashMap<>();
        param.put("labourId",labourId);
        return daoClient.queryForObject(sqlId,param, BigDecimal.class);
    }

    /**
     *  章节练习，上一题、下一题
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:13
     */
    public EduQuestion queryNextOrPrevQuestion(Integer questionBankId, Integer questionId, String nextPrevFlag) {
        String sqlId = NAMESPACE + "queryNextOrPrevQuestion";

        Map<String,Object> param = new HashMap<>();
        param.put("questionBankId", questionBankId);
        param.put("questionId", questionId);
        param.put("nextPrevFlag", nextPrevFlag);

        return daoClient.queryForObject(sqlId, param, EduQuestion.class);
    }

    /**
     *  根据条件查询出练习记录
     * @author liuao
     * @date 2018/6/12 21:36
     */
    public EduQuestionExercise queryQuestionExercise(Integer labourId, Integer questionId, Integer questionBankId){
        String sqlId = NAMESPACE + "queryQuestionExercise";

        Map<String,Object> param = new HashMap<>();
        param.put("questionBankId", questionBankId);
        param.put("questionId", questionId);
        param.put("labourId", labourId);

        return daoClient.queryForObject(sqlId, param, EduQuestionExercise.class);
    }

    /**
     *  手机端 -  我的错题，上一题、下一题
     *  nextPrevFlag : 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/12 10:06
     */
    public EduQuestion queryNextOrPrevQuestionForWrong(Integer questionId, String nextPrevFlag, Integer labourId) {
        String sqlId = NAMESPACE + "queryNextOrPrevQuestionForWrong";

        Map<String,Object> param = new HashMap<>();
        param.put("questionId", questionId);
        param.put("nextPrevFlag", nextPrevFlag);
        param.put("labourId", labourId);

        return daoClient.queryForObject(sqlId, param, EduQuestion.class);
    }
}
