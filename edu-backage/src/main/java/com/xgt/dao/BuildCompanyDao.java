package com.xgt.dao;

import com.xgt.base.DaoClient;
import com.xgt.entity.BuildCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjy
 * @Date 2018/6/26 10:24.
 */
@Repository
public class BuildCompanyDao {
    private static final Logger logger = LoggerFactory.getLogger(BuildLabourerDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String name_space = "buildCompany.";

    /**
     * 查询父节点
     * 查询id和companyName
     *
     * @return
     */
    public List<BuildCompany> queryParentNode(Integer userId) {
        Map params = new HashMap();
        params.put("userId",userId);
        String sqlId = name_space + "queryParentNode";
        return daoClient.queryForList(sqlId,params ,BuildCompany.class);
    }

    /**
     * 查询建筑公司列表数量
     *
     * @param buildCompany
     * @return
     */
    public Integer queryBuildCompanyCount(BuildCompany buildCompany) {
        String sqlId = name_space + "queryBuildCompanyCount";
        return daoClient.queryForObject(sqlId, buildCompany, Integer.class);
    }

    /**
     * 查询建筑公司列表
     *
     * @param buildCompany
     * @return
     */
    public List<BuildCompany> queryBuildCompany(BuildCompany buildCompany) {
        String sqlId = name_space + "queryBuildCompany";
        return daoClient.queryForList(sqlId, buildCompany, BuildCompany.class);
    }

    /**
     * 新增建筑公司
     *
     * @param buildCompany
     */
    public void createBuildCompany(BuildCompany buildCompany) {
        String sqlId = name_space + "createBuildCompany";
        daoClient.insertAndGetId(sqlId, buildCompany);
    }

    /**
     * 修改建筑公司
     *
     * @param buildCompany
     */
    public void updateBuildCompany(BuildCompany buildCompany) {
        String sqlId = name_space + "updateBuildCompany";
        daoClient.excute(sqlId, buildCompany);
    }

    /**
     * 建筑公司启用和禁用
     *
     * @param map
     */
    public void enOrDisBuildCompany(Map<String, Object> map) {
        String sqlId = name_space + "enOrDisBuildCompany";
        daoClient.excute(sqlId, map);
    }
}
