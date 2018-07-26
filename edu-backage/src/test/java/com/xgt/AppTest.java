package com.xgt;


import com.baidu.aip.speech.AipSpeech;
import com.google.zxing.EncodeHintType;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.xgt.config.onebyone.CallBack;
import com.xgt.config.onebyone.OneByOne;
import com.xgt.config.onebyone.RedisOneByOneTemplate;
import com.xgt.dto.ImgQrcodeDto;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.util.*;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.plutext.jaxb.xslfo.FontStyleType;
import shaded.org.apache.commons.lang3.StringEscapeUtils;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import static com.xgt.constant.SystemConstant.TOTAL_SCORE;

/**
 * Unit test for simple App.
 */
public class AppTest{



    private static final String TEMP_MP3_PATH = "/opt/temp.mp3";

    public static void main(String[] args) throws IOException {


//        StringBuilder str = new StringBuilder();
//
//        File file = new File("C:\\Users\\Saltwater_leo\\Desktop\\yyh\\销售合同模板-（含税）-18.5.15 -完成版.htm");
//        readToString();
//        str.append();


        List<String> list = new ArrayList<String>();
        list.add("b");
        list.add("a");
        list.add("c");
        String str = StringUtils.join(list.toArray(), ",");
        System.out.println(str);


        Collections.sort(list);
        String str1 = StringUtils.join(list.toArray(), ",");
        System.out.println(str1);

//        String str1 = String.format("%06d", 11444);

//        System.out.println(str1); // 0001


        // 获取html 文本
        File file = new File("C:\\Users\\Saltwater_leo\\Desktop\\test.html");
        String text = FileToolUtil.inputStream2String(new FileInputStream(file));
//        // 提取出纯文本
//        text = Html2TxtUtil.html2Txt(text);
//        String textStr= StringEscapeUtils.unescapeHtml4(text);
//        System.out.println(text);
//
//        System.out.println(file.getName());
//
//        // 创建语音合成 client
//        AipSpeech client = new AipSpeech(Txt2Mp3Util.APP_ID, Txt2Mp3Util.API_KEY, Txt2Mp3Util.SECRET_KEY);
//        // 总的录音文件
//        File totalFile = FileToolUtil.createNewFile("/opt/totalMp3.mp3");
//        File tempMp3 = null;
//        int alen = text.length();

//        for(int i=1;i<50 ;i++){
//            new Runnable(){
//                public void run() {
//                    new RedisOneByOneTemplate().execute(new OneByOne("Txt2Mp3Util", "string2MP3", "-"),
//                            new CallBack<Boolean>() {
//                                @Override
//                                public Boolean invoke() {
//                                    File tempMp3_ = null;
//
//                                    // 每500个字生成一次语音，然后写入总的录音文件中
//                                    for (int i=0 ;i<alen ;i=i+500){
//                                        int startIdx = i;
//                                        int endIdx = startIdx + 500 ;
//                                        endIdx = endIdx > alen ?  alen :endIdx ;
//                                        String txtStr = textStr.substring(startIdx, endIdx);
//                                        File tempMp3 = Txt2Mp3Util.string2MP3(txtStr, TEMP_MP3_PATH, client);
//                                        Txt2Mp3Util.fileCopyWithFileChannel(tempMp3, totalFile, true);
//                                    }
//
//                                    // 删除每次的零时文件
//                                    if(null != tempMp3_){
//                                        tempMp3_.delete();
//                                    }
//                                    return null;
//                                }
//                            });
//
//                }
//            }.run();
//        }


//        System.out.println(EncryptUtil.md5("123456","18856656521", 2 ));



//        String EncryptUtil.md5("123456", "", EncryptUtil.HASHITERATIONS);

//        File docFile = new File("C:\\Users\\Saltwater_leo\\Desktop\\test.doc");
//
//        Doc2Pdf.html2Doc(text, docFile);


//        List<EduQuestionAnswer> l = new ArrayList<>();
//        EduQuestionAnswer a = new EduQuestionAnswer();
//        a.setOptionCode("A");
//
//        EduQuestionAnswer a1 = new EduQuestionAnswer();
//        a1.setOptionCode("B");
//
//        l.add(a);
//        l.add(a1);
//        System.out.println(getAnswerStr(l).toString());


//        System.out.println(TOTAL_SCORE.divide(BigDecimal.valueOf(3), 0, BigDecimal.ROUND_HALF_UP));

        /*String strx = "D://test.ftl";


        File inputFile = new File(strx);

        String targetFiletaPath = "D:\\tmp\\imgTest.doc";
        File targetFile = FileToolUtil.createNewFile(targetFiletaPath);

        Writer resultFileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
        Reader templateFileReader = new InputStreamReader(new FileInputStream(inputFile),"utf-8");




        Map param = new HashMap();
        param.put("img", "iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");

        FreeMakerParser.process(param, resultFileWriter, templateFileReader);

        File pdfFile = FileToolUtil.createNewFile("D:\\tmp\\imgTest.pdf");

        Doc2Pdf.doc2pdf(new FileInputStream(targetFiletaPath), pdfFile);*/


        // ------------------------------------ 图片下方加文字  start--------------------------------------

        // 生成url 的二维码
        int width = 100, height = 140;
        int margin = 1;//边框值
        String url = "https://www.baidu.com";
        ByteArrayOutputStream out = QRCode.from(url).
                withHint(EncodeHintType.MARGIN, margin).to(ImageType.PNG).withSize(width, height).stream();

        String target = new String(Base64.encodeBase64(out.toByteArray()));


        // 添加文字效果
        int fontSize = 12; // 字体大小
        int fontStyle = 0; // 字体风格

        String texta = "测试二维码";
        String phone = "15556091923";
        String withTextPath = "D:\\tmp\\imgTest.png";
        String targetPath = "D:\\tmp\\aaa.png";

        FileOutputStream outputStream = new FileOutputStream(targetPath);
        outputStream.write(out.toByteArray());
        outputStream.close();

        pressText(texta, phone, withTextPath, targetPath, fontStyle, Color.black, fontSize, width, height);

        // ------------------------------------ 图片下方加文字  end--------------------------------------




        // ----------------------------------- word指定位置加图片 start  ------------------------------------------------


        List<ImgQrcodeDto> imgList = new ArrayList<>();
        ImgQrcodeDto dto = new ImgQrcodeDto();
        dto.setImg1("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto.setImg2("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto.setImg3("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto.setImg4("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto.setImg5("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto.setImg6("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");

        ImgQrcodeDto dto1 = new ImgQrcodeDto();
        dto1.setImg1("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto1.setImg2("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto1.setImg3("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto1.setImg4("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto1.setImg5("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");
        dto1.setImg6("iVBORw0KGgoAAAANSUhEUgAAAGQAAACMCAIAAAAFl5vsAAAC/ElEQVR42u3a7Y7jIAyF4d7/Tc9UqjQahWCMffgIfaP9scumCXmKjSF9/XC4jxcEYIEFFlhggQUBWGCBBRZYYEEAFlhggQUWWBCABRZYYIEFFgRggQUWWGCBBYEY65U7yut4bmF8ymhJ9hAssPbH6ovwEE3syvIeggXW/ljJDNV15S59eQ/BAut4LFU6AwsssIQpJnYyWGB9IZbqU12lw5cud8ACa/B+lrzl5M0/sL4bS/Z2ZNiy+cBXYWCB1f/qJfkuJ3ny1qUDWGBJc5bqhU2MRl5DjE3wYIE1vXSIdS4JMbkEAQushVhd6SOZj2IL6WQWAwus/bGS83oyHz1sWxkssNbNhsl94XFFiXA7DCywtsJKbn7Jq4o5K2qwwNoBK5mhkhXDuOU3WGCdgaWa15PpTP7FgAXWJljyrecJ1mCBdRiW/Hd1qqV18oJb/0wSLLAUG0CqT8UWwKqfZYAF1rZYqt8DyfNj8gcWYIH1PVjyCV61HQYWWMdjqe417nURWGA9CGvb5W7stStYYD0IS/5+JVZny1fUYIG1PxYHWGCBBRZYYHEMx3qXL58K5u8vxgnhi3uu8znh8mc21n+L206Uz1N7YBXWpbHWqzVYlxtfOl0Dqn3Ptf8t71L+s3mm/c3NCMOLSxMr/w2Xz28EuzHo1uSsJpaRJsJYpYI9+taPrFum25F1eaRMajcuWCIaLZHvSZWznFi1PBKOxFoY2nPxAix/zmpOXrEwNHKWp3B5ElYmDO0Z1hmnC8LQeGwDqyTOZwCjnlg/sm6ncKNuNErHZpFVnva8nOUZPnbnMnV8YCmzbLnDQroxfDwLjt7GWnA5G5td6m2sBkGsrmmWy72NntWJEddG1AeuWZ0TeodVbbBkpOzGJpbdpfBNI1jNqScZg0ajH6tJqYmVXqxmkZkZ8+Ew7MJqbkYOxEoGWjjiAnOOv4g5AcvegYmNl6lYq8LQ36XMg6RmQ3lRE2sM1zf2DGOvHCN11vmVeqbOUu2lPAiLteGstSHH+/gFMukop1QUeuQAAAAASUVORK5CYIJ=");


        imgList.add(dto);
        imgList.add(dto1);


//        String strx = "D://tmp//test.ftl";
        InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("imgPrint.ftl");

        String targetFiletaPath = "D:\\tmp\\imgTest.doc";
        File targetFile = FileToolUtil.createNewFile(targetFiletaPath);

        Writer resultFileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
        Reader templateFileReader = new InputStreamReader(is,"utf-8");




        Map param = new HashMap();
        param.put("imgList", imgList);

        FreeMakerParser.process(param, resultFileWriter, templateFileReader);

        // ----------------------------------- word指定位置加图片 end  ------------------------------------------------


//        FileInputStream inputStream = new FileInputStream("D:\\tmp\\imgTest.png");
//        String target = new String(Base64.encodeBase64(FileToolUtil.inputStreamToByte(inputStream)));
//        System.out.println(target);

        // ---------------------------------------- 打印word ---------------------------------------------------
//                printByWord("D:\\\\tmp\\\\imgTest.doc");

    }




    /**
     * 进行数据组装（"A,B,..."）
     *
     * @param list
     * @return
     */
    public static StringBuilder getAnswerStr(List<EduQuestionAnswer> list) {
        StringBuilder trueAnswer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i).getOptionCode();
            if (i < list.size() - 1) {
                trueAnswer.append(code + ",");
            } else {
                trueAnswer.append(code);
            }
        }
        return trueAnswer;
    }

    /**
     *  调用打印机、打印word 文档
     * @author liuao
     * @date 2018/7/6 9:45
     */
    public static void printByWord(String path ){
        ComThread.InitSTA();
        ActiveXComponent word=new ActiveXComponent("Word.Application");
        Dispatch doc=null;
        Dispatch.put(word, "Visible", new Variant(false));
        Dispatch docs=word.getProperty("Documents").toDispatch();
        doc=Dispatch.call(docs, "Open", path).toDispatch();

        try {
            Dispatch.call(doc, "PrintOut");//打印
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("打印失败");
        }finally{
            try {
                if(doc!=null){
                    Dispatch.call(doc, "Close",new Variant(0));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            //释放资源
            ComThread.Release();
        }
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
                                 Color color, int fontSize, int width, int height) {

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
        try {
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
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
