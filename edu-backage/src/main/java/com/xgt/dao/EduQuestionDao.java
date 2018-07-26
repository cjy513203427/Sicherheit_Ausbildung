package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.constant.SystemConstant;
import com.xgt.dto.RandomQuestionParam;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduQuestionBank;
import com.xgt.entity.NotSelectEduQuestion;
import com.xgt.entity.exam.ExamMock;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.entity.train.EduTraprogramExamCfg;
import com.xgt.entity.train.QuestionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/1 19:29.
 */
@Repository
public class EduQuestionDao {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "eduQuestion.";

    /**
     * 检索题目
     * @param eduQuestion
     * @return
     */
    public List<EduQuestion> queryEduQuestion(EduQuestion eduQuestion) {
        String sqlId = NAMESPACE + "queryEduQuestion";
        return daoClient.queryForList(sqlId,eduQuestion, EduQuestion.class);
    }

    /**
     * 统计题目总数
     * @param eduQuestion
     * @return
     */
    public Integer countEduQuestion(EduQuestion eduQuestion){
        String sqlId = NAMESPACE + "countEduQuestion";
        return daoClient.queryForObject(sqlId,eduQuestion, Integer.class);
    }

    /**
     * 修改题目
     * @param eduQuestion
     * @return
     */
    public Integer modifyEduQuestion(EduQuestion eduQuestion){
        String sqlId = NAMESPACE + "modifyEduQuestion";
        return daoClient.excute(sqlId,eduQuestion);
    }

    /**
     * 添加题目
     * @param eduQuestion
     * @return
     */
    public Integer addEduQuestion(EduQuestion eduQuestion){
        String sqlId = NAMESPACE + "addEduQuestion";
        return daoClient.insertAndGetId(sqlId,eduQuestion);
    }

    /**
     * 删除题目
     * @param id
     * @return
     */
    public Integer deleteEduQuestion(Integer id){
        Map param = new HashMap();
        param.put("id",id);
        String sqlId = NAMESPACE + "deleteEduQuestion";
        return daoClient.excute(sqlId,param);
    }

    /**
     * 检索当前题目的答案
     * @param questionId
     * @return
     */
    public List<EduQuestionAnswer> queryEduQuestionAnswer(Integer questionId){
        String sqlId = NAMESPACE + "queryEduQuestionAnswer";
        Map<String,Object> param = new HashMap<>();
        param.put("questionId",questionId);
        return daoClient.queryForList(sqlId,param,EduQuestionAnswer.class);
    }

    /**
     *  根据工人id 、 页面最后一个收藏内容的id 、 分页查询收藏的题库
     * @author liuao
     * @date 2018/6/11 10:31
     */
    public List<EduQuestionBank> queryCollectQuestionBankBy(Integer labourId, Integer lastId, Integer pageSize, String collectionType) {
        String sqlId = NAMESPACE + "queryCollectQuestionBankBy";
        Map params = new HashMap();
        params.put("labourId",labourId);
        params.put("lastId",lastId);
        params.put("pageSize",pageSize);
        params.put("collectionType", collectionType);
        return daoClient.queryForList(sqlId,params,EduQuestionBank.class);
    }


    /**
     *  根据工人id 、 页面最后一个收藏内容的id 、查询该题库收藏了多少题
     * @author liuao
     * @date 2018/6/11 10:31
     */
    public Integer queryCollectQuestionCountBy(Integer labourId, Integer questionBankId, String collectionType) {
        String sqlId = NAMESPACE + "queryCollectQuestionCountBy";
        Map params = new HashMap();
        params.put("labourId",labourId);
        params.put("questionBankId",questionBankId);
        params.put("collectionType", collectionType);
        return daoClient.queryForObject(sqlId,params,Integer.class);
    }

