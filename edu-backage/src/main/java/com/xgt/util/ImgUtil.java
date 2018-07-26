package com.xgt.util;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;


public class ImgUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImgUtil.class);
    public static Integer width = 500;

    public static Integer height = 500;

    public static void main(String[] args) {
        try {
            ImgUtil test = new ImgUtil();
           // test.ImageTset();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    /**
     * 注释勿删，测试有用
     * 生成图片，合成图片
     * @param fileDirectory
     * @param realnames
     * @throws Exception
     */
    public static void ImageTset(String fileDirectory,String[] realnames) throws Exception {

        /*File _file1 = new File("D:\\QRCodePng\\奥特曼.png");
        File _file2 = FontImageUtil.createJpgByFontAndAlign("奥特曼", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\奥特曼.jpg");
        File _file3 = new File("D:\\QRCodePng\\谷阿莫.png");
        File _file4 = FontImageUtil.createJpgByFontAndAlign("谷阿莫", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\谷阿莫.jpg");
        File _file5 = new File("D:\\QRCodePng\\怪兽.png");
        File _file6 = FontImageUtil.createJpgByFontAndAlign("怪兽", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\怪兽.jpg");
        File _file7 = new File("D:\\QRCodePng\\蓝胖.png");
        File _file8 = FontImageUtil.createJpgByFontAndAlign("蓝胖", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\蓝胖.jpg");
        File _file9 = new File("D:\\QRCodePng\\淑女.png");
        File _file10 = FontImageUtil.createJpgByFontAndAlign("淑女", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\淑女.jpg");
        File _file11 = new File("D:\\QRCodePng\\小草.png");
        File _file12 = FontImageUtil.createJpgByFontAndAlign("小草", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:\\QRCodePng\\小草.jpg");
        Image src1 = javax.imageio.ImageIO.read(_file1);
        Image src2 = javax.imageio.ImageIO.read(_file2);
        Image src3 = javax.imageio.ImageIO.read(_file3);
        Image src4 = javax.imageio.ImageIO.read(_file4);
        Image src5 = javax.imageio.ImageIO.read(_file5);
        Image src6 = javax.imageio.ImageIO.read(_file6);
        Image src7 = javax.imageio.ImageIO.read(_file7);
        Image src8 = javax.imageio.ImageIO.read(_file8);
        Image src9 = javax.imageio.ImageIO.read(_file9);
        Image src10 = javax.imageio.ImageIO.read(_file10);
        Image src11 = javax.imageio.ImageIO.read(_file11);
        Image src12 = javax.imageio.ImageIO.read(_file12);
        //获取图片的宽度
        int width = src1.getWidth(null);
        //获取图片的高度
        int height = src1.getHeight(null);*/

        //构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为第一只的宽度，高度为各个图片高度之和
        BufferedImage tag = new BufferedImage(width + width, 4*height, BufferedImage.TYPE_INT_RGB);
        //创建输出流
        FileOutputStream out = new FileOutputStream("D:\\QRCodePng\\合成图.png");
        //绘制合成图像
        Graphics g = tag.createGraphics();

        /**
         * 此方法最多六行两列，6张二维码图和对应的6张文字图
         * 如需扩展，要重新调整尺寸
         * x=0,2,4,6,8...
         * x对4取余为0存在线性关系，y=x/3
         * x对4取余不为0存在线性关系，y=(x-2)/3
         * x=1,3,5,7,9...
         * x对4取余为1存在线性关系,y=(4x+2)/3
         * x对4取余不为1存在线性关系，y=x/3
         */
        for(int i=0;i<realnames.length*2;i++){
            if(i%2==0) {
                File _file = new File(fileDirectory,realnames[i/2]+".png");
                Image src = javax.imageio.ImageIO.read(_file);
                if(i%4==0){
                    g.drawImage(src, 0, i*height/3, width, height, null);
                }else {
                    g.drawImage(src, width, (i-2)*height/3, width, height, null);
                }
            }else{
                File _file = FontImageUtil.createJpgByFontAndAlign(realnames[(i-1)/2], "center", 32, 150, 50, Color.white, Color.black,
                        new Font(null, Font.BOLD, 32), fileDirectory+"\\"+realnames[(i-1)/2]+".jpg");
                Image src = javax.imageio.ImageIO.read(_file);
                if(i%4==1){
                    g.drawImage(src, 0, (i+2)*height/3, width, height/3, null);
                }else {
                    g.drawImage(src, width, i*height/3, width, height/3, null);
                }
            }
        }

        /*g.drawImage(src1, 0, 0, width, height, null);
        g.drawImage(src2, 0, height, width, height/3, null);
        g.drawImage(src3, width, 0, width , height, null);
        g.drawImage(src4, width, height, width, height/3, null);
        g.drawImage(src5, 0, 4*height/3, width, height, null);
        g.drawImage(src6, 0, 7*height/3, width, height/3, null);
        g.drawImage(src7, width, 4*height/3, width, height, null);
        g.drawImage(src8, width, 7*height/3, width , height/3, null);
        g.drawImage(src9, 0, 8*height/3, width, height, null);
        g.drawImage(src10, 0, 11*height/3, width, height/3, null);
        g.drawImage(src11, width, 8*height/3, width, height, null);
        g.drawImage(src12, width, 11*height/3, width, height/3, null);*/
        // 释放此图形的上下文以及它使用的所有系统资源。
        g.dispose();
        //将绘制的图像生成至输出流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        //关闭输出流
        out.close();
        logger.info("合成图出来了");
    }

}
