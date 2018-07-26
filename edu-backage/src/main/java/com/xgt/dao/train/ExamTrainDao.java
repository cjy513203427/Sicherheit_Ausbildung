package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.EduExamQuestionRel;
import com.xgt.entity.exam.ExamMock;
import com.xgt.entity.train.EduExamTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamTrainDao {

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "examTrain" + SystemConstant.SPOT;


    /**
     *  根据方案id 查询方案对应的试卷信息
     * @author liuao
     * @date 2018/7/17 14:24
     */
    public EduExamTrain queryTrainExamByProgramId(Integer programId){
        String sqlId = name_space + "queryTrainExamByProgramId";
        Map<String,Object> param = new HashMap<>();
        param.put("programId", programId);
        return daoClient.queryForObject(sqlId, param, EduExamTrain.class);
    }

    /**
     *  查询人员是否参与本次考试
     * @author liuao
     * @date 2018/7/17 14:44
     */
    public boolean queryLabourInExamExsit(Integer examId, Integer [] labourIds ){
        String sqlId = name_space + "queryLabourInExamExsit";
        Map<String,Object> param = new HashMap<>();
        param.put("examId", examId);
        param.put("labourIds", labourIds);
        int count =  daoClient.queryForObject(sqlId, param, Integer.class);
        if(count >0){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  根据试卷id ,查询出试卷的所有试题，按照题目的id 排序
     * @author liuao
     * @date 2018/7/17 15:59
     */
    public List<Integer> queryQuestionIdListByExamId(Integer examId, String examType){
        String sqlId = name_space + "queryQuestionIdListByExamId";
        Map<String,Object> param = new HashMap<>();
        param.put("examId", examId);
        param.put("examType", examType);
        return daoClient.queryForList(sqlId, param, Integer.class);
    }
}
