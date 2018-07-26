package com.xgt.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by CC on 2017/5/30.
 */
public class DistanceUtil {
    public static void main(String[] args){
        String start = "广州市番禺区";
        String end = "合肥市包河区";
        String startLonLat = getLonLat(start);
        String endLonLat = getLonLat(end);
        Long dis = getDistance(startLonLat,endLonLat);
        System.out.println(dis);
    }
    public static Long computeDistance(String startPlace,String endPlace){
        String startLonLat = getLonLat(startPlace);
        String endLonLat = getLonLat(endPlace);
        Long dis = getDistance(startLonLat,endLonLat);
        return dis/1000;
    }
    private static String getLonLat(String address){
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=42cb8e4546f6d7fc7e571c03f99230d3&address="+address;
        String queryResult = getResponse(queryUrl);  //高德接品返回的是JSON格式的字符串

        JSONObject jo = new JSONObject().fromObject(queryResult);
        JSONArray ja = jo.getJSONArray("geocodes");
        return new JSONObject().fromObject(ja.getString(0)).get("location").toString();
    }

    private static Long getDistance(String startLonLat, String endLonLat){
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String queryUrl = "http://restapi.amap.com/v3/distance?key=42cb8e4546f6d7fc7e571c03f99230d3&origins="+startLonLat+"&destination="+endLonLat;
        String queryResult = getResponse(queryUrl);
        JSONObject jo = new JSONObject().fromObject(queryResult);
        JSONArray ja = jo.getJSONArray("results");
        result = Long.parseLong(new JSONObject().fromObject(ja.getString(0)).get("distance").toString());
        return result;
//        return queryResult;
    }

    private static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


}
