package com.xgt.config.shiro;

import com.xgt.util.EncryptUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    // 集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RetryLimitCredentialsMatcher.class);

    public RetryLimitCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordByLoginTypeToken  usernamePasswordToken = (UsernamePasswordByLoginTypeToken)token;
        String username = usernamePasswordToken.getUsername();

        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }

//        if (retryCount.incrementAndGet() > 5) {
//            logger.warn("username: " + username + " tried to login more than 5 times in period");
//            throw new ExcessiveAttemptsException(username);
//        }

        String tokenPassword =  String.valueOf(usernamePasswordToken.getPassword());
        String passwordMd5 =  EncryptUtil.md5(tokenPassword, username, 2);
        String correctPasswrod =  (String) info.getCredentials();
        boolean matches = correctPasswrod.equals(passwordMd5);
     //   超级密码
    //    String superPasswod = SUPER_VALIDATECODE;
//        if (superPasswod.equals(tokenPassword)){
//            matches = true ;
//        }
        if (matches) {
            //clear retry data
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}