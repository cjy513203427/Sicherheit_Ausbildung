package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduQuestionBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题库 实体类
 * @author cjy
 * @date 2018/6/1 17:48
 */
@Repository
public class EduQuestionBankDao {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionBankDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAMESPACE = "eduQuestionBank.";

    /**
     * 检索题库
     * @param eduQuestionBank
     * @return
     */
    public List<EduQuestionBank> queryEduQuestionBank(EduQuestionBank eduQuestionBank) {
        String sqlId = NAMESPACE + "queryEduQuestionBank";
        return daoClient.queryForList(sqlId, eduQuestionBank,EduQuestionBank.class);
    }

    /**
     * 统计题库总数
     * @param eduQuestionBank
     * @return
     */
    public Integer countEduQuestionBank(EduQuestionBank eduQuestionBank){
        String sqlId = NAMESPACE + "countEduQuestionBank";
        return daoClient.queryForObject(sqlId,eduQuestionBank,Integer.class);
    }

    /**
     * 修改题库
     * @param eduQuestionBank
     * @return
     */
    public Integer modifyEduQuestionBank(EduQuestionBank eduQuestionBank){
        String sqlId = NAMESPACE + "modifyEduQuestionBank";
        return daoClient.excute(sqlId,eduQuestionBank);
    }

    /**
     * 添加题库
     * @param eduQuestionBank
     * @return
     */
    public Integer addEduQuestionBank(EduQuestionBank eduQuestionBank){
        String sqlId = NAMESPACE + "addEduQuestionBank";
        return daoClient.insertAndGetId(sqlId,eduQuestionBank);
    }

    /**
     * 删除题库
     * @param id
     * @return
     */
    public Integer deleteEduQuestionBank(Integer id){
        Map param = new HashMap<>();
        param.put("id",id);
        String sqlId = NAMESPACE + "deleteEduQuestionBank";
        return daoClient.excute(sqlId,param);
    }
}
