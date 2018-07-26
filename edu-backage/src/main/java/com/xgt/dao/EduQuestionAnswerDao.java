package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduQuestionAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/6 11:27.
 */
@Repository
public class EduQuestionAnswerDao {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionAnswerDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "eduQuestionAnswer.";

    /**
     * 检索题目的答案
     * @param eduQuestionAnswer
     * @return
     */
    public List<EduQuestionAnswer> queryEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer) {
        String sqlId = NAMESPACE + "queryEduQuestionAnswer";
        return daoClient.queryForList(sqlId,eduQuestionAnswer, EduQuestionAnswer.class);
    }

    /**
     * 统计答案总数
     * @param eduQuestionAnswer
     * @return
     */
    public Integer countEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        String sqlId = NAMESPACE + "countEduQuestionAnswer";
        return daoClient.queryForObject(sqlId,eduQuestionAnswer, Integer.class);
    }

    /**
     * 修改答案
     * @param eduQuestionAnswer
     * @return
     */
    public Integer modifyEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        String sqlId = NAMESPACE + "modifyEduQuestionAnswer";
        return daoClient.excute(sqlId,eduQuestionAnswer);
    }

    /**
     * 添加答案
     * @param eduQuestionAnswer
     * @return
     */
    public Integer addEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        String sqlId = NAMESPACE + "addEduQuestionAnswer";
        return daoClient.insertAndGetId(sqlId,eduQuestionAnswer);
    }

    /**
     * 删除答案
     * @param id
     * @return
     */
    public Integer deleteEduQuestionAnswer(Integer id){
        Map param = new HashMap();
        param.put("id",id);
        String sqlId = NAMESPACE + "deleteEduQuestionAnswer";
        return daoClient.excute(sqlId,param);
    }

    /**
     *  批量插入题目的答案
     * @author liuao
     * @date 2018/6/9 17:43
     */
    public void batchAddEduQuestionAnswer(List<EduQuestionAnswer> questionAnswerList) {
        String sqlId = NAMESPACE + "batchAddEduQuestionAnswer";
        Map<String, Object> param = new HashMap<>();
        param.put("questionAnswerList", questionAnswerList);
        daoClient.batchInsertAndGetId(sqlId, param);
    }
}
