package com.xgt.config.shiro;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.BuildLabourerDao;
import com.xgt.entity.BuildLabourer;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class CutomerShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(CutomerShiroRealm.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Autowired
    private BuildLabourerDao labourerDao ;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        User user = (User)principalCollection.getPrimaryPrincipal();

        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        //添加权限
//        resourceService.queryResourceListByUserIdAndType(user.getId(),"button").
//                forEach(resource -> authorizationInfo.addStringPermission(resource.getPermission()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行..............doGetAuthenticationInfo...........");
        //获取用户的输入的账号.
        UsernamePasswordByLoginTypeToken token = (UsernamePasswordByLoginTypeToken) authenticationToken;

        String telephone = token.getUsername();

        // 查询条件
        BuildLabourer param = new BuildLabourer();
        param.setPhone(telephone);

        BuildLabourer labourer = labourerDao.queryLabourerOne(param);

        // 账号不存在
        if(labourer == null){
            throw new UnknownAccountException();
        }
        //账户被禁用
        if(SystemConstant.State.DISABLE_STATE.equals(labourer.getStatus())){
            throw new DisabledAccountException();
        }


        // 从 redis 获取验证码 ，这里需要完善
//        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
//        String validateCode = null ;
//        try {
//            validateCode =  valueOperations.get(telephone);
//        } catch (Exception e) {
//            logger.error("客户登录认证获取验证码异常",e);
//        }

//        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
//        logger.info(".............设置用户认证数据：{}.................",user);
        return new SimpleAuthenticationInfo(
                labourer, // 用户名
                labourer.getPassword(), // 密码
                getName()  //realm name
        );
    }
}
