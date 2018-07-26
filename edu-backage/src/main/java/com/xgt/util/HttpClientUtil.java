package com.xgt.util;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Author:wwei
 * DESC:HttpClient工具类
 */
public class HttpClientUtil {


    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String doGet(String url) {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        String tempTitle = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            // 执行get请求
            HttpResponse httpResponse;
            httpResponse = closeableHttpClient.execute(httpGet);
            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            // 判断响应实体是否为空
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(tempTitle);
            logger.error(e.toString());
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return null;
    }


    public static String doGet(String url, Map<String, Object> params) {
        if(!StringUtils.hasText(url)){
            return "";
        }
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 判断响应实体是否为空
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return null;
    }


    public static String doPost(RequestObject object) {
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        String tempTitle = null;
        try {
            HttpPost httpPost = new HttpPost(object.toUrl());
            // 执行get请求
            HttpResponse httpResponse;
            httpResponse = closeableHttpClient.execute(httpPost);

            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            // 判断响应实体是否为空
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(tempTitle);
            logger.error(e.toString());
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return null;
    }

    public static String doPost(String url,Map<String,Object> map,String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Authorization", "Basic YWRtaW46");
            //设置参数

            String paramStr = JSONUtils.toJSONString(map);
            logger.info("........"+ paramStr);
            StringEntity entity = new StringEntity(paramStr, "utf-8");
            entity.setContentEncoding(new BasicHeader("Content-Type",
                    "application/json"));
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


    /**
     *  restful post 请求
     * @author liuao
     * @date 2018/7/19 11:13
     */
    public static Map doPost(String url, Map param){
        try {
            Client client = Client.create();
            URI u = new URI(url);
            WebResource resource = client.resource(u);
            MultivaluedMap<String, String> params = new MultivaluedMapImpl();

            Iterator i$ = param.entrySet().iterator();
            while(i$.hasNext()) {
                Map.Entry<String, List<String>> e = (Map.Entry)i$.next();
                params.add(e.getKey(),  String.valueOf(e.getValue()) );
            }

            logger.info("查询参数:" + params.toString());
            String result = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, params);

            logger.info("url = :{},返回结果：{}", url, result);
            JSONObject jasonObject = JSONObject.parseObject(result);
            return  (Map)jasonObject;

        } catch (Exception e) {
            logger.error("..........");
        }
        return null;
    }

}
