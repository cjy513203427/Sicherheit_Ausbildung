package com.xgt.dao.train;

import com.xgt.base.DaoClient;
import com.xgt.dto.*;
import com.xgt.entity.train.EduTrainPlan;
import com.xgt.entity.train.EduTraplanLabourRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author HeLiu
 * @Description 培训计划dao层
 * @date 2018/6/29 14:15
 */
@Repository
public class PlanDao {

    private final static Logger logger = LoggerFactory.getLogger(PlanDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "plan.";

    /**
     * @Description 培训系统PC版-查询培训记录列表
     * @author HeLiu
     * @date 2018/6/29 15:28
     */
    public List<EduPlanDto> queryPlanList(EduPlanDto eduPlanDto) {
        String sqlId = name_space + "queryPlanList";
        return daoClient.queryForList(sqlId, eduPlanDto, EduPlanDto.class);
    }

    /**
     * @Description 培训系统PC版-查询培训计划合格人数
     * @author HeLiu
     * @date 2018/6/29 16:53
     */
    public Integer queryPassCount(String examType, String passStatus, Integer programId, Integer planId) {
        Map<String, Object> map = new HashMap();
        map.put("examType", examType);
        map.put("passStatus", passStatus);
        map.put("programId", programId);
        map.put("planId", planId);
        String sqlId = name_space + "queryPassCount";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * @Description 培训系统PC版-查询培训记录列表数量
     * @author HeLiu
     * @date 2018/6/30 9:48
     */
    public Integer queryPlanCount(EduPlanDto eduPlanDto) {
        String sqlId = name_space + "queryPlanCount";
        return daoClient.queryForObject(sqlId, eduPlanDto, Integer.class);
    }

    /**
     * @Description 培训系统PC版-查询培训记录详情
     * @author HeLiu
     * @date 2018/6/30 15:46
     */
    public List<EduPlanDetailsDto> queryPlanDetails(Integer programId, Integer planId, String examType) {
        Map<String, Object> map = new HashMap();
        map.put("programId", programId);
        map.put("planId", planId);
        map.put("examType", examType);
        String sqlId = name_space + "queryPlanDetails";
        return daoClient.queryForList(sqlId, map, EduPlanDetailsDto.class);
    }

    /**
     * @Description 新增培训计划
     * @author Joanne
     * @create 2018/7/12 17:30
     */
    public Integer addPlan(EduTrainPlan eduTrainPlan) {
        String sqlId = name_space + "addPlan";
        return daoClient.insertAndGetId(sqlId, eduTrainPlan);
    }

    /**
     * @Description 培训系统PC版-查询培训信息用于导出word
     * @author HeLiu
     * @date 2018/7/16 15:00
     */
    public EduPlanDto queryTrainInfo(Integer companyId, Integer planId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("planId", planId);
        String sqlId = name_space + "queryTrainInfo";
        return daoClient.queryForObject(sqlId, map, EduPlanDto.class);
    }

    /**
     * @Description 培训系统PC版-查询培训内容用于导出word
     * @author HeLiu
     * @date 2018/7/16 17:07
     */
    public List<TrainContentDto> queryTrainContent(Integer programId) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        String sqlId = name_space + "queryTrainContent";
        return daoClient.queryForList(sqlId, map, TrainContentDto.class);
    }

    /**
     * @Description 培训系统PC版-查询全部学员的考试成绩用于导出word
     * @author HeLiu
     * @date 2018/7/16 20:38
     */
    public List<UserDto> queryAllUserExamScore(Integer programId, Integer planId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        map.put("planId", planId);
        map.put("examType", examType);
        String sqlId = name_space + "queryAllUserExamScore";
        return daoClient.queryForList(sqlId, map, UserDto.class);
    }

    /**
     * @Description 培训系统PC版-查询我的答案
     * @author HeLiu
     * @date 2018/7/16 20:48
     */
    public List<String> queryMyAnswers(Integer programId, Integer labourerId, String examType) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        map.put("labourerId", labourerId);
        map.put("examType", examType);
        String sqlId = name_space + "queryMyAnswers";
        return daoClient.queryForList(sqlId, map, String.class);
    }

    /**
     * @Description 更新培训计划的方案名称
     * @author Joanne
     * @create 2018/7/12 18:27
     */
    public void updatePlan(EduTrainPlan eduTrainPlan) {
        String sqlId = name_space + "updatePlan";
        daoClient.excute(sqlId, eduTrainPlan);
    }

    /**
     * @Description 保存计划人员关系
     * @author Joanne
     * @create 2018/7/13 17:03
     */
    public void savePlanLabourRel(Integer planId, List<String> labourIdList) {
        String sqlId = name_space + "savePlanLabourRel";
        Map map = new HashMap();
        map.put("planId", planId);
        map.put("labourIds", labourIdList);
        daoClient.batchInsertAndGetId(sqlId, map);
    }

    /**
     * @Description 删除计划人员关系
     * @author Joanne
     * @create 2018/7/13 18:31
     */
    public void deleteLabPlanRel(Integer planId) {
        String sqlId = name_space + "deleteLabPlanRel";
        Map map = new HashMap();
        map.put("planId", planId);
        daoClient.excute(sqlId, map);
    }

    /**
     * @Description 培训系统PC版 - 方案修改查询详情
     * @author Joanne
     * @create 2018/7/16 20:26
     */
    public EduTrainPlan queryPlanById(Integer id) {
        String sqlId = name_space + "queryPlanById";
        Map map = new HashMap();
        map.put("id", id);
        return daoClient.queryForObject(sqlId, map, EduTrainPlan.class);
    }

