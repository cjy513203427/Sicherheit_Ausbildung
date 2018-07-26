package com.xgt.util;
import java.io.*;
import java.net.URL;
import java.util.*;


import com.aliyun.oss.OSSException;

import com.aliyun.oss.model.*;
import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;

public class OssUtil {
    private OSSClient client;


    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String bucketName;

    private Logger _logger = Logger.getLogger(OssUtil.class);
    private Map<String, String> suffixMap = new HashMap<String, String>(); // 后缀名map

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OssUtil(String accessKeyId, String accessKeySecret, String endpoint,String bucketName) {
        this.accessKeyId=accessKeyId;
        this.accessKeySecret=accessKeySecret;
        this.endpoint=endpoint;
        this.bucketName=bucketName;
        // 初始化一个OSSClient
        if(null == this.client){
            this.client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }

        suffixMap.put(".jpg", "image/jpg");
        suffixMap.put(".gif", "image/gif");
        suffixMap.put(".png", "image/png");
    }



    /**
     * 从这里去判断是通过普通上传还是分块上传
     *
     * @throws IOException
     */
    public void uploadObject(String key, File file) throws IOException {
        // 大于50M
        if (file.length() / (1024 * 1024) >= 50) {
            this.MultipartUpload(key, file);
        } else {
            this.putObject(key, file);
        }

    }

    /**
     * 简单上传文件
     *
     *            桶名
     * @param key
     *            文件名
     *            文件路径
     * @throws FileNotFoundException
     */
    public void putObject(String key, File file) throws FileNotFoundException {

        InputStream content = new FileInputStream(file);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(file.length());

        String suffix = key.substring(key.lastIndexOf("."));
        meta.setContentType(suffixMap.get(suffix));

        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, content,
                meta);

        // 打印ETag
        _logger.info(file.getName() + "上传成功：" + result.getETag());

