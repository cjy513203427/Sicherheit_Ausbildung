package com.xgt.util;

import com.xgt.service.fingerprint.FingerPrintService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.xgt.service.fingerprint.FingerPrintService.TEMPPIC_PATH;

public class Base64Util {
    private static final Logger logger = LoggerFactory.getLogger(Base64Util.class);
    /**
     *  根据图片路径，获取图片的base64
     * @author liuao
     * @date 2018/7/11 19:26
     */
    public static String getImgBase64(String path) throws IOException {
        //指纹图片转base64
        FileInputStream inputStream =  new FileInputStream(path);
        byte [] imgByte =  FileToolUtil.inputStreamToByte(inputStream);
        String fingerBase64 = new String(Base64.encodeBase64(imgByte));
        logger.info("...........fingerBase64......:{}.....", fingerBase64);

        // 关闭流，删除临时文件
        IOUtils.closeQuietly(inputStream);
        FileToolUtil.delFile(path);

        return fingerBase64;
    }

    /**
     * 将Base64文件转换成File ,返回文件路径
     * @param base64
     * @param filePath
     */
    public static String base64ToFile(String base64, String filePath) {
        File file = null;
        //创建文件目录
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            file= FileToolUtil.createNewFile(filePath);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            logger.error("...base64ToFile exception...", e);
            return null;
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(fos);
        }
        return filePath;
    }

    public static void main(String[] args) throws IOException {
//        String base64Str = Base64Util.getImgBase64("D:/tmp/fingerprint1531312120612.bmp");
//        logger.info(base64Str);
    }
}
