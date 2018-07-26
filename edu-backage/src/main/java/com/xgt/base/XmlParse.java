package com.xgt.base;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class XmlParse implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlParse.class);
	
	private Resource[] resources;
	
	private static final Map<String,String> sqlMap = new ConcurrentHashMap<String,String>();
	 
	public Resource[] getResources() {
		return resources;
	}
	public void setResources(Resource[] resources) {
		this.resources = resources;
	}

	public XmlParse(Resource[] resources) {
		this.resources = resources;
	}


	public XmlParse() {
	}

	public Map<String,String> initSqlMap() {
		try {
			if (null == resources || resources.length <= 0) {
				logger.error("没有配置sql文件");
				return sqlMap;
			}
			int count = 0;
			for (Resource resource : resources) {
				logger.info("sqlmap 文件url ......{}",resource.getURL());
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(resource.getInputStream());
				Element foo = doc.getRootElement();  
				
				String namespace = foo.getAttributeValue("namespace");
				if (StringUtils.isBlank(namespace)) {
					logger.error("sql的xml文件中，根节点没有配置namespace属性");
					return sqlMap;
				}
				@SuppressWarnings("unchecked")
				List<Element> childrens = foo.getChildren();   
				for (Element ele : childrens) {
					String id = ele.getAttributeValue("id");
					if (StringUtils.isBlank(id)) {
						logger.error("sql的xml文件中，没有配置sql的 id属性");
						return sqlMap;
					}
					String sqlContent = ele.getValue();
					count ++ ;
					sqlMap.put(namespace+"."+id, sqlContent);
				}

			}
			logger.info("sql 加载完成！共{}条", count);
		} catch (Exception e) {
			logger.error("解析sqlmap 异常", e);
		}
		return sqlMap ;
	} 
	
	
	public  static String getSql(String sqlId) {
		return	sqlMap.get(sqlId);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initSqlMap();
	}
}
