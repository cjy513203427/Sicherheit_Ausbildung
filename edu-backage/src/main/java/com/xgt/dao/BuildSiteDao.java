package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.dto.BuildSiteDto;
import com.xgt.entity.BuildSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 5618 on 2018/6/1.
 */
@Repository
public class BuildSiteDao extends BuildSite{

    private static final Logger logger = LoggerFactory.getLogger(BuildSiteDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "buildSite.";

    /**
     * 建筑工地信息查询
     *
     * @param buildSite
     * @return
     */
    public List<BuildSite> queryBuildSiteList(BuildSite buildSite) {
        String sqlId = name_space + "queryBuildSiteList";
        return daoClient.queryForList(sqlId, buildSite, BuildSite.class);
    }

    public List<BuildSiteDto> queryBuildSiteById(Integer labourerId){
        Map params = new HashMap();
        params.put("labourerId",labourerId);
        String sqlId = name_space + "queryBuildSiteById";
        return daoClient.queryForList(sqlId, params, BuildSiteDto.class);
    }

    /**
     * 查询该工地下的附件
     * @param labourerId
     * @return
     */
    public List<BuildSiteDto> queryBuildSiteAttachment(Integer labourerId){
        Map params = new HashMap();
        params.put("labourerId",labourerId);
        String sqlId = name_space + "queryBuildSiteAttachment";
        return daoClient.queryForList(sqlId, params, BuildSiteDto.class);
    }

    /**
     * 建筑工地信息数量查询
     *
     * @param buildSite
     * @return
     */
    public Integer queryBuildSiteCount(BuildSite buildSite) {
        String sqlId = name_space + "queryBuildSiteCount";
        return daoClient.queryForObject(sqlId, buildSite, Integer.class);
    }

    /**
     * 工地启用和禁用
     *
     * @param map
     */
    public void enOrDisBuildSite(Map<String, Object> map) {
        String sqlId = name_space + "enOrDisBuildSite";
        daoClient.excute(sqlId, map);
    }

    /**
     * 自动生成工地编号
     *
     * @return
     */
    public Integer insertNullRecord() {
        String sqlId = name_space + "insertNullRecord";
        return daoClient.insertAndGetId(sqlId, null);
    }

    /**
     * 新增建筑工地
     *
     * @param buildSite
     */
    public void createBuildSite(BuildSite buildSite) {
        String sqlId = name_space + "createBuildSite";
        daoClient.excute(sqlId, buildSite);
    }

    /**
     * 修改建筑工地
     *
     * @param buildSite
     */
    public void updateBuildSite(BuildSite buildSite) {
        String sqlId = name_space + "updateBuildSite";
        daoClient.excute(sqlId, buildSite);
    }

    /**
     * 禁用工地对应的账号
     *
     * @param code
     */
    public void disBuildLabourers(String code, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("status", status);
        String sqlId = name_space + "disBuildLabourers";
        daoClient.excute(sqlId, map);
    }

    /**
     * 查询公司的孩子节点
     * 从属于公司的工地
     * @return
     */
    public List<BuildSite> queryChildNode(){
        String sqlId = name_space + "queryChildNode";
        return daoClient.queryForList(sqlId,BuildSite.class);
    }

    /**
     * 根据公司id查询公司的孩子节点
     * 从属于公司的工地
     * @param companyId
     * @return
     */
    public List<BuildSite> queryChildNodeByCompanyId(Integer companyId){
        Map params = new HashMap();
        params.put("companyId",companyId);
        String sqlId = name_space + "queryChildNodeByCompanyId";
        return daoClient.queryForList(sqlId,params,BuildSite.class);
    }

    /**
     * 添加单位信息/新增机构
     * @param buildSite
     */
    public Integer addBuildSite(BuildSite buildSite){
        String sqlId = name_space + "addBuildSite";
        return daoClient.insertAndGetId(sqlId,buildSite);
    }

    /**
     * 修改单位信息/修改机构
     * @param buildSite
     * @return
     */
    public Integer modifyBuildSite(BuildSite buildSite){
        String sqlId = name_space + "modifyBuildSite";
        return daoClient.excute(sqlId,buildSite);
    }

    /**
     * 删除分公司/工地/单位
     * @param id
     */
    public Integer deleteBuildSite(Integer id){
        Map params = new HashMap();
        params.put("id",id);
        String sqlId = name_space + "deleteBuildSite";
        return daoClient.excute(sqlId,params);
    }

    /**
     * 更新工地code
     * @param id
     */
    public void updateBuildSiteCode(Integer id,String code){
        Map params = new HashMap();
        params.put("id",id);
        params.put("code",code);
        String sqlId = name_space + "updateBuildSiteCode";
        daoClient.excute(sqlId,params);
    }

    /**
     * @Description 根据siteCOde查询工地信息
     * @author Joanne
     * @create 2018/7/20 10:28
     */
    public BuildSite queryBuildSiteByCode(String buildSiteCode) {
        String sqlId = name_space + "buildSiteCode" ;
        Map map = new HashMap();
        map.put("buildSiteCode",buildSiteCode);
        return daoClient.queryForObject(sqlId,map,BuildSite.class);
    }
}
