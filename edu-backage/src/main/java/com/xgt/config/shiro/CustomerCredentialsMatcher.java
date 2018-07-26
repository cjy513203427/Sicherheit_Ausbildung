package com.xgt.config.shiro;

import com.xgt.constant.SystemConstant;
import com.xgt.util.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class CustomerCredentialsMatcher extends HashedCredentialsMatcher {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCredentialsMatcher.class);
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

    //    String superPasswod = DictionaryDao.getDictionaryText(SUPER_PASSWORD1);
    //    superPasswod = StringUtils.isBlank(superPasswod) ? SUPER_VALIDATECODE : superPasswod;

        UsernamePasswordByLoginTypeToken  customerToken = (UsernamePasswordByLoginTypeToken)token;
        String telephone = customerToken.getUsername();
        // 输入的密码
        String tokenPassword =  String.valueOf(customerToken.getPassword());
        // 正确密码
        String correctPasswrod =  (String) info.getCredentials();
        // 输入的密码加密
        String newTokenPassword = EncryptUtil.md5(tokenPassword, telephone, EncryptUtil.HASHITERATIONS);
        // 比较密码
        boolean matchResult = newTokenPassword.equals(correctPasswrod);

//        if (superPasswod.equals(tokenPassword)){
//            matchResult = true ;
//        }


        logger.info("输入密码加密前：{},密码加密后：{}， 正确密码：{},matchResult:{}",
                tokenPassword , newTokenPassword, correctPasswrod, matchResult);
        return matchResult ;
    }
}
