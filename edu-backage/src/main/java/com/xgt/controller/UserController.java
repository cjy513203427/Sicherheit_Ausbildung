package com.xgt.controller;

import com.xgt.common.BaseController;
import com.xgt.constant.SystemConstant;
import com.xgt.entity.Resource;
import com.xgt.entity.User;
import com.xgt.service.ResourceService;
import com.xgt.service.UserService;
import com.xgt.util.EncryptUtil;
import com.xgt.util.ResultUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.State.ACTIVE_STATE;
import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;
import static org.apache.poi.poifs.crypt.Decryptor.DEFAULT_PASSWORD;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService ;

    @Autowired
    private ResourceService resourceService ;

    @RequestMapping(value = "/queryUser", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryUser(){
        try {
            List<User> result =  userService.queryUser();
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("confirmComplete .......异常，orderProgressId="  , e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    /**
     * 获取用户登录的基础信息
     *
     * @author CC
     * @date 2018/4/4 09:44
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info(".......user info ..........{}", user.toString());
        List<Resource> list = resourceService.buildResource(user.getMenuList());
        User newUser = (User) BeanUtils.cloneBean(user);
        newUser.setMenuList(list);
        newUser.setButtonList(user.getButtonList());
        newUser.setImageAddressUrl(imageAddress);
        return ResultUtil.createSuccessResult(newUser);

    }

    @RequestMapping("/validateLogin")
    @ResponseBody
    public Map<String, Object> validateLogin() {
        return ResultUtil.createSuccessResult("GGsimida");
    }

    /**
     * 查询用户列表
     *
     * @author liuao
     * @date 2018/4/2 13:44
     */
    @RequestMapping("/queryUserList")
    @ResponseBody
    public Map<String, Object> queryUserList(User user) {
        try {
            Map<String, Object> result = userService.queryUserList(user);
            return ResultUtil.createSuccessResult(result);
        } catch (Exception e) {
            logger.error("查询用户列表异常：user=" + user.toString(), e);
            return ResultUtil.createFailResult("查询用户列表异常");
        }
    }

    /**
     * 保存用户信息
     *
     * @author liuao
     * @date 2018/4/2 13:44
     */
    @RequestMapping("/saveUser")
    @ResponseBody
    public Map<String, Object> saveUser(User user) {
        try {
            String passwordMd5 = EncryptUtil.md5(DEFAULT_PASSWORD, user.getUsername(), 2);
            user.setPassword(passwordMd5);
            userService.saveUser(user);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("保存异常： user=" + user, e);
            return ResultUtil.createFailResult("保存异常");
        }
    }

    /*
     * @author Joanne
     * @date 2018/4/10 10:12
     * @Description 修改用户
     */
    @RequestMapping("/modifyUser")
    @ResponseBody
    public Map<String, Object> modifyUser(User user) {
        try {
            userService.modifyUser(user);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("更新用户异常：", e);
            return ResultUtil.createFailResult("更新用户异常");
        }
    }

    /*
     * @author Joanne
     * @date 2018/4/10 12:29
     * @Description 禁用用户
     */
    @RequestMapping("/disable")
    @ResponseBody
    public Map<String, Object> disable(String userIds) {
        try {
            Assert.notNull(userIds, "用户ID不能为空");
            userService.changeUserState(userIds, SystemConstant.State.DISABLE_STATE);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("disable..........异常 userIds=" + userIds, e);
            return ResultUtil.createFailResult("disable..........异常 userIds =" + userIds);
        }
    }

    /**
     * @author Joanne
     * @date 2018/4/10 12:33
     * @Description 激活用户
     */
    @RequestMapping("/active")
    @ResponseBody
    public Map<String, Object> active(String userId) {
        try {
            Assert.notNull(userId, "用户ID不能为空");
            userService.changeUserState(userId,ACTIVE_STATE);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("active..........异常 userId=" + userId, e);
            return ResultUtil.createFailResult("active..........异常 userId =" + userId);
        }
    }

    /**
     * @author Joanne
     * @date 2018/4/10 14:35
     * @Description 重置密码
     */
    @RequestMapping("/resetpassword")
    @ResponseBody
    public Map<String, Object> resetPassword(String[] userIds, String[] username) {
        try {
            Assert.notNull(userIds, "用户ID不能为空");
            if (userIds.length != username.length) {
                logger.info("重置密码出错");
                return ResultUtil.createFailResult("重置密码出错");
            }
            userService.resetPassword(userIds, username);
            return ResultUtil.createSuccessResult();
        } catch (Exception e) {
            logger.error("resetpassword..........异常 userIds=" + userIds + " username=" + username, e);
            return ResultUtil.createFailResult("resetpassword..........异常 userIds =" + userIds + " username=" + username);
        }
    }
}
