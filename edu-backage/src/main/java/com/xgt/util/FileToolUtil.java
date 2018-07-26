package com.xgt.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import it.sauronsoftware.jave.Encoder;
import org.apache.commons.io.IOUtils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Iterator;

public class FileToolUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileToolUtil.class);
    /**
     *
     * @author liuao
     * @date 2018/5/10 17:35
     */
    public static File createNewFile(String pathFileName) throws IOException {
        File outFile = new File(pathFileName);
        File parentFile = outFile.getParentFile();
        if (parentFile !=null && !parentFile.exists()){
            boolean ret = parentFile.mkdirs();//不存在则创建父目录
            logger.info("......创建 {}，目录结果：{}", parentFile.getAbsolutePath(), ret);
        }
        if(!outFile.exists()) {
            outFile.createNewFile();
        }
        return outFile;
    }

    /**
     * @author cjy
     * @date 2018/6/5 14:35
     * @param filePath
     * @return
     */
    public static void createNewDirectory(String filePath ){
        File filepath = new File(filePath);
        //判断路径是否存在，如果不存在就创建一个
        File file =new File(filePath);
        //如果文件夹不存在则创建
        if  (!file .exists()  && !file .isDirectory()){
            logger.info(filePath + "不存在，...need..create");
            if(file .mkdir()){
                logger.info(filePath + " ..create..success ");
            }

        } else {
            logger.info("//目录存在");
        }
    }

    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                logger.info("dir exists");
            } else {
                logger.info("the same name file exists, can not create dir");
            }
        } else {
            logger.info("dir not exists, create it ...");
            file.mkdirs();
        }

    }

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param videofile  源视频文件路径
     * @param framefile  截取帧的图片存放路径
     * @throws Exception D:/edu/video/test.mp4  http://oss.yyhvr.cn/edu/video/test.mp4
     */
    public static String fetchFrame(String videofile, String framefile) {
        try {
            long start = System.currentTimeMillis();
            File targetFile = new File(framefile);
            ifFileExist(targetFile);
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
            ff.start();
            int lenght = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < lenght) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i > 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int owidth = img.width();
            int oheight = img.height();
            // 对截取的帧进行等比例缩放
            logger.info("对截取的帧进行等比例缩放.......");
            int width = 800;
            int height = (int) (((double) width / owidth) * oheight);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            logger.info(".........写文件至：{}.......",targetFile);
            ImageIO.write(bi, "jpg", targetFile);
            //ff.flush();
            ff.stop();
            logger.info("视频图片截取信息.......",System.currentTimeMillis() - start);
            return framefile ;
        }catch (Exception e){
            logger.info("视频图片截取失败",e);
        }finally {
//            IOUtils.closeQuietly();
        }
        return null ;
    }

    /**
     * 获取PDF缩略图
     * 生成一本书的缩略图
     * @param inputFile        需要生成缩略图的书籍的完整路径
     * @param outputFile    生成缩略图的放置路径
     */
    public static String fetchPDF(String inputFile, String outputFile) {
        Document document = null;
        FileOutputStream out = null;
        ImageOutputStream outImage = null;
        try {
            float rotation = 0f;
            //缩略图显示倍数，1表示不缩放，0.5表示缩小到50%
            float zoom = 0.8f;
            document = new Document();
            document.setFile(inputFile);
            // maxPages = document.getPageTree().getNumberOfPages();
            logger.info(".........生成缩略图开始.......");
            BufferedImage image = (BufferedImage)document.getPageImage(0, GraphicsRenderingHints.SCREEN,
                    Page.BOUNDARY_CROPBOX, rotation, zoom);

            Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
            logger.info(".........写文件.......");
            ImageWriter writer = (ImageWriter)iter.next();

            File output = new File(outputFile);
            ifFileExist(output);
            out = new FileOutputStream(output);
            outImage = ImageIO.createImageOutputStream(out);

            writer.setOutput(outImage);
            writer.write(new IIOImage(image, null, null));
            return outputFile ;
        } catch(Exception e) {
            logger.info( "to generate thumbnail of a book fail : " + inputFile );
            logger.error("to generate thumbnail of a book fail",e);
        }finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(outImage);
        }
        return null ;
    }

    /**
     * @Description 判断是否存在文件及文件夹
     * @author Joanne
     * @create 2018/5/31 11:45
     */
    public static void ifFileExist(File file){
        if(!file.exists()){
            File parentFolder = new File(file.getParent());
            parentFolder.mkdirs();
        }
    }

    /**
     * @Description 获取视频长度
     * @author Joanne
     * @create 2018/6/4 19:43
     */
    public static Long videoLength(String imageAddress,String filePath,String folder){

        Encoder encoder = new Encoder();
//        FileChannel fc= null;
//        String size = "";
        try {
            InputStream inputStream = FileToolUtil.getInputStreamFromUrl(imageAddress + filePath);
            File source = FileToolUtil.inputStream2File(inputStream,folder+filePath);
            it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(source);
            long ls = m.getDuration();
            logger.info("此视频时长为:",ls/60000+"分"+(ls)/1000+"秒！");
            //视频帧宽高
//            logger.info("此视频高度为:",m.getVideo().getSize().getHeight());
//            logger.info("此视频宽度为:",m.getVideo().getSize().getWidth());
//            logger.info("此视频格式为:",m.getFormat());
//            FileInputStream fis = new FileInputStream(source);
//            fc= fis.getChannel();
//            BigDecimal fileSize = new BigDecimal(fc.size());
//            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
//            logger.info("此视频大小为:",size);
            source.delete();
            return ls/1000 ;
        } catch (Exception e) {
            logger.error(".......",e);
        }finally {
//            IOUtils.closeQuietly();
        }
        return null ;
    }
    /**
     *  流转字符串
     * @author liuao
     * @date 2018/6/4 11:08
     */
    public static String inputStream2String(InputStream in) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("..........InputStream2String............",e);
        }
        return sb.toString();
    }


    /**
     *  从网络url 获取文件流
     * @author liuao
     * @date 2018/4/4 14:00
     */
    public  static InputStream getInputStreamFromUrl(String fileUrl) throws IOException {
        logger.info("....getInputStreamFromUrl.:{}....." , fileUrl);
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        return conn.getInputStream();
    }


    /**
     * 用filechannel进行文件转移
     * @author liuao
     * @param fromFile 源文件
     * @param toFile   目标文件
     * @param append false or true 是否追加在文件后
     */
    public static void fileCopyWithFileChannel(File fromFile, File toFile, boolean append) {
        // 输入文件流
        FileInputStream fileInputStream = null;
        // 输出文件流
        FileOutputStream fileOutputStream = null;
        // 输入流管道
        FileChannel fileChannelInput = null;
        // 输出流管道
        FileChannel fileChannelOutput = null;
        try {
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(toFile,append);
            //得到fileInputStream的文件通道
            fileChannelInput = fileInputStream.getChannel();
            //得到fileOutputStream的文件通道
            fileChannelOutput = fileOutputStream.getChannel();
            //将fileChannelInput通道的数据，写入到fileChannelOutput通道
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
        } catch (IOException e) {
            logger.error("...fileCopyWithFileChannel..exception...",e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(fileChannelInput);
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(fileChannelOutput);
        }
    }

    /**
     *  输入流转file
     * @author liuao
     * @date 2018/6/6 21:04
     */
    public static File inputStream2File(InputStream in, String targetPath) {
        OutputStream os = null ;
        File targetFile = null;

        try {

            targetFile = FileToolUtil.createNewFile(targetPath);
            os = new FileOutputStream(targetFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error(".......",e);
            return null;
        }finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(in);
        }

        return targetFile;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * @Description 网络路径文件转换为本地文件
     * @author Joanne
     * @create 2018/6/12 21:09
     */
    public static File transUrlFileToLocal(String url,String targetPath){
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamFromUrl(url);
            return inputStream2File(inputStream,targetPath);
        } catch (IOException e) {
            logger.error(".......",e);
            return null;
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 输入流转字节流
     * */
    public static final byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int ch;
        /**
         *
         * */
        while ((ch = is.read(buffer)) != -1) {
            bytestream.write(buffer,0,ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }

    /**
     *
     * @author liuao
     * @date 2018/7/11 20:36
     */
    public static final InputStream byte2InputStream(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }


    /**
     *  调用打印机、打印word 文档
     * @author liuao
     * @date 2018/7/6 9:45
     */
    public static void printByWord(String path ){
        ComThread.InitSTA();
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        Dispatch doc=null;
        Dispatch.put(word, "Visible", new Variant(false));
        Dispatch docs = word.getProperty("Documents").toDispatch();
        doc=Dispatch.call(docs, "Open", path).toDispatch();

        Dispatch.call(doc, "PrintOut");//打印
        if(doc!=null){
            Dispatch.call(doc, "Close",new Variant(0));
        }
        //释放资源
        ComThread.Release();
    }

    /**
     *   删除指定文件夹下所有文件
     *   param path 文件夹完整绝对路径
     * @author liuao
     * @date 2018/7/10 9:53
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        if(tempList == null || tempList.length <=0){
            return true;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     *  删除文件夹
     *  param folderPath 文件夹完整绝对路径
     * @author liuao
     * @date 2018/7/10 9:54
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            delFile(folderPath); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  删除具体文件
     * @author liuao
     * @date 2018/7/11 20:04
     */
    public static void delFile(String filePath){
        File file = new File(filePath);
        if(null != file &&  file.exists()){
            file.delete();
        }
    }




}
