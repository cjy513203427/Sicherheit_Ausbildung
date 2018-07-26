package com.xgt.config.shiro;

import com.xgt.constant.SystemConstant;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Map;


public class CustomRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 存放realm
     */
    private Map<String, Realm> definedRealms;

    public void setDefinedRealms(Map<String, Realm> definedRealms) {
        this.definedRealms = definedRealms;
    }

    /**
     * 根据用户类型判断使用哪个Realm
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        super.assertRealmsConfigured();
        Realm realm = null;
        // 使用自定义Token
        UsernamePasswordByLoginTypeToken token = (UsernamePasswordByLoginTypeToken) authenticationToken;
        // 判断用户类型
        String loginType = token.getLoginType();
        // 后台管理系统登陆认证
        if (SystemConstant.LoginType.LOGIN_TYPE_USER.equals(loginType)) {
            realm = (Realm) this.definedRealms.get("shiroRealm");

        // 手机端考试练习系统登陆认证
        } else if (SystemConstant.LoginType.LOGIN_TYPE_CUSTOMER.equals(loginType)) {
            realm = (Realm) this.definedRealms.get("cutomerShiroRealm");
        }
        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }
}
