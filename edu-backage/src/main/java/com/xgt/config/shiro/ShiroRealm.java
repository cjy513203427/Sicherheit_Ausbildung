package com.xgt.config.shiro;

import com.xgt.entity.User;
import com.xgt.service.ResourceService;
import com.xgt.service.RoleService;
import com.xgt.service.UserService;
import com.xgt.util.EncryptUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Joanne
 * @Description
 * @create 2018-05-31 18:16
 **/
public class ShiroRealm extends AuthorizingRealm{

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private ResourceService resourceService ;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService ;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加权限
        resourceService.queryResourceListByUserIdAndType(user.getId(),"button").
                forEach(resource -> info.addStringPermission(resource.getPermission()));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("执行..............doGetAuthenticationInfo...........");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        User user = userService.queryUserByUsername(username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        // 账号不存在
        if(user==null){
            throw new UnknownAccountException();
        }
        //账户被禁用
        if(user.getStatus().equals("0")){
            throw new DisabledAccountException();
        }
        //通过本地服务器返回的代码比对，如果不一致则需要验证能否在其它地方登录
        user.setEncryptSalt(EncryptUtil.getDefaultSalt());
        user.setMenuList(resourceService.queryResourceListByUserIdAndType(user.getId(),"menu"));
        user.setRoleList(roleService.queryRoleListByUserId(user.getId()));
        user.setButtonList(resourceService.queryResourceListByUserIdAndType(user.getId(),"button"));

        return new SimpleAuthenticationInfo(
                user, //principal
                user.getPassword(), //credentials 密码
                ByteSource.Util.bytes(user.getEncryptSalt()),//salt=username+salt 若明文可不填
                getName() //realmName
        );
    }
}