    /**
     * 根据题库id 查询出该上一个或者下一题户收藏的题目
     * nextPrevFlag 1：下一个 、 2:上一个
     * @author liuao
     * @date 2018/6/11 19:57
     */
    public EduQuestion queryNextOrPrevQuestionForCollect(Integer labourId, Integer questionBankId, Integer questionId,
                                                         String nextPrevFlag) {
        String sqlId = NAMESPACE + "queryNextOrPrevQuestionForCollect";
        Map params = new HashMap();
        params.put("labourId",labourId);
        params.put("questionBankId",questionBankId);
        params.put("questionId",questionId);
        params.put("nextPrevFlag",nextPrevFlag);
        params.put("collectionType", SystemConstant.CollectionType.QUESTION);
        return daoClient.queryForObject(sqlId,params,EduQuestion.class);
    }

    /**
     * 查询所有题目列表（除了已添加）
     *
     * @param
     * @param notSelectEduQuestion
     * @return
     */
    public List<ExamMock> queryExceptThisQuestions(NotSelectEduQuestion notSelectEduQuestion) {
        String sqlId = NAMESPACE + "queryExceptThisQuestions";
        return daoClient.queryForList(sqlId, notSelectEduQuestion, ExamMock.class);
    }

    /**
     * 查询所有题目列表数量（除了已添加）
     *
     * @param
     * @param notSelectEduQuestion
     * @return
     */
    public Integer exceptThisQuestionsCount(NotSelectEduQuestion notSelectEduQuestion) {
        String sqlId = NAMESPACE + "exceptThisQuestionsCount";
        return daoClient.queryForObject(sqlId, notSelectEduQuestion, Integer.class);
    }

    /**
     * @Description  培训系统PC版 - 根据课程id获取题目List(有身份判断)
     * @author Joanne
     * @create 2018/6/27 19:07
     */
    public List<EduQuestion> queryEduQuestionByCourseIds(String courseIds, Integer pageOffset, Integer pageSize,String testName) {
        String sqlId = NAMESPACE + "queryEduQuestionByCourseIds";
        Map params = new HashMap() ;
        params.put("courseIds",courseIds);
        params.put("testName",testName);
        params.put("pageOffSet",pageOffset);
        params.put("pageSize",pageSize);
        return daoClient.queryForList(sqlId,params,EduQuestion.class);
    }

    /**
     * @Description 培训系统PC版 - 根据课程id获取题目数量(需要身份验证)
     * @author Joanne
     * @create 2018/6/27 19:15
     */
    public Integer countQuestionByCourseIds(String courseIds) {
        String sqlId = NAMESPACE + "countQuestionByCourseIds" ;
        Map param = new HashMap() ;
        param.put("courseIds",courseIds) ;
        Long questionNumber = daoClient.queryForObject(sqlId,param,Long.class);
        return questionNumber.intValue();
    }

    /**
     *  根据题型、难易程度、选中题数 随机获取题目
     * @author liuao
     * @date 2018/6/29 9:44
     */
    public List<EduQuestion>  queryRandomQuestion(RandomQuestionParam randomQuestionParam){

        String sqlId = NAMESPACE + "queryRandomQuestion" ;
//        Map param = new HashMap() ;
//        param.put("questionType", questionType) ;
//        param.put("difficultyDegree", difficultyDegree) ;
//        param.put("selectedNumber", selectedNumber) ;

        return daoClient.queryForList(sqlId,randomQuestionParam,EduQuestion.class);

    }

    /**
     * @Description 根据课程ID统计题目信息
     * @author Joanne
     * @create 2018/7/12 10:16
     */
    public List<QuestionStatistics> statisticQuestionByCourseIds(String ids) {
        String sqlId = NAMESPACE + "statisticQuestionByCourseIds" ;
        Map param = new HashMap();
        param.put("ids",ids);
        return daoClient.queryForList(sqlId,param,QuestionStatistics.class);
    }

    /**
     *  根据主键id,查询 题目信息
     * @author liuao
     * @date 2018/7/17 19:39
     */
    public EduQuestion queryQuestionById(Integer questionId){
        String sqlId = NAMESPACE + "queryQuestionById" ;
        Map param = new HashMap();
        param.put("questionId",questionId);
        return daoClient.queryForObject(sqlId,param,EduQuestion.class);
    }
}
