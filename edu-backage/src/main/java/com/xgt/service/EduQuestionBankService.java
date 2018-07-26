package com.xgt.service;

import com.xgt.dao.EduQuestionBankDao;
import com.xgt.entity.EduQuestionBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/1 21:10.
 */
@Service
public class EduQuestionBankService {
    private static final Logger logger = LoggerFactory.getLogger(EduQuestionBankService.class);
    @Autowired
    private EduQuestionBankDao eduQuestionBankDao;

    /**
     * 检索题库
     * @param eduQuestionBank
     * @return
     */
    public Map queryEduQuestionBank(EduQuestionBank eduQuestionBank) {
        Map map = new HashMap();
        Integer total = eduQuestionBankDao.countEduQuestionBank(eduQuestionBank);
        List<EduQuestionBank> list = eduQuestionBankDao.queryEduQuestionBank(eduQuestionBank);
        map.put("list",list);
        map.put("total",total);
        return map;
    }

    /**
     * 修改题库
     * @param eduQuestionBank
     * @return
     */
    public Integer modifyEduQuestionBank(EduQuestionBank eduQuestionBank){
        return eduQuestionBankDao.modifyEduQuestionBank(eduQuestionBank);
    }

    /**
     * 添加题库
     * @param eduQuestionBank
     * @return
     */
    public Integer addEduQuestionBank(EduQuestionBank eduQuestionBank){
        return eduQuestionBankDao.addEduQuestionBank(eduQuestionBank);
    }

    /**
     * 删除题库
     * @param id
     * @return
     */
    public Integer deleteEduQuestionBank(Integer id){
        return eduQuestionBankDao.deleteEduQuestionBank(id);
    }
}
