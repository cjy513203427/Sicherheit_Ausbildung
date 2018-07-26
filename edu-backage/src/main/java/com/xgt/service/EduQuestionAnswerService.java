package com.xgt.service;

import com.xgt.dao.DictionaryDao;
import com.xgt.dao.EduQuestionAnswerDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/6 11:41.
 */
@Service
public class EduQuestionAnswerService {

    private static final Logger logger = LoggerFactory.getLogger(EduQuestionAnswerService.class);

    @Autowired
    private EduQuestionAnswerDao eduQuestionAnswerDao;

    /**
     * 查询题目的答案
     * @param eduQuestionAnswer
     * @return
     */
    public Map queryEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        Integer total = eduQuestionAnswerDao.countEduQuestionAnswer(eduQuestionAnswer);
        List<EduQuestionAnswer> list = eduQuestionAnswerDao.queryEduQuestionAnswer(eduQuestionAnswer);
        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);
        return map;
    }

    /**
     * 修改答案
     * @param eduQuestionAnswer
     * @return
     */
    public Integer modifyEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        return eduQuestionAnswerDao.modifyEduQuestionAnswer(eduQuestionAnswer);
    }

    /**
     * 添加答案
     * @param eduQuestionAnswer
     * @return
     */
    public Integer addEduQuestionAnswer(EduQuestionAnswer eduQuestionAnswer){
        return eduQuestionAnswerDao.addEduQuestionAnswer(eduQuestionAnswer);
    }

    /**
     * 删除答案
     * @param id
     * @return
     */
    public Integer deleteEduQuestionAnswer(Integer id){
        return eduQuestionAnswerDao.deleteEduQuestionAnswer(id);
    }
    /**
     *  批量插入题目的答案
     * @author liuao
     * @date 2018/6/9 17:43
     */
    public void batchAddEduQuestionAnswer(List<EduQuestionAnswer> questionAnswerList) {
          eduQuestionAnswerDao.batchAddEduQuestionAnswer(questionAnswerList);
    }
}
