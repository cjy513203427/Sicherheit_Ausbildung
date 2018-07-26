package com.xgt.util;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AliyunPublicParamsUtil {

    static String accessKeyId = "WGIX72GCzaesHTSO";
    public static void main(String[] args) throws IOException {
        /**
         *获取视频列表的公共参数
         */
        Map<String,String> publicParams = new HashMap<>();
        publicParams.put("Version","2017-03-21");
        publicParams.put("SignatureMethod","HMAC-SHA1");
        publicParams.put("Timestamp",generateTimestamp());
        publicParams.put("SignatureVersion","1.0");
        publicParams.put("SignatureNonce",generateRandom());
        publicParams.put("Action","GetVideoList");
        publicParams.put("Format","JSON");

        Map<String,String> privateParams = new HashMap<>();
        privateParams.put("AccessKeyId","WGIX72GCzaesHTSO");

        /**
         * 获取视频播放凭证的公共参数
         */
        Map<String,String> publicParams1 = new HashMap<>();
        publicParams1.put("Version","2017-03-21");
        publicParams1.put("SignatureMethod","HMAC-SHA1");
        publicParams1.put("Timestamp",generateTimestamp());
        publicParams1.put("SignatureVersion","1.0");
        publicParams1.put("SignatureNonce",generateRandom());
        publicParams1.put("Action","GetVideoPlayAuth");
        publicParams1.put("Format","JSON");
        publicParams1.put("VideoId","362be314b7ad4cd29d31badcdd993335");

        Map<String,String> privateParams1 = new HashMap<>();
        privateParams1.put("AccessKeyId","WGIX72GCzaesHTSO");
        List<String> stringList = getAllParams(publicParams1,privateParams1);
        String CanonicalizedQueryString = getCQS(stringList);

        String httpMethod = "GET";
        String StringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(CanonicalizedQueryString);
        byte[] HMAC = hmacSHA1Signature("RPLCvcDXVcRX0iXPGv0gczfsa3hKBv",StringToSign);
        String signature = newStringByBase64(HMAC);
        /*String GetVideoListUrl = "http://vod.cn-shanghai.aliyuncs.com/?Timestamp="+publicParams.get("Timestamp")+"&AccessKeyId="+accessKeyId+"&Action=GetVideoList" +
                "&SignatureVersion=1.0&SignatureNonce="+publicParams.get("SignatureNonce")+"&SignatureMethod=HMAC-SHA1&Format=JSON" +
                "&Version=2017-03-21&Signature="+signature;

        String jsonArray = URLUtil.getUrlForHero(GetVideoListUrl);*/

        String GetVideoPlayAuthUrl = "http://vod.cn-shanghai.aliyuncs.com/?Timestamp="+publicParams1.get("Timestamp")+"&AccessKeyId="+accessKeyId+"&Action=GetVideoPlayAuth&VideoId=362be314b7ad4cd29d31badcdd993335" +
                "&SignatureVersion=1.0&SignatureNonce="+publicParams1.get("SignatureNonce")+"&SignatureMethod=HMAC-SHA1&Format=JSON" +
                "&Version=2017-03-21&Signature="+signature;
        System.out.println(GetVideoPlayAuthUrl);
    }
    /*对所有参数名称和参数值做URL编码*/
    public static List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) throws UnsupportedEncodingException {
        List<String> encodeParams = new ArrayList<String>();
        if (publicParams != null) {
            for (String key : publicParams.keySet()) {
                String value = publicParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        if (privateParams != null) {
            for (String key : privateParams.keySet()) {
                String value = privateParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        return encodeParams;
    }
    /*获取 CanonicalizedQueryString*/
    public static String getCQS(List<String> allParams) {
        ParamsComparator paramsComparator = new ParamsComparator();
        Collections.sort(allParams, paramsComparator);
        String cqString = "";
        for (int i = 0; i < allParams.size(); i++) {
            cqString += allParams.get(i);
            if (i != allParams.size() - 1) {
                cqString += "&";
            }
        }
        return cqString;
    }
    /*字符串参数比较器*/
    public static class ParamsComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    /*特殊字符替换为转义字符*/
    public static String percentEncode(String value) {
        try {
            String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
            String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
            String starReplaced = plusReplaced.replace("*", "%2A");
            String waveReplaced = starReplaced.replace("%7E", "~");
            return waveReplaced;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static byte[] hmacSHA1Signature(String accessKeySecret, String stringToSign) {
        try {
            String key = accessKeySecret + "&";
            try {
                SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(signKey);
                return mac.doFinal(stringToSign.getBytes());
            } catch (Exception e) {
                throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String newStringByBase64(byte[] bytes)
            throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(new BASE64Encoder().encode(bytes));
    }

    /*生成当前UTC时间戳Time*/
    public static String generateTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    public static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }
}
