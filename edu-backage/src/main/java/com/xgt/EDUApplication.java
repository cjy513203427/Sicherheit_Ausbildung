package com.xgt;

import com.xgt.controller.UserController;
import com.xgt.service.rdcard.RdCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages={"com.xgt"})//添加该注解，是为了扫描到controller 、service、 dao 层
@EnableAutoConfiguration  //启用自动配置
@ImportResource({"classpath:application-*.xml"})
public class EDUApplication   {
    private static final Logger logger = LoggerFactory.getLogger(EDUApplication.class);

    public static void main(String[] args) {
        try {
            String javaVersion = System.getProperty("java.version");
            String arch = System.getProperty("sun.arch.data.model");
            logger.info("........javaVersion:{},...........jdkBit:{}...........",javaVersion, arch);
        }catch (Exception e){
            logger.error("..",e);
        }
        SpringApplication.run(EDUApplication.class, args);
    }
}