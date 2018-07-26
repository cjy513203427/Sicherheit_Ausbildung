package com.xgt.service;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildCompanyDao;
import com.xgt.entity.BuildCompany;
import com.xgt.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 5618 on 2018/6/28.
 */
@Service
public class BuildCompanyService {

    private static final Logger logger = LoggerFactory.getLogger(BuildCompanyService.class);

    @Autowired
    private BuildCompanyDao buildCompanyDao;

    /**
     * 查询建筑公司列表（分页）
     *
     * @param buildCompany
     * @return
     */
    public Map<String, Object> queryBuildCompany(BuildCompany buildCompany) {
        Integer total = buildCompanyDao.queryBuildCompanyCount(buildCompany);
        List<BuildCompany> list = null;
        if (total != null && total > 0) {
            list = buildCompanyDao.queryBuildCompany(buildCompany);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", JSONUtil.filterDateProperties(list, BuildCompany.class));
        map.put("total", total);
        return map;
    }

    /**
     * 新增建筑公司
     *
     * @param buildCompany
     */
    public void createBuildCompany(BuildCompany buildCompany) {
        buildCompanyDao.createBuildCompany(buildCompany);
    }

    /**
     * 修改建筑公司
     *
     * @param buildCompany
     */
    public void updateBuildCompany(BuildCompany buildCompany) {
        buildCompanyDao.updateBuildCompany(buildCompany);
    }

    /**
     * 建筑公司启用和禁用
     *
     * @param id
     * @param status
     */
    public void enOrDisBuildCompany(Integer id, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (SystemConstant.State.ACTIVE_STATE.equals(status)) {
            map.put("status", SystemConstant.State.DISABLE_STATE);
        } else {
            map.put("status", SystemConstant.State.ACTIVE_STATE);
        }
        buildCompanyDao.enOrDisBuildCompany(map);
    }
}
