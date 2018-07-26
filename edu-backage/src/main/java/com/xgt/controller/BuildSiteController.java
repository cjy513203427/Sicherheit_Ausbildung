package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.entity.BuildAttachment;
import com.xgt.entity.BuildCompany;
import com.xgt.entity.BuildSite;
import com.xgt.service.BuildSiteService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;


/**
 * Created by 5618 on 2018/6/1.
 */

@Controller
@RequestMapping("/buildSite")
public class BuildSiteController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BuildSiteController.class);

    @Autowired
    private BuildSiteService buildSiteService;

    /**
     * 建筑工地信息查询接口
     *
     * @param buildSite
     * @return
     */
    @RequestMapping(value = "/queryBuildSiteList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryBuildSiteList(BuildSite buildSite) {
        Map<String, Object> result = null;
        try {
            result = buildSiteService.queryBuildSiteList(buildSite);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryBuildSiteList方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 获取指定id的工地信息
     * @param labourerId
     * @return
     */
    @RequestMapping(value = "/queryBuildSiteById", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryBuildSiteById(Integer labourerId) {
        Map<String, Object> result = null;
        try {
            result = buildSiteService.queryBuildSiteById(labourerId);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryBuildSiteById方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 工地启用和禁用接口
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/enOrDisBuildSite", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> enOrDisBuildSite(Integer id, String status, String code) {
        try {
            buildSiteService.enOrDisBuildSite(id, status, code);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————enOrDisBuildSite方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 新增建筑工地接口
     *
     * @param buildSite
     * @return
     */
    @RequestMapping(value = "/createBuildSite", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> createBuildSite(BuildSite buildSite) {
        try {
            buildSite.setCreateUserId(getLoginUserId());
            buildSiteService.createBuildSite(buildSite);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————createBuildSite方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 修改建筑工地接口
     *
     * @param buildSite
     * @return
     */
    @RequestMapping(value = "/updateBuildSite", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateBuildSite(BuildSite buildSite) {
        try {
            buildSiteService.updateBuildSite(buildSite);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————updateBuildSite方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 生成公司-子公司二级树
     * @return
     */
    @RequestMapping(value = "/generateTree", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> generateTree() {
        List<BuildCompany> result = null;
        try {
            //Integer userId = getLoginUserId();
            result = buildSiteService.generateTree(1);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————generateTree：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 添加单位/新增机构
     * @param files
     * @param buildAttachment
     * @param buildSite
     * @return
     */
    @RequestMapping(value = "/addBuildSite")
    @ResponseBody
    public Map<String,Object> addBuildSite(@RequestParam("fileUpload[]") String[] files,@RequestParam("fileNames[]") String[] fileNames, BuildAttachment buildAttachment, BuildSite buildSite) {
        try {
            buildSiteService.addBuildSite(files,fileNames,buildSite,buildAttachment);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————addBuildSite：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 修改单位/修改机构
     * @param files
     * @param fileNames
     * @param buildAttachment
     * @param buildSite
     * @return
     */
    @RequestMapping(value = "/modifyBuildSite")
    @ResponseBody
    public Map<String,Object> modifyBuildSite(@RequestParam("fileUpload[]") String[] files,@RequestParam("fileNames[]") String[] fileNames, BuildAttachment buildAttachment, BuildSite buildSite) {
        try {
            buildSiteService.modifyBuildSite(files,fileNames,buildSite,buildAttachment);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————addBuildSite：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 删除分公司/工地/单位
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteBuildSite", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteBuildSite(Integer id) {
        try {
            Integer effectdNum = buildSiteService.deleteBuildSite(id);
            return ResultUtil.createSuccessResult(effectdNum);
        } catch (Exception e) {
            logger.error("——————deleteBuildSite方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

}
