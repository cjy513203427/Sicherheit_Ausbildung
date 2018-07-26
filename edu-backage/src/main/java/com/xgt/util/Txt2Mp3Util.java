package com.xgt.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 *  文本转MP3
 * @author liuao
 * @date 2018/6/4 17:53
 */
public class Txt2Mp3Util {

    private static final Logger logger = LoggerFactory.getLogger(Txt2Mp3Util.class);
    //设置APPID/AK/SK
    public static final String APP_ID = "11347050";
    public static final String API_KEY = "z5etXd4sBZ8UclwGlzGZm3G0";
    public static final String SECRET_KEY = "GTtqPL4cS7GUyAo0fdQQ8IoHY8sYApxY";

    public static void main(String[] args) throws IOException {

        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        File file1 = string2MP3("xxxxxxxxx", "/opt/m1.mp3", client);
        File file2 = string2MP3("yyyyyyyyyyyy", "/opt/m2.mp3", client);
        File file3 = string2MP3("zzzzzzzzzz", "/opt/m3.mp3", client);

        File totalFile = FileToolUtil.createNewFile("/opt/total.mp3");
        List<File> mp3Files = new ArrayList<>();
        mp3Files.add(file1);
        mp3Files.add(file2);
        mp3Files.add(file3);
        for (File mp3: mp3Files) {
            FileToolUtil.fileCopyWithFileChannel(mp3, totalFile, true);
        }
        
    }

    /**
     *  文本转MP3 返回MP3 file
     * @author liuao
     * @date 2018/6/4 17:35
     */
    public static File string2MP3(String text , String mp3FilePah,
                                  AipSpeech client){

        logger.info("..........AipSpeech:{}.......", JSONObject.toJSONString(client));
        // 初始化一个AipSpeech
//        AipSpeech client = new AipSpeech(appId, apiKey, secretKey);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        TtsResponse res = client.synthesis(text, "zh", 1, null);
        byte[] data = res.getData();
        if (data != null) {
            try {
                FileToolUtil.createNewFile(mp3FilePah);
                Util.writeBytesToFileSystem(data, mp3FilePah);
                return  new File(mp3FilePah);
            } catch (IOException e) {
                logger.error("...string2MP3..exception...",e);
            }
        }

        return null;
    }


}