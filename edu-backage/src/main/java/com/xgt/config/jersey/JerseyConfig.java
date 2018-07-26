package com.xgt.config.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @时间 2018年2月4日15:30:16
 * @author Mr.Li
 * 
 */
@ApplicationPath("/restful") //// 顶级uri 即基本路径
public class JerseyConfig extends ResourceConfig {
	private static final Logger logger = LoggerFactory.getLogger(JerseyConfig.class);
	public JerseyConfig() {
		logger.info("......JerseyConfig.....start...");
		// 构造函数，在这里注册需要使用的内容，（过滤器，拦截器，API等）
		// 注册类的方式
		// register(Demo.class);
		// register(RequestContextFilter.class);
		// 注册包的方式
		packages("com.xgt.controller.sync");
	}
}