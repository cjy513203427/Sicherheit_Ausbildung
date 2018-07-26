package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.constant.SystemConstant;
import com.xgt.dto.BuildLabourerDto;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.BuildSite;
import com.xgt.entity.exam.ExamLabourerRel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 5618 on 2018/6/1.
 */
@Repository
public class BuildLabourerDao implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(BuildLabourerDao.class);

    public static Map<String, BuildLabourer> labourerMap = new HashMap<>();

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "buildLabourer.";

    /**
     * 建筑工地人员信息查询
     *
     * @param buildLabourer
     * @return
     */
    public List<BuildLabourer> queryBuildLabourer(BuildLabourer buildLabourer) {
        String sqlId = name_space + "queryBuildLabourer";
        return daoClient.queryForList(sqlId, buildLabourer, BuildLabourer.class);
    }

    /**
     * 建筑工地人员信息数量查询
     *
     * @param buildLabourer
     * @return
     */
    public Integer queryBuildLabourerCount(BuildLabourer buildLabourer) {
        String sqlId = name_space + "queryBuildLabourerCount";
        return daoClient.queryForObject(sqlId, buildLabourer, Integer.class);
    }

    /**
     * 新增建筑工地人员信息
     *
     * @param buildLabourer
     */
    public void createBuildLabourer(BuildLabourer buildLabourer) {
        String sqlId = name_space + "createBuildLabourer";
        buildLabourer.setStatus(SystemConstant.State.DISABLE_STATE);
        Integer labourId = daoClient.insertAndGetId(sqlId, buildLabourer);
        // 更新人员指纹和基本信息
        updateLabourBuffer(labourId);
    }

    /**
     * 人员启用和禁用
     *
     * @param map
     */
    public void enOrDisBuildLabourer(Map<String, Object> map) {
        String sqlId = name_space + "enOrDisBuildLabourer";
        daoClient.excute(sqlId, map);
    }

    /**
     * 修改建筑工地人员信息
     *
     * @param buildLabourer
     */
    public void updateBuildLabourer(BuildLabourer buildLabourer) {
        String sqlId = name_space + "updateBuildLabourer";
        daoClient.excute(sqlId, buildLabourer);
        // 更新人员指纹和基本信息
        updateLabourBuffer(buildLabourer.getId());
    }

    /**
     * 修改密码
     *
     * @param buildLabourer
     */
    public void updateLabourerPsw(BuildLabourer buildLabourer) {
        String sqlId = name_space + "updateLabourerPsw";
        daoClient.excute(sqlId, buildLabourer);
    }

    /**
     * 手机端个人资料查询
     *
     * @param buildLabourer
     * @return
     */
    public BuildLabourer queryLabourerOne(BuildLabourer buildLabourer) {
        String sqlId = name_space + "queryLabourerOne";
        return daoClient.queryForObject(sqlId, buildLabourer, BuildLabourer.class);
    }

    /**
     * 根据工人id 查询出工人信息
     *
     * @author liuao
     * @date 2018/6/6 9:59
     */
    public BuildLabourer queryLabourerById(Integer labourId) {
        String sqlId = name_space + "queryLabourerOne";
        Map<String, Object> param = new HashMap<>();
        param.put("id", labourId);
        return daoClient.queryForObject(sqlId, param, BuildLabourer.class);
    }

    /**
     * 根据工人手机号 查询出工人信息
     *
     * @author liuao
     * @date 2018/6/6 9:59
     */
    public BuildLabourer queryLabourerByPhone(String phone) {
        String sqlId = name_space + "queryLabourerOne";
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        return daoClient.queryForObject(sqlId, param, BuildLabourer.class);
    }

    /**
     * 已经开通账号数量
     *
     * @param code
     */
    public Integer getHasedNumber(String code, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("status", status);
        String sqlId = name_space + "getHasedNumber";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * 获取个人中心信息
     * @return
     */
    public List<BuildLabourer> getPersonalCenter(Integer labourId){
        String sqlId = name_space + "getPersonalCenter";
        Map<String,Object> param = new HashMap<>();
        param.put("labourId", labourId);
        return daoClient.queryForList(sqlId,param,BuildLabourer.class);
    }

    /**
     * 查询学员
     * 参数buildCompanyId,buildSiteCode,idCard,startTime,endTime
     * @param buildLabourer
     * @return
     */
    public List<BuildLabourer> queryTrainee(BuildLabourer buildLabourer){
        String sqlId = name_space + "queryTrainee";
        return daoClient.queryForList(sqlId,buildLabourer,BuildLabourer.class);
    }

    /**
     * 统计学员个数
     * 参数buildCompanyId,buildSiteCode,idCard,startTime,endTime
     * @param buildLabourer
     * @return
     */
    public Integer countQueryTrainee(BuildLabourer buildLabourer){
        String sqlId = name_space + "countQueryTrainee";
        return daoClient.queryForObject(sqlId,buildLabourer,Integer.class);
    }

    /**
     * 批量转移，从子公司转移到另外一个子公司
     * @param ids
     */
    public Integer batchTransferBuildLabour(Integer[] ids,String buildSiteCode){
        String sqlId = name_space + "batchTransferBuildLabour";
        Map <String,Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("buildSiteCode",buildSiteCode);
        return daoClient.excute(sqlId,params);
    }

    /**
     * 删除工人，禁用
     * 离场
     * @param ids
     * @return
     */
    public Integer disableBuildLabourer(Integer[] ids){
        Map <String,Object> params = new HashMap<>();
        params.put("ids",ids);
        String sqlId = name_space + "disableBuildLabourer";
        return daoClient.excute(sqlId,params);
    }

    /**
     *  查询出所有人员的指纹信息
     * @author liuao
     * @date 2018/7/12 10:19
     */
    public List<BuildLabourer> queryLabourFingerBase64(){
        String sqlId = name_space + "queryLabourFingerBase64";
        return daoClient.queryForList(sqlId, BuildLabourer.class);
    }

    /**
     *  项目启动时将指纹信息加载到内存中
     * @author liuao
     * @date 2018/7/12 10:27
     */
    @Override
    public void afterPropertiesSet() {

        List<BuildLabourer> fingerList = queryLabourFingerBase64();
        if(CollectionUtils.isEmpty(fingerList)){
            return ;
        }
        for (BuildLabourer labourer : fingerList) {
            labourerMap.put(labourer.getIdCard(), labourer);
        }

        logger.info(".......缓存中缓存的工人数量...：{}...",labourerMap.size());
    }

    /**
     *  更新指纹缓存
     * @author liuao
     * @date 2018/7/12 11:34
     */
    public void updateLabourBuffer(Integer labourId){
        BuildLabourer labourer = queryLabourerById(labourId);
        labourerMap.put(labourer.getIdCard(), labourer);
    }

    /**
     * @Description 根据集团ID查询下属分公司
     * @author Joanne
     * @create 2018/7/13 9:52
     */
    public List<BuildSite> queryBuildSiteByCompanyId(Integer companyId) {
        String sqlId = name_space + "queryBuildSiteByCompanyId" ;
        Map param = new HashMap();
        param.put("companyId",companyId);
        return daoClient.queryForList(sqlId,param,BuildSite.class);
    }

    /**
     * @Description 查询以供选择受训的人员
     * @author Joanne
     * @create 2018/7/13 10:15
     */
    public List<BuildLabourer> queryLabourerForSelect(BuildLabourer buildLabourer) {
        String sqlId = name_space + "queryLabourerForSelect";
        return daoClient.queryForList(sqlId,buildLabourer,BuildLabourer.class);
    }

    /**
     * 批量导入工人
     * @param buildLabourerList
     */
    public void batchImportBuildLabourer(List<BuildLabourer> buildLabourerList){
        Map params = new HashMap();
        params.put("buildLabourerList",buildLabourerList);
        String sqlId = name_space + "batchImportBuildLabourer";
        daoClient.excute(sqlId, params);
    }

    /**
     * 新增学员基本信息
     * @param buildLabourer
     */
    public Integer addSingleBuildLabourer(BuildLabourer buildLabourer){
        String sqlId = name_space + "addSingleBuildLabourer";
        return daoClient.insertAndGetId(sqlId, buildLabourer);
    }

    /**
     * 修改学员信息
     * @param buildLabourer
     */
    public void updateSingleBuildLabourer(BuildLabourer buildLabourer){
        String sqlId = name_space + "updateSingleBuildLabourer";
        daoClient.excute(sqlId, buildLabourer);
    }

    /**
     * 根据id查询工人信息
     * @param id
     * @return
     */
    public BuildLabourerDto queryBuildLabourerById(Integer id){
        Map params = new HashMap();
        params.put("id",id);
        String sqlId = name_space + "queryBuildLabourerById";
        return daoClient.queryForObject(sqlId, params,BuildLabourerDto.class);
    }

    /**
     * 查询工人的附件
     * @param id
     * @return
     */
    public List<BuildLabourerDto> queryBuildLabourerAttachmentById(Integer id){
        Map params = new HashMap();
        params.put("id",id);
        String sqlId = name_space + "queryBuildLabourerAttachmentById";
        return daoClient.queryForList(sqlId, params,BuildLabourerDto.class);
    }

    /**
     *  根据身份证号查询人员信息
     * @author liuao
     * @date 2018/7/16 15:36
     */
    public BuildLabourer queryLabourByIdCard(String idCard){
        BuildLabourer labourer = BuildLabourerDao.labourerMap.get(idCard);
        return labourer;
    }

    /**
     * @Description 根据方案id查询已经参加培训考试的人员
     * @author Joanne
     * @create 2018/7/17 15:32
     */
    public List<ExamLabourerRel> queryExamLabourRel(Integer programId) {
        String sqlId = name_space + "queryExamLabourRel" ;
        Map map = new HashMap() ;
        map.put("programId",programId);
        return daoClient.queryForList(sqlId,map,ExamLabourerRel.class);
    }

    /**
     * @Description 培训系统PC端 - 根据id查询大于该id的本地人员
     * @author Joanne
     * @create 2018/7/19 19:43
     */
    public BuildLabourer queryNextLabourByPreviousId(Integer previousId) {
        String sqlId = name_space + "queryNextLabourByPreviousId" ;
        Map map = new HashMap() ;
        map.put("previousId",previousId);
        return daoClient.queryForObject(sqlId,map,BuildLabourer.class);
    }
}
