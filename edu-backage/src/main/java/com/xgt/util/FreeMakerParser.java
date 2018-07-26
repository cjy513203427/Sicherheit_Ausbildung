package com.xgt.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreeMakerParser { 
	
	private static  Log logger = LogFactory.getLog(FreeMakerParser.class );
    private static final String DEFAULT_TEMPLATE_KEY = "default_template_key";  
    private static final String DEFAULT_TEMPLATE_EXPRESSION = "default_template_expression";  
    private static final Configuration CONFIGURER = new Configuration();  
    static {  
        CONFIGURER.setClassicCompatible(true);  
    }  
    /**  
     * 配置SQL表达式缓存  
     */  
    private static Map<String, Template> templateCache = new HashMap<String, Template>();  
    /**  
     * 分库表达式缓存  
     */  
    private static Map<String, Template> expressionCache = new HashMap<String, Template>();  
  
    public static String process(String expression, Map<String, Object> root)   {  
        StringReader reader = null;  
        StringWriter out = null;  
        Template template = null;  
        try {  
            if (expressionCache.get(expression) != null) {  
                template = expressionCache.get(expression);  
            }  
            if (template == null) {  
                template = createTemplate(DEFAULT_TEMPLATE_EXPRESSION, new StringReader(expression));  
                expressionCache.put(expression, template);  
            }  
            out = new StringWriter();  
            template.process(root, out);  
            return out.toString();  
        } catch (Exception e) {  
        	logger.error("freemark解析sql 异常", e);
        } finally {  
            if (reader != null) {  
                reader.close();  
            }  
            try {  
                if (out != null) {  
                    out.close();  
                }  
            } catch (IOException e) {  
                return null;  
            }  
        }  
        return null;  
    }  
  
    private static Template createTemplate(String templateKey, Reader reader) throws IOException {
        Template template = new Template(DEFAULT_TEMPLATE_KEY, reader, CONFIGURER);  
        template.setNumberFormat("#");  
        return template;  
    }  
  
    public static String process(Map<String, Object> root, String sql, String sqlId) {  
        StringReader reader = null;  
        StringWriter out = null;  
        Template template = null;  
        try {  
            if (templateCache.get(sqlId) != null) {  
                template = templateCache.get(sqlId);  
            }  
            if (template == null) {  
                reader = new StringReader(sql);  
                template = createTemplate(DEFAULT_TEMPLATE_KEY, reader);  
                templateCache.put(sqlId, template);  
            }  
            out = new StringWriter();  
            template.process(root, out);  
            return out.toString();  
        } catch (Exception e) {  
        	logger.error("freemark解析sql 异常", e);
        } finally {  
            if (reader != null) {  
                reader.close();  
            }  
            try {  
                if (out != null) {  
                    out.close();  
                }  
            } catch (IOException e) {  
                return null;  
            } 
        }
        return null;  
    }


    /**
     *  模板文件解析
     *  @param paramMap 参数
     *  @param resultFileWriter  结果文件写入器
     *  @param templateFileReader 模板文件读取器
     *  @author liu ao
     *  @created 2018年4月3日 下午4:41:47
     */
    public static void process(Map<String, Object> paramMap, Writer resultFileWriter,
                               Reader templateFileReader ) {
        CONFIGURER.setDefaultEncoding("UTF-8");// 设置默认编码方式
        try {

            Template template = createTemplate(DEFAULT_TEMPLATE_EXPRESSION, templateFileReader);
            template.process(paramMap, resultFileWriter);
            logger.info(".............freemark文件解析完成..........");
        } catch (Exception e) {
            logger.error("freemark 解析异常", e);
            e.printStackTrace();
        }
    }
}  