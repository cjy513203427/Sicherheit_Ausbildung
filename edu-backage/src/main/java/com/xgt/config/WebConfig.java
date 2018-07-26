package com.xgt.config;

import com.xgt.filter.CharacterEncodingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * copyright © 2008-2016 CTIM. All Right Reserved.
 * Created by CC on 2016/9/9.
 * Desc:
 */
@Configuration
public class WebConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);


    @Bean
    public FilterRegistrationBean corsFilter() {
        logger.info("解决跨域的过滤器。。。。。。。。。。。");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new SimpleCORSFilter());
        filterRegistrationBean.setName("corsFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean encodingFilterRegistration() {
        logger.info(".........CharacterEncodingFilter come in  ........");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharacterEncodingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("encodingFilter");
        return registration;
    }

}
