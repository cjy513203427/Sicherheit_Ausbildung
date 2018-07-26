package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.dto.EduPlanDto;
import com.xgt.dto.EduProgramDto;
import com.xgt.entity.EduAnswertoolRel;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeLiu
 * @Description 进入培训Dao层
 * @date 2018/7/6 15:02
 */
@Repository
public class InTheTrainDao {

    private static final Logger logger = LoggerFactory.getLogger(InTheTrainDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "inTheTrain.";

    /**
     * @Description 培训系统PC版-查询培训方案列表
     * @author HeLiu
     * @date 2018/7/6 15:32
     */
    public List<EduProgramDto> queryEduProgramList(Integer companyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        String sqlId = name_space + "queryEduProgramList";
        return daoClient.queryForList(sqlId, map, EduProgramDto.class);
    }

    /**
     * @Description 培训系统PC版-查询进行中的培训计划列表
     * @author HeLiu
     * @date 2018/7/6 16:46
     */
    public List<EduPlanDto> queryRunningPlanList(Integer companyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        String sqlId = name_space + "queryRunningPlanList";
        return daoClient.queryForList(sqlId, map, EduPlanDto.class);
    }

    /**
     * @Description 培训系统PC版-查询考试题目（视频题目）
     * @author HeLiu
     * @date 2018/7/11 15:12
     */
    public Map<String, Object> queryTestQuestion(Integer programId, Integer planId, Integer questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        map.put("planId", planId);
        map.put("questionId", questionId);
        String sqlId = name_space + "queryTestQuestion";
        return daoClient.queryForMap(sqlId, map);
    }

    /**
     *  保存人员和答题器的,对应关系
     * @author liuao
     * @date 2018/7/16 14:12
     */
    public void saveAnswertoolRel(List<EduAnswertoolRel> answertoolRelList) {
        Map<String, Object> map = new HashMap<>();
        map.put("answertoolRelList", answertoolRelList);
        String sqlId = name_space + "saveAnswertoolRel";
        daoClient.excute(sqlId, map);
    }

    /**
     *  根据培训计划id，查询
     * @author liuao
     * @date 2018/7/17 16:37
     */
    public void deleteAnswerToolRel(Integer planId) {
        String sqlId = name_space + "deleteAnswerToolRel";
        Map<String, Object> param = new HashMap<>();
        param.put("planId", planId);
        daoClient.excute(sqlId, param);
    }

    /**
     *  根据计划id 和 答题器编号 查询工人id
     * @author liuao
     * @date 2018/7/17 19:05
     */
    public Integer queryLabourIdByToolNumAndPlanId(String answerToolNum, Integer planId){
        String sqlId = name_space + "queryLabourIdByToolNumAndPlanId";
        Map<String, Object> param = new HashMap<>();
        param.put("planId", planId);
        param.put("answerToolNum", answerToolNum);
        return daoClient.queryForObject(sqlId, param, Integer.class);
    }

}
