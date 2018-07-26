package com.xgt.controller;


import com.xgt.common.BaseController;
import com.xgt.config.shiro.UsernamePasswordByLoginTypeToken;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.UserDao;
import com.xgt.entity.BuildLabourer;
import com.xgt.entity.Customer;
import com.xgt.entity.User;
import com.xgt.enums.EnumPcsServiceError;
import com.xgt.service.BuildLabourerService;
import com.xgt.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static com.xgt.enums.EnumPcsServiceError.ERROR_OPERATE;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private SecurityManager securityManager;

//    @Autowired
//    private CustomerService customerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private BuildLabourerDao labourerDao ;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    @ResponseBody
    // 此方法不处理登录成功,由shiro进行处理.
    public Map<String, Object> login(User user, String loginType, Customer customer) throws Exception {
        loginType = StringUtils.isBlank(loginType) ? SystemConstant.LoginType.LOGIN_TYPE_USER : loginType;

        logger.info(".............login start.........");
        Map<String, Object> result = new HashMap<>();
        result.put("code", EnumPcsServiceError.SUCCESS.code);


        try {
            UsernamePasswordByLoginTypeToken token = initUsernamePasswordByLoginTypeToken(loginType, user, customer);
            SecurityUtils.setSecurityManager(securityManager);
            Subject subject = SecurityUtils.getSubject();
            token.setRememberMe(true);
            subject.login(token);
            // 保存opendid
            ifSaveOpenId(loginType, customer);
            logger.info(".............login success.........");
        } catch (AuthenticationException exception) {
//            logger.error("........认证失败.......", exception);
            // 登录失败从request中获取shiro处理的异常信息。
            // shiroLoginFailure:就是shiro异常类的全类名.
            EnumPcsServiceError msg = EnumPcsServiceError.BUSINESS_DATA_NONE;
            if (exception != null) {
                if (exception instanceof UnknownAccountException) {// 账号不存在
                    msg = EnumPcsServiceError.BUSINESS_USER_NONE;
                    logger.warn("login failed, account not existed. account={}", user.getUsername());
                } else if (exception instanceof IncorrectCredentialsException) {// 密码不正确
                    msg = EnumPcsServiceError.BUSINESS_USER_PASSWORD_ERROR;
                    logger.warn("login failed, password not correct. account={}", user.getUsername());
                } else if (exception instanceof ExcessiveAttemptsException) {// 登录失败多次
                    msg = EnumPcsServiceError.BUSINESS_USER_LOGIN_ERROR;
                    logger.warn("login failed, login failed times too much. account={}", user.getUsername());
                } else if ("kaptchaValidateFailed".equals(exception)) {// 验证码错误
                    msg = EnumPcsServiceError.BUSINESS_USER_VERIFYCODE_ERROR;
                } else if (exception instanceof DisabledAccountException) {
                    msg = EnumPcsServiceError.BUSINESS_USER_FORBIDDEN; //账户被禁用
                }
            }
            result.put("code", msg.code);
            result.put("message", msg.desc);
        } catch (Exception e) {
            logger.error("登陆异常......", e);
            result.put("code", ERROR_OPERATE.code);
            result.put("message", ERROR_OPERATE.desc);
        }
        return result;
    }


    /**
     * 初始化用戶
     *
     * @author liuao
     * @date 2018/4/7 17:59
     */
    public static UsernamePasswordByLoginTypeToken initUsernamePasswordByLoginTypeToken(String loginType,
                                                                                        User user, Customer customer) {

        UsernamePasswordByLoginTypeToken token = null;
        if (SystemConstant.LoginType.LOGIN_TYPE_USER.equals(loginType)) {
            token = new UsernamePasswordByLoginTypeToken(user.getUsername(), user.getPassword(), loginType);
        } else if (SystemConstant.LoginType.LOGIN_TYPE_CUSTOMER.equals(loginType)) {
            token = new UsernamePasswordByLoginTypeToken(customer.getPhone(), customer.getPassword(), loginType);
            // 设置token 中额外信息
            Map<String, Object> extraParam = new HashMap<>();
            extraParam.put("customer", customer);
            token.setExtraParam(extraParam);
        }
        return token;
    }

    /**
     * 注冊情況下保存客戶信息
     *
     * @author liuao
     * @date 2018/4/7 18:18
     */
    private void ifSaveOpenId(String loginType, Customer customer) {
        if (SystemConstant.LoginType.LOGIN_TYPE_CUSTOMER.equals(loginType)) {
            logger.info("客户信息:{}", customer.toString());


            BuildLabourer oldLabourer = labourerDao.queryLabourerByPhone(customer.getPhone());

            String oldOpenId = oldLabourer.getOpenId();// 数据库原来的openId
            String openIdFromPage = customer.getOpenId();// 页面传递的openId

            // 对于数据库没有openId ,且页面传递了openId 的，将页面的传递的openId 保存到数据库
            if (StringUtils.isBlank(oldOpenId) && StringUtils.isNotBlank(openIdFromPage)) {

                BuildLabourer updateParam = new BuildLabourer();
                updateParam.setId(oldLabourer.getId());
                updateParam.setOpenId(openIdFromPage);
                // 保存openId
                labourerDao.updateBuildLabourer(updateParam);
            }

        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> loginout() throws Exception {
        SecurityUtils.getSubject().logout();
        return ResultUtil.createSuccessResult();
    }

    /**
     * openId 登陆
     *
     * @author liuao
     * @date 2018/4/19 16:23
     */
//    @RequestMapping("/loginByOpenId")
//    @ResponseBody
//    public Map<String, Object> loginByOpenId(String openId, HttpServletRequest request) {
//        Customer customer = customerService.queryCustomerByOpenId(openId);
//        if (null == customer) {
//            return ResultUtil.createFailResult("用户不存在");
//        }
//        try {
//            customer.setValidateCode(SUPER_VALIDATECODE);
//            stringRedisTemplate.opsForValue().set(customer.getTelephone(), SUPER_VALIDATECODE, 5, TimeUnit.MINUTES);
//            UsernamePasswordByLoginTypeToken token = initUsernamePasswordByLoginTypeToken(
//                    SystemConstant.LoginType.LOGIN_TYPE_CUSTOMER
//                    , null, customer);
//            SecurityUtils.setSecurityManager(securityManager);
//            Subject subject = SecurityUtils.getSubject();
//            token.setRememberMe(true);
//            subject.login(token);
//            logger.info(".............login success.........");
//        } catch (Exception exception) {
//            logger.error("opendId登陆异常！openId="+openId, exception);
//            return ResultUtil.createFailResult("opendId登陆异常！");
//        }
//        return ResultUtil.createSuccessResult("登陆成功！");
//    }

    /**
     * 钉钉id 登陆
     *
     * @author liuao
     * @date 2018/5/3 16:05
     */
//    @RequestMapping(value = "/loginByDingID", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public Map<String, Object> loginByDingID(String code) {
//        try {
//            logger.info("钉钉登陆获取code.............:{}",code);
//            String accessToken = AuthHelper.getAccessToken();
//            logger.info("钉钉登陆获取accessToken.............:{}",accessToken);
//            String dingId = UserHelper.getUserInfo(accessToken, code).getUserid();
//            logger.info("钉钉登陆获取dingId.............:{}",dingId);
//
//            User user = userDao.queryUserByDingId(dingId);
//            user.setPassword(SUPER_VALIDATECODE); // 使用超级密码登陆
////            logger.info("获取 User 信息........{}", JSONUtils.toJSONString(user));
//            UsernamePasswordByLoginTypeToken token = initUsernamePasswordByLoginTypeToken(
//                    SystemConstant.LoginType.LOGIN_TYPE_USER
//                    , user, null);
//            SecurityUtils.setSecurityManager(securityManager);
//            Subject subject = SecurityUtils.getSubject();
//            token.setRememberMe(true);
//            subject.login(token);
//            return ResultUtil.createSuccessResult("登陆成功！");
//        } catch (Exception e) {
//            logger.error("钉钉登陆异常：", e);
//            return ResultUtil.createFailResult("钉钉登陆异常！");
//        }
//    }







    /**
     * userid 登陆
     *
     * @author liuao
     * @date 2018/5/3 16:05
     */
//    @RequestMapping(value = "/loginByUserID", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public Map<String, Object> loginByUserID(Integer userId) {
//        try {
//            logger.info("..........userId.............:{}",userId);
//
//            User user = userDao.queryUserById(userId);
//            user.setPassword(SUPER_VALIDATECODE); // 使用超级密码登陆
////            logger.info("获取 User 信息........{}", JSONUtils.toJSONString(user));
//            UsernamePasswordByLoginTypeToken token = initUsernamePasswordByLoginTypeToken(
//                    SystemConstant.LoginType.LOGIN_TYPE_USER
//                    , user, null);
//            SecurityUtils.setSecurityManager(securityManager);
//            Subject subject = SecurityUtils.getSubject();
//            token.setRememberMe(true);
//            subject.login(token);
//            logger.info(".....loginByUserID .........success.........");
//            return ResultUtil.createSuccessResult("登陆成功！");
//        } catch (Exception e) {
//            logger.error("loginByUserID.......异常：", e);
//            return ResultUtil.createFailResult("loginByUserID.......异常！");
//        }
//    }

}
