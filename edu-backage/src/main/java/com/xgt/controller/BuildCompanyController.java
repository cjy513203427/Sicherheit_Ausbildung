package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.BuildCompany;
import com.xgt.entity.User;
import com.xgt.service.BuildCompanyService;
import com.xgt.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;

/**
 * Created by 5618 on 2018/6/28.
 */
@Controller
@RequestMapping("/buildCompany")
public class BuildCompanyController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BuildCompanyController.class);

    @Autowired
    private BuildCompanyService buildCompanyService;

    /**
     * 查询建筑公司列表接口
     *
     * @param buildCompany
     * @return
     */
    @RequestMapping(value = "/queryBuildCompany", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryBuildCompany(BuildCompany buildCompany) {
        try {
            Map<String, Object> result = buildCompanyService.queryBuildCompany(buildCompany);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("——————queryBuildCompany方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 新增建筑公司接口
     *
     * @param buildCompany
     * @return
     */
    @RequestMapping(value = "/createBuildCompany", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> createBuildCompany(BuildCompany buildCompany) {
        try {
            //获取当前登录人信息
            User user = getUser();
            buildCompany.setCreateUserId(user.getId());
            buildCompany.setCreateUsername(user.getUsername());
            //默认创建状态无效
            buildCompany.setStatus(SystemConstant.State.DISABLE_STATE);
            buildCompanyService.createBuildCompany(buildCompany);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————createBuildCompany方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 修改建筑公司接口
     *
     * @param buildCompany
     * @return
     */
    @RequestMapping(value = "/updateBuildCompany", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateBuildCompany(BuildCompany buildCompany) {
        try {
            buildCompanyService.updateBuildCompany(buildCompany);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————updateBuildCompany方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 建筑公司启用和禁用
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/enOrDisBuildCompany", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> enOrDisBuildCompany(Integer id, String status) {
        try {
            buildCompanyService.enOrDisBuildCompany(id, status);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("——————enOrDisBuildCompany方法异常：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


}
