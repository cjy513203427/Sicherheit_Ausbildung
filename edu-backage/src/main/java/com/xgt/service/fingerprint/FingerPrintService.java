package com.xgt.service.fingerprint;

import com.sun.jna.ptr.IntByReference;
import com.xgt.service.rdcard.IRdcardDll;
import com.xgt.util.*;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by 5618 on 2018/6/1.
 */
@Service
public class FingerPrintService {



    private static final Logger logger = LoggerFactory.getLogger(FingerPrintService.class);

    // 指纹服务必要变量
    public static long mhDevice ;
    public static long mhDB;

    //the width of fingerprint image
    private static int fpWidth = 0;
    //the height of fingerprint image
    private static int fpHeight = 0;

    // 指纹图片缓存字节数组
    private static byte[] imgbuf ;

    private static byte[] template = new byte[2048];
    private static int[] templateLen = new int[1];

    private static int nFakeFunOn = 1;

    // 指纹文件路径
    public static String  TEMPPIC_PATH =  "fingerprint%s.bmp";

    private static String  tempPicPath1 =  "fingerprint1.bmp";

    private static String  tempPicPath2 =  "fingerprint2.bmp";

    /**
     *   采集指纹，返回指纹的base64 ，并关闭仪器
     * @author liuao
     * @date 2018/7/11 20:09
     */
    public String  collectionFingerBase64() throws IOException {
        Map openResult = openServer();
        logger.info("......open........"+ openResult);
        String fingerBase64 = null;
        try {
            String fingerPath = collectionFingerPicPath();
            fingerBase64 = Base64Util.getImgBase64(fingerPath);
        }catch (Exception e){
            logger.error(".....collectionFingerBase64...", e);
        }finally {
            freeSensor();
        }

        return fingerBase64;

    }

    /**
     *  获取指纹图片路径, 指纹识别仪器不关闭
     * @author liuao
     * @date 2018/7/12 14:27
     */
    public String collectionFingerPicPath(){
        // 开启指纹识别仪器
        Map openRet = openServer();
        logger.info("...........打开指纹采集器.结果:{}.....", openRet);

        String code = MapUtils.getString(openRet, "code");
        String path = null;

        if(!"0".equals(code)){
            return path;
        }
        long startTime = new Date().getTime();
        while(true) {
            // 获取指纹零时文件地址
            path = getFingerImgPath();

            logger.info("........path...."+ path);
            long currentTime = new Date().getTime();
            // 获取指纹图片，则关闭
            if(StringUtils.isNotBlank(path)
                    || startTime + 5000 >= currentTime) {// 超过5秒，没有获取就结束本次获取
                break;
            }
        }
        return path;
    }




    /**
     *  开启服务
     * @return
     */
    public  Map openServer() {
        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
        {
            return ResultUtil.createFailResult("Init failed!");
        }
        int ret = FingerprintSensorEx.GetDeviceCount();
        if (ret < 0)
        {
            return ResultUtil.createFailResult("No devices connected!");
        }
        if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
        {
            return ResultUtil.createFailResult("Open device fail, ret = " + ret + "!");

        }
        if (0 == (mhDB = FingerprintSensorEx.DBInit()))
        {
            freeSensor();
            return ResultUtil.createFailResult("Init DB fail, ret = " + ret + "!");
        }

        //For ISO/Ansi
        int nFmt = 0;	//Ansi
//		int	nFmt = 1;	//ISO
        FingerprintSensorEx.DBSetParameter(mhDB,  5010, nFmt);
        //For ISO/Ansi End

        //set fakefun off
        //FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        //GetFakeOn
        //size[0] = 4;
        //FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
        //nFakeFunOn = byteArrayToInt(paramValue);

        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
        fpWidth = byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
        fpHeight = byteArrayToInt(paramValue);
        //width = fingerprintSensor.getImageWidth();
        //height = fingerprintSensor.getImageHeight();
        imgbuf = new byte[fpWidth*fpHeight];
        return ResultUtil.createSuccessResult();
    }


