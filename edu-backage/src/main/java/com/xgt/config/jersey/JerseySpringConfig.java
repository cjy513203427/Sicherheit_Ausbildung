package com.xgt.config.jersey;

import com.xgt.config.SimpleCORSFilter;
import com.xgt.filter.CharacterEncodingFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * copyright © 2008-2016 CTIM. All Right Reserved.
 * Created by CC on 2016/9/9.
 * Desc:
 */
@Configuration
public class JerseySpringConfig {

    private static final Logger logger = LoggerFactory.getLogger(JerseySpringConfig.class);


    @Bean
    public ServletRegistrationBean jersetServlet() {
        logger.info("......jersetServlet..start.....");
        ServletRegistrationBean registration = new ServletRegistrationBean(
                // 顶级uri 用来区分SpringMVC
                new ServletContainer(), "/restful/*");
        registration.addInitParameter(
                ServletProperties.JAXRS_APPLICATION_CLASS,
                JerseyConfig.class.getName());
        return registration;
    }

}
