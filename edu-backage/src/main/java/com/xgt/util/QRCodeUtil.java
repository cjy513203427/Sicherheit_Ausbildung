package com.xgt.util;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Hashtable;
public class QRCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    /**
     * 创建二维码
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String createQRCode(String url,String fileDirectory,String fileName) throws IOException {
        int width = 500;
        int height = 500;
        String format = "png";
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

            File fileDir = new File(fileDirectory);
            FileToolUtil.judeDirExists(fileDir);
            Path file = new File(fileDirectory,fileName+".png").toPath();//在D盘生成二维码图片
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
            ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
            byte b[] = os.toByteArray();//从流中获取数据数组。
            String str = new BASE64Encoder().encode(b);
            IOUtils.closeQuietly(os);
            return str;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //DeleteFileUtil.delete(fileDirectory);
        }
        return "NULL";
    }

    /**
     * 解析出二维码的url
     * 无用
     * @param file
     * @param fileDirectory
     * @throws NotFoundException
     */
    public static void anlaysisQRCode(File file,String fileDirectory) throws NotFoundException {
        MultiFormatReader formatReader=new MultiFormatReader();
        BufferedImage image=null;
        try {
            image = ImageIO.read(file);
        BinaryBitmap binaryBitmap =new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Hashtable hints=new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        Result result=formatReader.decode(binaryBitmap,hints);
        logger.info("解析结果："+result.toString());
        logger.info("解析格式:"+result.getBarcodeFormat());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            DeleteFileUtil.delete(fileDirectory);
        }
    }
}