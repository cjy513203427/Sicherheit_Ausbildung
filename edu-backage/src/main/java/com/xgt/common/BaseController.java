package com.xgt.common;

import com.xgt.entity.BuildLabourer;
import com.xgt.entity.User;
import com.xgt.enums.EnumPcsServiceError;
//import org.apache.shiro.SecurityUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Base controller which contains common methods for other controllers.
 */
public class BaseController {

    @Value("${default.pageSize}")
    private int defPageSize;


    @Value("${default.maxPageSize}")
    private int defMaxPageSize;


    @Value("${aliyunOSS.accessKeyId}")
    protected String accessKeyId;

    @Value("${aliyunOSS.accessKeySecret}")
    protected String accessKeySecret;

    @Value("${aliyunOSS.uploadUrl}")
    protected String endpoint;

    @Value("${aliyunOSS.bucketname}")
    protected String bucketName;

    @Value("${aliyunOSS.imageAddress}")
    protected String imageAddress;


    /**
     * 构建 分页 页数
     *
     * @param page 页
     * @return int
     */
    protected int getCurPage(Integer page) {
        if (page == null || page < 1) {
            return 1;
        }
        return page;
    }

    /**
     * 构建 分页 每页显示条数
     *
     * @param pageSize 每页显示条数
     * @return int
     */
    protected int getPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1) {
            return defPageSize;
        }
        if (pageSize > defMaxPageSize) {
            return defMaxPageSize;
        }
        return pageSize;
    }

    /**
     * 获取ct_user用户表当前登录人id
     * （后端）
     *
     * @return
     */
    protected Integer getLoginUserId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getId();
    }

    /**
     * 获取build_labourer表当前登录人id
     * （前端）
     *
     * @return
     */
    protected Integer getLabourerUserId() {
        BuildLabourer buildLabourer = (BuildLabourer) SecurityUtils.getSubject().getPrincipal();
        return buildLabourer.getId();
    }

    protected String getLoginUserEmployeeNo() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getEmployeeNo();
    }

    protected List<Integer> getDepartmentUserIdList() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getDepartmentUserIdList();
    }

    protected String getCustomerTelephone() {
        String telephone = (String) SecurityUtils.getSubject().getPrincipal();
        return telephone;
    }

    /**
     * @Description 获取当前用户的建筑集团编号
     * @author HeLiu
     * @date 2018/6/29 14:49
     */
    protected Integer getCompanyId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getCompanyId();
    }

    /**
     * 获取当前登陆的工人信息
     *
     * @author liuao
     * @date 2018/6/5 19:12
     */
    public BuildLabourer getLoginLabourer() {
        return (BuildLabourer) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取ct_user用户表当前登录人
     *
     * @return
     */
    public User getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

}