    /**
     *  获取指纹的 图片路径
     * @return
     * @throws IOException
     */
    public  String  getFingerImgPath() {
        // 获取指纹信息，生成图片
        templateLen[0] = 2048;
        int ret  = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen);
        if (0 == ret){
            if (nFakeFunOn == 1)
            {
                byte[] paramValue = new byte[4];
                int[] size = new int[1];
                size[0] = 4;
                int nFakeStatus = 0;
                //GetFakeStatus
                ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
                nFakeStatus = byteArrayToInt(paramValue);
                System.out.println("ret = "+ ret +",nFakeStatus=" + nFakeStatus);
                if (0 == ret && (byte)(nFakeStatus & 31) != 31)
                {
                    return null;
                }
            }

            Date date = new Date();
            String path = String.format(TEMPPIC_PATH, date.getTime());
            // 指纹图片写入本地
            path = OnCatpureOK(imgbuf, path);
            return path;
        }
        return null;
    }



    /**
     * 将指纹图片写入本地，返回图片路径
     * @param imgBuf
     */
    private static String OnCatpureOK(byte[] imgBuf, String tempPicPath )
    {
        try {
            writeBitmap(imgBuf, fpWidth, fpHeight, tempPicPath);
            return tempPicPath;
        } catch (IOException e) {
            logger.error("....将指纹图片写入本地 异常....", e);
            return null ;
        }
    }

    /**
     *   获取识别分数
     * @return
     * @throws IOException
     */
    public int getMatchScore(String tempPicPath,  String templatePicPath) throws IOException {

        int matchRet =0;
        try {
            byte[] tempByte = new byte[2048];
            byte[] templateByte = new byte[2048];
            int[] sizeFPTemp = new int[1];
            sizeFPTemp[0] = 2048;

            // 待匹配指纹字节数组
            int ret1 = FingerprintSensorEx.ExtractFromImage(mhDB, tempPicPath, 500, tempByte, sizeFPTemp);
            // 指纹模板字节数组
            int ret2 = FingerprintSensorEx.ExtractFromImage(mhDB, templatePicPath, 500, templateByte, sizeFPTemp);

            if(ret1 == 0 && ret2 ==0) {
                // 获取匹配分数
                matchRet = FingerprintSensorEx.DBMatch(mhDB, tempByte, templateByte);
                if(matchRet > 0){
                    logger.info("Verify succ, score=" + matchRet);
                }
                else{
                    logger.info("Verify fail, ret=" + matchRet);
                }
            }
        }catch (Exception e){
            logger.info("比对指纹分数异常");
        }
        return matchRet;
    }

    /**
     *  图片写入本地
     * @author liuao
     * @date 2018/7/11 20:13
     */
    private static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth+3)/4)*4);
        int bfType = 0x424d; // 位图文件类型（0—1字节）
        int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大小（2—5字节）
        int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
        int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
        int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

        dos.writeShort(bfType); // 输入位图文件类型'BM'
        dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
        dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

        int biSize = 40;// 信息头所需的字节数（14-17字节）
        int biWidth = nWidth;// 位图的宽（18-21字节）
        int biHeight = nHeight;// 位图的高（22-25字节）
        int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
        int biBitcount = 8;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
        int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
        int biSizeImage = w * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
        int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
        int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
        int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
        int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

        dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
        dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
        dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
        dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
        dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
        dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
        dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
        dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
        dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth)
        {
            filter = new byte[w-nWidth];
        }

        for(int i=0;i<nHeight;i++)
        {
            dos.write(imageBuf, (nHeight-1-i)*nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w-nWidth);
        }
        dos.flush();
        dos.close();
        fos.flush();
        fos.close();
    }

    /**
     *  关闭指纹识别仪器
     */
    public  void freeSensor() {
        try {
            FingerprintSensorEx.DBFree(mhDB);
            FingerprintSensorEx.CloseDevice(mhDevice);
            FingerprintSensorEx.Terminate();
        } catch (Exception e) {

        }

    }

    private static byte[] intToByteArray (final int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    private static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    private static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值。
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

}
