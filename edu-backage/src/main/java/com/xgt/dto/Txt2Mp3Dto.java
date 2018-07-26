package com.xgt.dto;

import com.baidu.aip.speech.AipSpeech;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.service.UploadService;
import com.xgt.util.FileToolUtil;
import com.xgt.util.Html2TxtUtil;
import com.xgt.util.Txt2Mp3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shaded.org.apache.commons.lang3.StringEscapeUtils;
import shaded.org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

import static com.xgt.constant.SystemConstant.TXT_AUDIO_FOLDER;

public class Txt2Mp3Dto implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Txt2Mp3Dto.class);

    // 临时MP3文件路径
    private static final String TEMP_MP3_PATH = "/opt/edu/audioPath/tempMp3_%s.mp3";
    // 合并后的MP3文件
    private static final String TOTAL_MP3_PATH = "/opt/edu/audioPath/totalMp3_%s.mp3";

    // 图文主键id
    private Integer imgTextId ;
    // 待转换成MP3的文本
    private String text ;


    // 语音识别client 的初始化参数
    private String appId ;
    private String apiKey ;
    private String secretKey ;

    // 文件上传oss
    private UploadService uploadService ;

    // 图文dao
    private ImgtextDao imgtextDao;



    /**
     *  将文本转换成MP3,
     *  上传MP3文件到oss 服务器
     *  保存路径到图文表中
     * @author liuao
     * @date 2018/6/6 15:36
     */
    @Override
    public void run() {

        String totalMp3Path  = String.format(TOTAL_MP3_PATH, imgTextId);
        String tempMp3Path  = String.format(TEMP_MP3_PATH, imgTextId);
        // 提取出纯文本
        text = Html2TxtUtil.html2Txt(text);
        String textStr= StringEscapeUtils.unescapeHtml4(text);

        // 创建语音合成 client
        AipSpeech client = new AipSpeech(appId, apiKey, secretKey);
        // 总的录音文件
        File totalFile = null;
        try {
            totalFile = FileToolUtil.createNewFile(totalMp3Path);
        } catch (IOException e) {
            logger.error("......createNewFile../opt/totalMp3.mp3..exception...", e);
        }
        int alen = textStr.length();

        File tempMp3 = null;
        // 每500个字生成一次语音，然后写入总的录音文件中
        for (int i=0 ;i<alen ;i=i+500){
            int startIdx = i;
            int endIdx = startIdx + 500 ;
            endIdx = endIdx > alen ?  alen :endIdx ;
            logger.info(textStr);
            logger.info("startIdx:{},endIdx:{},alen:{}",startIdx,endIdx,alen);
            String txtStr = textStr.substring(startIdx, endIdx);
            tempMp3 = Txt2Mp3Util.string2MP3(txtStr, tempMp3Path, client);
            // 追加写入总文件_
            FileToolUtil.fileCopyWithFileChannel(tempMp3, totalFile, true);
        }

        // 删除每次的零时文件
        if(null != tempMp3){
//            tempMp3.delete();
            logger.info("............删除mp3临时文件成功.............");

        }
        // 上传到 oss
        String audioPath = uploadService.uploadToOSS(totalFile, TXT_AUDIO_FOLDER);

        // 上传失败，直接结束
        if(StringUtils.isBlank(audioPath)){
            logger.info("..........mp3文件上传失败...图文id:{}..",imgTextId);
            return ;
        }

        // 更新音频路径到图文信息中
        imgtextDao.updateAudioPathById(imgTextId, audioPath);
        logger.info("............更新音频路径到图文信息中成功.............：{}", audioPath);
        // 删除总文件
        if(null != totalFile) {
//            totalFile.delete();
            logger.info("............删除totalMp3临时文件成功.............：{}", audioPath);
        }else{
            logger.info("............删除totalMp3临时文件失败.............：{}", audioPath);
        }
    }


    public Integer getImgTextId() {
        return imgTextId;
    }

    public void setImgTextId(Integer imgTextId) {
        this.imgTextId = imgTextId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public UploadService getUploadService() {
        return uploadService;
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public ImgtextDao getImgtextDao() {
        return imgtextDao;
    }

    public void setImgtextDao(ImgtextDao imgtextDao) {
        this.imgtextDao = imgtextDao;
    }

    public Txt2Mp3Dto() {

    }


}
