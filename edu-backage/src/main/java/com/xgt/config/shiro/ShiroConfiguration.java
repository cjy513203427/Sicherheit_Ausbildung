package com.xgt.config.shiro;

import com.xgt.config.shiro.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * Author: CC
 * Date: 2016/8/18
 * Desc:配置
 */
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean(name="customRealmAuthenticator")
    public CustomRealmAuthenticator customRealmAuthenticator(@Qualifier("cutomerShiroRealm") CutomerShiroRealm cutomerShiroRealm,
                                                             @Qualifier("shiroRealm") ShiroRealm shiroRealm) {
        logger.info(".........customRealmAuthenticator init ......");
        CustomRealmAuthenticator customRealmAuthenticator = new CustomRealmAuthenticator();

        Map<String,Realm>  realmMap = new HashMap<>();
        realmMap.put("cutomerShiroRealm",cutomerShiroRealm);
        realmMap.put("shiroRealm",shiroRealm);
        customRealmAuthenticator.setDefinedRealms(realmMap);
        return customRealmAuthenticator ;

    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public CustomShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager,
                                                   @Qualifier("kickoutSessionControlFilter") AccessControlFilter kickoutSessionControlFilter){
        CustomShiroFilterFactoryBean shiroFilterFactoryBean  = new CustomShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/wechat/**", "anon");
//        filterChainDefinitionMap.put("/product/**", "anon");
//        filterChainDefinitionMap.put("/order/**", "anon");
        filterChainDefinitionMap.put("/index/**", "anon");
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/video/**", "anon");
        filterChainDefinitionMap.put("/chapterContent/**", "anon");
        filterChainDefinitionMap.put("/imgtxtClassify/**", "anon");
        filterChainDefinitionMap.put("/imgtext/**", "anon");
        filterChainDefinitionMap.put("/rdCard/**", "anon");
        filterChainDefinitionMap.put("/trainExam/**", "anon");
        filterChainDefinitionMap.put("/restful/**", "anon");



        filterChainDefinitionMap.put("/collection/**", "anon");

        filterChainDefinitionMap.put("/eduQuestion/**", "anon");
        filterChainDefinitionMap.put("/eduQuestionBank/**", "anon");
        filterChainDefinitionMap.put("/eduQuestionAnswer/**", "anon");
        filterChainDefinitionMap.put("/eduQuestionExercise/**", "anon");
        filterChainDefinitionMap.put("/imgtext/**", "anon");
        filterChainDefinitionMap.put("/notice/**", "anon");
        filterChainDefinitionMap.put("/buildLabourer/**", "anon");
        filterChainDefinitionMap.put("/buildSite/**", "anon");
        filterChainDefinitionMap.put("/buildCompany/**", "anon");
        filterChainDefinitionMap.put("/fingerPrint/**", "anon");
        filterChainDefinitionMap.put("/dictionary/**", "anon");
        filterChainDefinitionMap.put("/plan/**", "anon");

        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/*/**", "authc");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 设置踢出过滤器 、限制同一帐号同时在线的个数。
        Map<String, Filter> filters = new HashMap<>();
        filters.put("kickout",kickoutSessionControlFilter);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**   客户登录认证
     * @author liuao
     * @date 2018/4/6 22:21
     */
    @Bean(name="cutomerShiroRealm")
    public CutomerShiroRealm cutomerShiroRealm(@Qualifier("customerCredentialsMatcher")
                                                       CustomerCredentialsMatcher customerCredentialsMatcher){
        logger.info(".........cutomerShiroRealm init ......");
        CutomerShiroRealm cutomerShiroRealm = new CutomerShiroRealm();
        cutomerShiroRealm.setCredentialsMatcher(customerCredentialsMatcher);
        return cutomerShiroRealm;
    }

    /**
     * 身份认证realm;
     * (这个需要自己写，账号密码校验；权限等)
     * @return MyShiroRealm
     */
    @Bean(name="shiroRealm")
    public ShiroRealm myShiroRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher){
        ShiroRealm myShiroRealm = new ShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return HashedCredentialsMatcher
     */
    @Bean(name="hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(@Qualifier("ehCacheManager") EhCacheManager ehCacheManager){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        RetryLimitCredentialsMatcher hashedCredentialsMatcher = new RetryLimitCredentialsMatcher(ehCacheManager);
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    // 客户登陆匹配器
    @Bean(name="customerCredentialsMatcher")
    public CustomerCredentialsMatcher customerCredentialsMatcher(){
        CustomerCredentialsMatcher customerCredentialsMatcher = new CustomerCredentialsMatcher();
        customerCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        customerCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return customerCredentialsMatcher;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(3600000*3);// 3小时（默认值也为30分钟）
        return sessionManager;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return EhCacheManager
     */
    @Bean(name="ehCacheManager")
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean(name="sessionDao")
    public SessionDAO sessionDAO(){
        SessionDAO sessionDAO=new EnterpriseCacheSessionDAO();
        return sessionDAO;
    }

    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("customRealmAuthenticator")CustomRealmAuthenticator customRealmAuthenticator,
                                           @Qualifier("shiroRealm")ShiroRealm myShiroRealm,
                                           @Qualifier("cutomerShiroRealm") CutomerShiroRealm cutomerShiroRealm,
                                           @Qualifier("ehCacheManager") EhCacheManager ehCacheManager,
                                           DefaultWebSessionManager sessionManager
//                                           @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager
    ){
        logger.info(".........securityManager init ......");
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setAuthenticator(customRealmAuthenticator);

        //设置realm.
        List<Realm> realms = new ArrayList<>();
        realms.add(myShiroRealm);
        realms.add(cutomerShiroRealm);
        securityManager.setRealms(realms);

        //注入缓存管理器;
        securityManager.setCacheManager(ehCacheManager);//这个如果执行多次，也是同样的一个对象;
        securityManager.setSessionManager(sessionManager);
//        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    /**
     *  rememberMe cookie
     * @author liuao
     * @date 2018/4/13 14:23
     */
//    @Bean(name="rememberMeCookie")
//    public SimpleCookie simpleCookie (){
//        logger.info("...............init  cookieRememberMeCookie..........");
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setPath("/");
//        simpleCookie.setHttpOnly(true);
//        simpleCookie.setMaxAge(2592000);
//        return simpleCookie;
//    }

    /**
     *  rememberMeManager
     * @author liuao
     * @date 2018/4/13 14:27
     */
//    @Bean(name="cookieRememberMeManager")
//    public CookieRememberMeManager cookieRememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie simpleCookie){
//        logger.info("...............init  cookieRememberMeMannager..........");
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(simpleCookie);
//        return cookieRememberMeManager;
//    }

    /**
     *  设置踢出过滤器
     * @author liuao
     * @date 2018/6/5 20:39
     */
    @Bean(name = "kickoutSessionControlFilter")
    public KickoutSessionControlFilter kickoutSessionControlFilter(
            @Qualifier("sessionManager") DefaultWebSessionManager sessionManager,
            @Qualifier("ehCacheManager") EhCacheManager ehCacheManager  ){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionControlFilter.setCacheManager(ehCacheManager);
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login.html");
        return kickoutSessionControlFilter;
    }
}
