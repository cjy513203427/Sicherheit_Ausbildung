package com.xgt.util;
import com.google.zxing.EncodeHintType;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xgt.dto.ImgQrcodeDto;
import com.xgt.entity.BuildLabourer;
import com.xgt.service.BuildLabourerService;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


public class FontImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(FontImageUtil.class);
    public static void main(String[] args) throws Exception {
        //createImage("谷阿莫", new Font("微软雅黑", Font.PLAIN, 32), new File("d:/a.png"), 150, 50);

        createJpgByFontAndAlign("生成图片", "right", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:/right.jpg");
        createJpgByFontAndAlign("生成图片", "center", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:/center.jpg");
        createJpgByFontAndAlign("生成图片", "left", 32, 150, 50, Color.white, Color.black,
                new Font(null, Font.BOLD, 32), "D:/left.jpg");
    }

    // 根据str,font的样式以及输出文件目录
    public static File createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        ImageIO.write(image, "png", outFile);// 输出png图片
        return outFile;
    }

    /**
     * 根据提供的文字生成jpg图片
     *
     * @param s
     *            String 文字
     * @param align
     *            文字位置（left,right,center）
     * @param y
     *            y坐标
     * @param width
     *              图片宽度
     * @param height
     *              图片高度
     * @param bgcolor
     *            Color 背景色
     * @param fontcolor
     *            Color 字色
     * @param font
     *            Font 字体字形字号
     * @param jpgname
     *            String jpg图片名
     * @return
     */
    public static File createJpgByFontAndAlign(String s, String align, int y, int width, int height,
                                                   Color bgcolor, Color fontcolor, Font font, String jpgname) {
        try { // 宽度 高度
            BufferedImage bimage = new BufferedImage(width,
                    height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.setColor(bgcolor); // 背景色
            g.fillRect(0, 0, width, height); // 画一个矩形
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON); // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
            g.setColor(fontcolor); // 字的颜色
            g.setFont(font); // 字体字形字号

            int size = font.getSize();  //文字大小
            int x = 5;
            if(align.equals("left")){
                x = 5;
            } else if(align.equals("right")){
                x = width - size * s.length() - 5;
            } else if(align.equals("center")){
                x = (width - size * s.length())/2;
            }
            g.drawString(s, x, y); // 在指定坐标除添加文字
            g.dispose();
            FileOutputStream out = new FileOutputStream(jpgname); // 指定输出文件
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(50f, true);
            encoder.encode(bimage, param); // 存盘
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("createJpgByFont Failed!");
            e.printStackTrace();
        }
        return new File(jpgname);
    }

    /**
     * 为图片添加文字
     * @param pressText 文字
     * @param newImage 带文字的图片
     * @param targetImage 需要添加文字的图片
     * @param fontStyle 字体风格
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param width 图片宽度
     * @param height 图片高度
     */
    public static void pressText(String pressText, String code, String newImage, String targetImage, int fontStyle,
                                 Color color, int fontSize, int width, int height) throws IOException {
        logger.info("...........图片添加文字开始......");
        code = "("+ code +")" ;
        // 计算文字开始的位置
        // x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (width-(fontSize*pressText.length()))/2;
        // y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height-(height-width)/2 + fontSize/3;

        // code 的 X 开始位置
        int codeStarx = (width-(fontSize/2*code.length()))/2;
        // code 的 Y 开始位置
        int codeStartY = startY + fontSize ;
        File file = new File(targetImage);
        BufferedImage src = ImageIO.read(file);
        int imageW = src.getWidth(null);
        int imageH = src.getHeight(null);
        BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(src, 0, 0, imageW, imageH, null);
        g.setColor(color);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        g.drawString(code, codeStarx, codeStartY );
        g.dispose();
        FileOutputStream out = new FileOutputStream(newImage);
        ImageIO.write(image, "png", out);
        IOUtils.closeQuietly(out);
        out.close();
        logger.info("...........图片添加文字完成......");
    }

    /**
     * @Description 生成二维码图片的base64 字符串
     * @author Joanne
     * @create 2018/7/6 18:31
     */
    public static String printQrInfo(BuildLabourer buildLabourer,String id, String url)throws Exception{

        String tagetInput = null ;

        if(buildLabourer!=null){
            int width = 100, height = 140;
            int margin = 1;//边框值
            // 添加文字效果
            int fontSize = 12; // 字体大小
            int fontStyle = 0; // 字体风格

            // 生成二维码的字节流
            ByteArrayOutputStream out = QRCode.from(url)
                    .withHint(EncodeHintType.MARGIN, margin)
                    .to(ImageType.PNG)
                    .withSize(width, height)
                    .stream();

            String withTextPath = "D:\\tmp\\withText_"+id+".png";
            String targetPath = "D:\\tmp\\target_"+id+".png";
            File withText = FileToolUtil.createNewFile(withTextPath);
            File withoutText = FileToolUtil.createNewFile(targetPath);

            // 二维码写入文件targetxx.png
            FileOutputStream outputStream = new FileOutputStream(targetPath);
            outputStream.write(out.toByteArray());
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(out);

            // 二维码图片添加文字（姓名和电话号码），并将新的图片写入 withTextpath
            FontImageUtil.pressText(buildLabourer.getRealname(), buildLabourer.getPhone(),
                    withTextPath, targetPath, fontStyle, Color.black, fontSize, width, height);

            // 读取 添加文字后的图片，转换成base64
            FileInputStream iutputStream = new FileInputStream(withTextPath);
            tagetInput = new String(Base64.encodeBase64(FileToolUtil.inputStreamToByte(iutputStream)));
            IOUtils.closeQuietly(iutputStream);

            // 删除二维码临时文件
//            withText.delete();
//            withoutText.delete();

        }
        return tagetInput;
    }

    /**
     * @Description 二维码于Word位置
     * @author Joanne
     * @create 2018/7/6 18:32
     */
    public static void wordPicPosition(List<ImgQrcodeDto> imgList, String targetWordPath)throws Exception{

        InputStream is = FontImageUtil.class.getClassLoader().getResourceAsStream("template/imgPrint.ftl");

        if(null == is){
            logger.info(".............InputStream is null..........");
        }

        File targetFile = FileToolUtil.createNewFile(targetWordPath);

        Writer resultFileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
        Reader templateFileReader = new InputStreamReader(is,"utf-8");

        Map param = new HashMap();
        param.put("imgList", imgList);

        FreeMakerParser.process(param, resultFileWriter, templateFileReader);
    }


}