        client.shutdown();
    }

    /**
     * 简单上传文件

     * @param key
     *            文件名
     * @param file
     *            字节文件
     * @throws FileNotFoundException
     */
    public void putObject(String key,byte[] file) throws FileNotFoundException {
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, new ByteArrayInputStream(file));

        _logger.info("上传成功：" + result.getETag());
        client.shutdown();
    }


    /**
     * 简单上传文件

     * @param key
     *            文件名
     * @param file
     *            字节文件
     * @throws FileNotFoundException
     */
    public void putObject(String key,InputStream file)  {
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, file);
        _logger.info("上传成功：" + result.getETag());
        client.shutdown();
    }

    /**
     * 创建文件夹
     *
     *            桶名
     * @param objectName
     *            文件夹名，必须以"/"结尾
     * @throws IOException
     */
    public void createFolder(String objectName) throws IOException {
        ObjectMetadata objectMeta = new ObjectMetadata();
        /*
         * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
         */
        byte[] buffer = new byte[0];
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        objectMeta.setContentLength(0);
        try {
            client.putObject(bucketName, objectName, in, objectMeta);
        } finally {
            in.close();
        }
    }

    /**
     * 列出指定桶名里的所有Object
     *
     */
    public void listObjects() {

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = client.listObjects(bucketName);

        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }

    public void listFiles() {
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(
                bucketName);

        // List Objects
        ObjectListing listing = client.listObjects(listObjectsRequest);

        // 遍历所有Object
        System.out.println("Objects:");
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }

        // 遍历所有CommonPrefix
        System.out.println("CommonPrefixs:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println(commonPrefix);
        }
    }

    /**
     * 删除指定文件
     *
     * @param key
     */
    public void deleteObject(String key) {
        client.deleteObject(bucketName, key);
    }

    public File getObject(String key, String newFileName) throws IOException {
        _logger.info("-------------------开始调用getObject方法");
        _logger.info("key:" + key);
        _logger.info("newFileName:" + newFileName);
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);

        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();

        File file = new File(newFileName);
        if (!file.exists()) {

            File dir = new File(newFileName.substring(0,
                    newFileName.lastIndexOf("\\")));

            if (!dir.exists()) {
                dir.mkdirs();
            }

            file.createNewFile();

            OutputStream out = new FileOutputStream(newFileName);
            byte[] bytes = new byte[objectContent.available()];
            while ((objectContent.read(bytes)) != -1) {
                out.write(bytes);
                bytes = new byte[objectContent.available()];
            }

            objectContent.close();
            out.close();
            // 关闭流
            // objectContent.close();
            return file;
        }
        return file;
    }

    public void MultipartUpload(String key, File partFile) throws IOException {

        // 开始Multipart Upload
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                bucketName, key);
        InitiateMultipartUploadResult initiateMultipartUploadResult = client
                .initiateMultipartUpload(initiateMultipartUploadRequest);
        // 打印UploadId
        System.out.println("UploadId: "
                + initiateMultipartUploadResult.getUploadId());
        // 设置每块为 10M
        final int partSize = 1024 * 1024 * 10;
        // 计算分块数目
        int partCount = (int) (partFile.length() / partSize);
        if (partFile.length() % partSize != 0) {
            partCount++;
        }
        // 新建一个List保存每个分块上传后的ETag和PartNumber
        List<PartETag> partETags = new ArrayList<PartETag>();
        for (int i = 0; i < partCount; i++) {
            // 获取文件流
            FileInputStream fis = new FileInputStream(partFile);
            // 跳到每个分块的开头
            long skipBytes = partSize * i;
            fis.skip(skipBytes);
            // 计算每个分块的大小
            long size = partSize < partFile.length() - skipBytes ? partSize
                    : partFile.length() - skipBytes;
            // 创建UploadPartRequest，上传分块
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(initiateMultipartUploadResult
                    .getUploadId());
            uploadPartRequest.setInputStream(fis);
            uploadPartRequest.setPartSize(size);
            uploadPartRequest.setPartNumber(i + 1);
            UploadPartResult uploadPartResult = client
                    .uploadPart(uploadPartRequest);
            // 将返回的PartETag保存到List中。
            partETags.add(uploadPartResult.getPartETag());
            // 关闭文件

            fis.close();
        }
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                bucketName, key, initiateMultipartUploadResult.getUploadId(),
                partETags);

        // 完成分块上传
        CompleteMultipartUploadResult completeMultipartUploadResult = client
                .completeMultipartUpload(completeMultipartUploadRequest);

        // 打印Object的ETag
        System.out.println(completeMultipartUploadResult.getETag());
    }

    /**
     * 获取dir下所有的文件
     *
     * @param dir
     * @return
     */
    public List<OSSObjectSummary> getFileLstInDir(String dir) {
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(
                bucketName);

        // "/" 为文件夹的分隔符
        listObjectsRequest.setDelimiter("/");

        // 列出fun目录下的所有文件和文件夹
        listObjectsRequest.setPrefix(dir + "/");

        ObjectListing listing = client.listObjects(listObjectsRequest);

        return listing.getObjectSummaries();
    }

    /**
     * 判断OSS上是否有key为keyName的Object
     *
     * @param keyName
     * @return
     */
    public boolean isObjectExists(String keyName) {
        // 获取Object，返回结果为OSSObject对象
        try {
            OSSObject object = client.getObject(bucketName, keyName);
            if (object != null) {
                return true;
            }
        } catch (OSSException e) {
            return false;
        }
        return false;
    }

    /**
     * 从OSS上下载CKT文件至本地并打包上传至OSS上
     *
     * @param wjjPath
     * @param localPath
     * @throws Exception
     */
    public void cktZip(String wjjPath, String localPath) throws Exception {
        _logger.info("wjjPath:" + wjjPath);
        _logger.info("localPath:" + localPath);

        // 获取OSS目录下参考图的List
        List<OSSObjectSummary> fileList = getFileLstInDir(wjjPath);
        _logger.info(fileList.size());

        if (fileList == null || fileList.size() == 0) {
            return;
        }

        File cktDir = new File(localPath + wjjPath);
        _logger.info(cktDir.getName());
        if (!cktDir.exists()) {
            cktDir.mkdirs();
        }

        for (OSSObjectSummary ossObject : fileList) {
            String fileName = ossObject.getKey();
            _logger.info("fileName:" + fileName);
            // 将OSS的文件取回本地，为下一步压缩ZIP包作准备
            getObject(fileName, localPath + fileName.replaceAll("/", "\\\\"));
        }
        ZipUtils zipUtils = new ZipUtils();
        _logger.info("need zip's dirName:" + localPath + wjjPath);
        zipUtils.compress(localPath + wjjPath);
        File zipFile = new File(localPath + wjjPath.replaceAll("/", "\\\\")
                + ".zip");
        _logger.info("压缩后的文件名:" + localPath + wjjPath.replaceAll("/", "\\\\")
                + ".zip");
        putObject(wjjPath + ".zip", zipFile);
        if (zipFile.exists()) {
            zipFile.delete();
        }
    }
    /**
     *  获取上传文件的url
     * @author liuao
     * @date 2018/4/11 16:25
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, key,expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    public File getFile(String key, File file){
        // 下载object到文件
        client.getObject(new GetObjectRequest(bucketName, key),file );
        // 关闭client
        client.shutdown();
        return file ;
    }

}
