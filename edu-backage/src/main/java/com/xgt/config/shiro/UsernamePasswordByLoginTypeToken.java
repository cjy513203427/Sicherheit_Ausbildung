package com.xgt.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-05-31 17:47
 **/
public class UsernamePasswordByLoginTypeToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -7638434498222500528L;

    /*
     * 登陆类型 1：管理人员、2：客户端人员
     */
    private String loginType;

    private Map<String,Object> extraParam ;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public UsernamePasswordByLoginTypeToken(String username, String password, String loginType,
                                            Map<String,Object> extraParam) {
        super(username, password);
        this.loginType = loginType;
        this.extraParam = extraParam;
    }

    public UsernamePasswordByLoginTypeToken( ) {
        super();
    }

    public UsernamePasswordByLoginTypeToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public UsernamePasswordByLoginTypeToken(String username, String password) {
        super(username, password);
    }

    public Map<String, Object> getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(Map<String, Object> extraParam) {
        this.extraParam = extraParam;
    }
}
