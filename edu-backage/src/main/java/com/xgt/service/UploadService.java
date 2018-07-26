package com.xgt.service;

import com.xgt.constant.SystemConstant;
import com.xgt.util.FileToolUtil;
import com.xgt.util.MD5Util;
import com.xgt.util.OssUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;

import static com.xgt.constant.SystemConstant.DISC;
import static com.xgt.util.FileToolUtil.inputStream2File;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-04 11:42
 **/
@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${aliyunOSS.accessKeyId}")
    protected String accessKeyId;

    @Value("${aliyunOSS.accessKeySecret}")
    protected String accessKeySecret;

    @Value("${aliyunOSS.uploadUrl}")
    protected String endpoint;

    @Value("${aliyunOSS.bucketname}")
    protected String bucketName;

    @Value("${aliyunOSS.imageAddress}")
    public String imageAddress;

    public String uploadToOSS(MultipartFile multipartFile, String upPath) {
        try {
            //加密文件名
            String fileName = encryptFileName(multipartFile);
            if (StringUtils.isNotBlank(fileName)) {
                InputStream inputStream = multipartFile.getInputStream();
                logger.info(".......上传文件开始........");
                String attachmentPath = upPath + fileName;
                logger.info(".......文件名：{}.......", fileName);

                // 上传到图片服务器
                OssUtil oss = new OssUtil(accessKeyId, accessKeySecret, endpoint, bucketName);
                logger.info(".........oss 服务相关信息-accessKeyId:{}, accessKeySecret:{}, endpoint:{},bucketName：{}",
                        new Object[]{accessKeyId, accessKeySecret, endpoint, bucketName});
                oss.putObject(attachmentPath, inputStream);

                logger.info(".......上传文件结束........");

                return upPath + fileName;
            }
        } catch (Exception e) {
            logger.error("上传 附件 到 oss 服务器 异常 ： ", e);
        }
        return null;
    }

    /**
     * @Description 以流的形式上传文件至OSS
     * @author
     * @create 2018/6/7 14:24
     */
    public String uploadIsToOSS(InputStream is, String upPath) {
        // 上传到图片服务器
        OssUtil oss = new OssUtil(accessKeyId, accessKeySecret, endpoint, bucketName);
        logger.info(".........oss 服务相关信息-accessKeyId:{}, accessKeySecret:{}, endpoint:{},bucketName：{}",
                new Object[]{accessKeyId, accessKeySecret, endpoint, bucketName});
        oss.putObject(upPath, is);

        logger.info(".......上传文件结束........");

        return upPath;
    }

    /**
     * @Description 上传本地文件至OSS并删除本地文件
     * @author Joanne
     * @create 2018/6/7 14:20
     */
    public void uploadToOssAndDeleteLocalFile(String filePath, String folderPath) {
        File file = new File(folderPath + filePath);
        FileInputStream fileInputStream = null;
        try {
            logger.info("文件转换为流开始.........");
            fileInputStream = new FileInputStream(file);
            uploadIsToOSS(fileInputStream,filePath);
            file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description
     * 删除本地文件
     * @author Joanne
     * @create 2018/7/16 11:42
     */
    public void deleteLocalFile(String filePath) {
        File file = new File(DISC + filePath);
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除本地文件失败....",e);
        }
    }

    /**
     *  上传file 到 oss ,并返回oss 的文件路径
     * @author liuao
     * @date 2018/6/6 15:25
     */
    public String uploadToOSS(File file, String targetfolder){
        try {

            // api 结果，例如： orderDetail.html
            String fileName = file.getName();

            if (StringUtils.isNotBlank(fileName)) {
                InputStream inputStream = new FileInputStream(file);
                logger.info(".......上传文件开始........");


                //  attachmentPath 例： opt/yyh/orderDetail.html
                String attachmentPath = targetfolder + fileName;
                logger.info(".......文件名：{}.......", fileName);

                // 上传到图片服务器
                OssUtil oss = new OssUtil(accessKeyId, accessKeySecret, endpoint, bucketName);
                logger.info(".........oss 服务相关信息-accessKeyId:{}, accessKeySecret:{}, endpoint:{},bucketName：{}",
                        new Object[]{accessKeyId, accessKeySecret, endpoint, bucketName});
                oss.putObject(attachmentPath, inputStream);
                logger.info(".......上传文件结束........");

                return attachmentPath ;
            }
        } catch (Exception e) {
            logger.error("上传 报价附件 到 oss 服务器 异常 ： ", e);
        }
        return null ;
    }

    /**
     * @Description
     * 上传文件只本地并返回本地路径
     * @author Joanne
     * @create 2018/7/16 9:57
     */
    public String uploadToLocal(MultipartFile multipartFile, String upPath){
        try{
            //加密文件名
            String encryptFileName = encryptFileName(multipartFile);
            if (StringUtils.isNotBlank(encryptFileName)) {
                InputStream inputStream = multipartFile.getInputStream();
                logger.info(".......上传文件开始........");
                String attachmentPath = DISC + upPath + encryptFileName;
                logger.info(".......文件路径：{}.......", attachmentPath);
                inputStream2File(inputStream,attachmentPath);
                IOUtils.closeQuietly(inputStream);

                logger.info(".......上传文件结束........");

                return upPath + encryptFileName;
            }
        }catch (Exception e){
            logger.error("上传文件至本地失败",e);
        }
        return null ;
    }

    /**
     * @Description
     * 删除OSS文件
     * @author Joanne
     * @create 2018/7/16 10:23
     */
    public void deleteOssFile(String oldPdfPath) {
        OssUtil ossUtil = new OssUtil(accessKeyId, accessKeySecret, endpoint, bucketName);
        ossUtil.deleteObject(oldPdfPath);
    }

    /**
     * @Description
     * 加密文件名
     * @author Joanne
     * @create 2018/7/16 10:36
     */
    public String encryptFileName(MultipartFile multipartFile){
        //包括后缀名的文件名
        String fileNameWithSuffix = multipartFile.getOriginalFilename();
        if(StringUtils.isBlank(fileNameWithSuffix)){
            return null ;
        }
        //后缀名
        String suffix = fileNameWithSuffix.substring(fileNameWithSuffix.lastIndexOf("."));
        //去除后缀名 加上当前时间戳 以作名称唯一性
        String fileNameWithoutSuffix = fileNameWithSuffix.substring(0,fileNameWithSuffix.lastIndexOf("."))+ System.currentTimeMillis();
        //加密名称加上后缀名
        String fileName = MD5Util.MD5(fileNameWithoutSuffix)+suffix;
        return fileName;
    }
}
