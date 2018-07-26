package com.xgt.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class URLUtil {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String jsonArray = URLUtil.getUrlForHero("http://127.0.0.1:8004/rest/buildLabourer/queryTrainee?buildCompanyId=1&status=1");
        System.out.println(jsonArray);
    }

    public static String getUrlForHero(String strURL) throws IOException {
        URL url = new URL(strURL);
        HttpURLConnection httpConn = (HttpURLConnection)
                url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpConn.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        httpConn.disconnect();

        String result = new String(buffer.substring(0,buffer.length()));
        //JSONArray jsonArray = JSONArray.fromObject(result);
        return result;
    }
}
