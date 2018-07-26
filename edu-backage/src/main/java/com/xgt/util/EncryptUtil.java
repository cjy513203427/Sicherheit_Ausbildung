package com.xgt.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Author: CC
 * Date: 2016/8/19
 * Desc:加密工具
 */
public class EncryptUtil {

    private static final String DEFAULT_SALT = "ypxgt_yyh$";

    private static final String POINT = ".";

    public static final String ALGORITHM_NAME = "md5";

    public static final int HASHITERATIONS = 2;

    public static String md5(String target) {
        return encrpt("md5", target, DEFAULT_SALT, 2);
    }

    public static String md5(String target, String salt, int hashIterations) {
        return encrpt("MD5", target, salt + DEFAULT_SALT, hashIterations);
    }

    private static String encrpt(String algorithmName, String target, String salt, int hashIterations) {
        SimpleHash hash = new SimpleHash(algorithmName, target, salt, hashIterations);
        return hash.toHex();
    }

   /* public static void main(String[] args) {
        System.out.println(EncryptUtil.md5("123456", "leo", 2));
    }*/

    public static String getDefaultSalt() {
        return DEFAULT_SALT;
    }

    /**
     *   文件名加密,（加密前，文件名添加了时间戳后缀）
     *  这里的fileName ,包含后缀名 （例如："aaa.doc"）
     * @author liuao
     * @date 2018/4/11 17:56
     */
    public static String getMd5FileName(String fileName ) {
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf(POINT));
        String fileNamePrefix = fileName.substring(0,fileName.lastIndexOf(POINT));

        String time = DateUtil.getCurrentTimeStr(DateUtil.YYYYMMDDHHMMSSSSS);
        fileNamePrefix = EncryptUtil.md5(fileNamePrefix + time );

        return  fileNamePrefix + fileNameSuffix ;
    }
}