    /**
     * @Description 查询计划人员关系
     * @author Joanne
     * @create 2018/7/16 20:38
     */
    public List<EduTraplanLabourRel> queryRel(Integer id) {
        String sqlId = name_space + "queryRel";
        Map map = new HashMap();
        map.put("id", id);
        return daoClient.queryForList(sqlId, map, EduTraplanLabourRel.class);
    }

    /**
     * @Description 培训系统PC版 - 删除计划
     * @author Joanne
     * @create 2018/7/16 21:26
     */
    public void deletePlan(Integer id) {
        String sqlId = name_space + "deletePlan";
        Map map = new HashMap();
        map.put("id", id);
        daoClient.excute(sqlId, map);
    }

    /**
     * @Description 培训系统PC版 - 查询题目集合用于导出word
     * @author HeLiu
     * @date 2018/7/17 11:37
     */
    public List<QuestionsDto> queryQuestionsList(Integer programId, String examType, String questionType) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        map.put("examType", examType);
        map.put("questionType", questionType);
        String sqlId = name_space + "queryQuestionsList";
        return daoClient.queryForList(sqlId, map, QuestionsDto.class);
    }

    /**
     * @Description 培训系统PC版 - 查询选项集合用于导出word
     * @author HeLiu
     * @date 2018/7/17 12:13
     */
    public List<OptionsDto> queryOptions(Integer questionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", questionId);
        String sqlId = name_space + "queryOptions";
        return daoClient.queryForList(sqlId, map, OptionsDto.class);
    }

    /**
     * @Description 培训系统PC版 - 查询根据ID查询方案名称
     * @author HeLiu
     * @date 2018/7/17 14:22
     */
    public String queryProgramNameById(Integer programId) {
        Map<String, Object> map = new HashMap<>();
        map.put("programId", programId);
        String sqlId = name_space + "queryProgramNameById";
        return daoClient.queryForObject(sqlId, map, String.class);
    }

    /**
     * 查询学员培训信息
     * @param labourerId
     * @param companyId
     * @return
     */
    public List<EduPlanDto> queryBuildLabourerTrainInfo(Integer labourerId,Integer companyId,Integer examId){
        Map<String,Object> params = new HashMap<>();
        params.put("labourerId",labourerId);
        params.put("companyId",companyId);
        params.put("examId",examId);
        String sqlId = name_space + "queryBuildLabourerTrainInfo";
        return daoClient.queryForList(sqlId,params,EduPlanDto.class);
    }

    /**
     * 查询学员培训内容
     * @param labourerId
     * @return
     */
    public List<TrainContentDto> queryBuildLabourerTrainContent(Integer labourerId,Integer examId){
        Map<String,Object> params = new HashMap<>();
        params.put("labourerId",labourerId);
        params.put("examId",examId);
        String sqlId = name_space + "queryBuildLabourerTrainContent";
        return daoClient.queryForList(sqlId,params,TrainContentDto.class);
    }

    /**
     * 获取数组examId
     * @param labourerId
     * @return
     */
    public List<Integer> getExamIdFromEduExamlabourRel(Integer labourerId){
        Map<String,Object> params = new HashMap<>();
        params.put("labourerId",labourerId);
        String sqlId = name_space + "getExamIdFromEduExamlabourRel";
        return daoClient.queryForList(sqlId,params,Integer.class);
    }

    /**
     * 获取分数数组
     * @param labourerId
     * @param examId
     * @return
     */
    public Integer getExamScoreFromEduExamlabourRel(Integer labourerId,Integer examId){
        Map<String,Object> params = new HashMap<>();
        params.put("labourerId",labourerId);
        params.put("examId",examId);
        String sqlId = name_space + "getExamScoreFromEduExamlabourRel";
        return daoClient.queryForObject(sqlId,params,Integer.class);
    }

    /**
     * 获取学员的回答信息
     * @param labourerId
     * @param examId
     * @return
     */
    public List<UserDto> getAnswerFromEduExamAnswercard(Integer labourerId,Integer examId){
        Map<String,Object> params = new HashMap<>();
        params.put("labourerId",labourerId);
        params.put("examId",examId);
        String sqlId = name_space + "getAnswerFromEduExamAnswercard";
        return daoClient.queryForList(sqlId,params,UserDto.class);
    }

    /**
     * 获取正确答案
     * @return
     */
    public List<AnswerKeyDto> getTrueAnswerFromEduQuestion(){
        String sqlId = name_space + "getTrueAnswerFromEduQuestion";
        return daoClient.queryForList(sqlId,AnswerKeyDto.class);
    }

    /**
     * 获取试卷选项和选项内容
     * @param questionType
     * @return
     */
    public List<EduQuestionDto> getEduQuestionInfo(String questionType,Integer examId,Integer questionId){
        Map params = new HashMap();
        params.put("questionType",questionType);
        params.put("examId",examId);
        params.put("questionId",questionId);
        String sqlId = name_space + "getEduQuestionInfo";
        return daoClient.queryForList(sqlId,params,EduQuestionDto.class);
    }

    /**
     * 获取试卷题目
     * @param examId
     * @return
     */
    public List<EduQuestionDto> getEduQuestionTitleInfo(String questionType,Integer examId){
        Map params = new HashMap();
        params.put("questionType",questionType);
        params.put("examId",examId);
        String sqlId = name_space + "getEduQuestionTitleInfo";
        return daoClient.queryForList(sqlId,params,EduQuestionDto.class);
    }

    /**
     * @Description 培训系统PC版 - 根据方案id删除方案计划
     * @author Joanne
     * @create 2018/7/19 10:47
     */
    public void deletePlanByProgramId(Integer programId) {
        String sqlId = name_space + "deletePlan" ;
        Map map = new HashMap();
        map.put("programId", programId);
        daoClient.excute(sqlId, map);
    }
}