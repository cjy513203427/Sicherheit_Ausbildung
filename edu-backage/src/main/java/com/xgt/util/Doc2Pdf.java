/**	
 * <br>
 * Copyright 2014 IFlyTek.All rights reserved.<br>
 * <br>			 
 * Package: com.kingt <br>
 * FileName: Doc2Pdf.java <br>
 * <br>
 * @version
 * @author liu ao  
 * @created 2018年5月10日
 * @last Modified 
 * @history
 */
package com.xgt.util;

import com.aspose.words.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Doc2Pdf {
    private static final Logger logger = LoggerFactory.getLogger(Doc2Pdf.class);
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Doc2Pdf.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            logger.error("....getLicense.......exception", e);
        }
        return result;
    }
    
    /**
     *  doc 文件 转 pdf
     * @author liuao
     * @date 2018/6/9 14:16
     */
    public static void doc2pdf(InputStream oldFile, File targetFile ) {

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        FileOutputStream os = null ;
        try {
            long old = System.currentTimeMillis();
            os = new FileOutputStream(targetFile);
            Document doc = new Document(oldFile);                    //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            logger.info("word转pdf共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            logger.error("........word转pdf exception...........", e);
        }finally {
            IOUtils.closeQuietly(oldFile);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     *  html 转 doc 文件
     * @author liuao
     * @date 2018/6/9 14:18
     */
    public static void html2Doc(String htmlText, File targetFile ){

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        FileOutputStream os = null ;
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);

            // html
            builder.insertHtml(htmlText);

            os = new FileOutputStream(targetFile);

            doc.save(os, SaveOptions.createSaveOptions(SaveFormat.DOC));//生成doc文件
            long now = System.currentTimeMillis();
            logger.info("html2Doc共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            logger.error("........html 转 word exception...........", e);
        }finally {
            IOUtils.closeQuietly(os);
        }

    }
}
