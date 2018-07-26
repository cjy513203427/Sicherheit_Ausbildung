package com.xgt.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.speech.AipSpeech;
import com.google.zxing.EncodeHintType;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.entity.BuildAttachment;
import com.xgt.entity.BuildSite;
import com.xgt.service.UploadService;
import com.xgt.util.FileToolUtil;
import com.xgt.util.Html2TxtUtil;
import com.xgt.util.ResultUtil;
import com.xgt.util.Txt2Mp3Util;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shaded.org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Map;

import static com.xgt.constant.SystemConstant.TXT_AUDIO_FOLDER;
import static com.xgt.enums.EnumPcsServiceError.ERROR_SERVER;


/**
 * @author Joanne
 * @Description
 * @create 2018-05-31 16:39
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // 临时MP3文件路径
    private static final String TEMP_MP3_PATH = "/opt/edu/audioPath/tempMp3_%s.mp3";
    // 合并后的MP3文件
    private static final String TOTAL_MP3_PATH = "/opt/edu/audioPath/totalMp3_%s.mp3";


    @Autowired
    private ThreadPoolTaskExecutor txt2MP3Executor;

    @Autowired
    private UploadService uploadService ;

    @Autowired
    private ImgtextDao imgtextDao ;

    @Value("${audio.appId}")
    private String AUDIO_APPID ;
    @Value("${audio.apiKey}")
    private String AUDIO_APIKEY ;
    @Value("${audio.secretKey}")
    private String AUDIO_SECRETKEY ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EduQuestionDao eduQuestionDao;


    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> test(String a) throws Exception {







//        // 获取html 文本
//        File file = new File("C:\\Users\\Saltwater_leo\\Desktop\\orderDetail.html");
//        String text = FileToolUtil.inputStream2String(new FileInputStream(file));
//        // 提取出纯文本
//        text = Html2TxtUtil.html2Txt(text);
//        String textStr = StringEscapeUtils.unescapeHtml4(text);
//        System.out.println(text);
//
//
//
//
//        for (int i=0; i<10; i++) {
//            Txt2Mp3Dto txt2Mp3Dto = new Txt2Mp3Dto();
//            txt2Mp3Dto.setText(textStr);
//            txt2Mp3Dto.setImgTextId(i+1);
//
//            txt2Mp3Dto.setApiKey(AUDIO_APIKEY);
//            txt2Mp3Dto.setAppId(AUDIO_APPID);
//            txt2Mp3Dto.setSecretKey(AUDIO_SECRETKEY);
//
//            txt2Mp3Dto.setImgtextDao(imgtextDao);
//            txt2Mp3Dto.setUploadService(uploadService);
//
//            txt2MP3Executor.execute(txt2Mp3Dto);
////            uploadMp3AndUpdateAudioPath(i+1, textStr);
//        }
//
//

//        List a =eduQuestionDao.queryRandomQuestion("1", "1", 10);


        return ResultUtil.createSuccessResult(a);
    }



    /**
     *  上传mp3 文件到oss ,并更新音频路径到图文信息中
     * @author liuao
     * @date 2018/6/6 17:10
     */
    
    public void uploadMp3AndUpdateAudioPath(Integer imgTextId, String text){
        String totalMp3Path  = String.format(TOTAL_MP3_PATH, imgTextId);
        String tempMp3Path  = String.format(TEMP_MP3_PATH, imgTextId);
        // 提取出纯文本
        text = Html2TxtUtil.html2Txt(text);
        String textStr= StringEscapeUtils.unescapeHtml4(text);

        // 创建语音合成 client
        AipSpeech client = new AipSpeech(AUDIO_APPID, AUDIO_APIKEY, AUDIO_SECRETKEY);
        // 总的录音文件
        File totalFile = null;
        try {
            totalFile = FileToolUtil.createNewFile(totalMp3Path);
        } catch (IOException e) {
            logger.error("......createNewFile../opt/totalMp3.mp3..exception...", e);
        }
        int alen = textStr.length();

        File tempMp3 = null;
        // 每500个字生成一次语音，然后写入总的录音文件中
        for (int i=0 ;i<alen ;i=i+500){
            int startIdx = i;
            int endIdx = startIdx + 500 ;
            endIdx = endIdx > alen ?  alen :endIdx ;
            logger.info(textStr);
            logger.info("startIdx:{},endIdx:{},alen:{}",startIdx,endIdx,alen);
            String txtStr = textStr.substring(startIdx, endIdx);
            tempMp3 = Txt2Mp3Util.string2MP3(txtStr, tempMp3Path, client);
            FileToolUtil.fileCopyWithFileChannel(tempMp3, totalFile, true);
        }

        // 删除每次的零时文件
        if(null != tempMp3){
//            tempMp3.delete();
        }

        // 上传到 oss
        String audioPath = uploadService.uploadToOSS(totalFile, TXT_AUDIO_FOLDER);
        if(null == audioPath){
            throw new RuntimeException("...........上传文件失败.......");
        }
        // 更新音频路径到图文信息中
        imgtextDao.updateAudioPathById(imgTextId, audioPath);
        logger.info("...............更新音频文件路径到数据库成功..........");

        // 删除总文件
        if(null != totalFile){
//            totalFile.delete();
        }
    }


    @RequestMapping(value = "/testDownload", produces = "application/json; charset=utf-8")
    @ResponseBody
    public void testDownload(HttpServletResponse response) throws Exception {
        FileInputStream in = new FileInputStream("D:\\doc_20180509221237.doc");

        String fileName = "test.doc";
        //设置文件输出类型
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

        byte [] data = FileToolUtil.inputStreamToByte(in);
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(data.length));

        OutputStream outputStream = response.getOutputStream();

        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }



    @RequestMapping(value = "/addBuildSite")
    @ResponseBody
    public Map<String,Object> addBuildSite(@RequestParam("fileUpload") MultipartFile file, BuildAttachment buildAttachment, BuildSite buildSite)
            throws IOException {
        try {
            String fileName = file.getOriginalFilename();
//            fileName = new String(fileName.getBytes(), "UTF-8");
            System.out.println(fileName);
            return ResultUtil.createSuccessResult(fileName);
        } catch (Exception e) {
            logger.error("——————addBuildSite：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }


    @RequestMapping(value = "/testRedis")
    @ResponseBody
    public Map<String,Object> testRedis(BuildAttachment buildAttachment, BuildSite buildSite) {
        try {
            stringRedisTemplate.opsForValue().set("16666","xxxxxxxxx");
            String  a = stringRedisTemplate.opsForValue().get("16666");
            logger.info("...........:{}",a );
            return ResultUtil.createSuccessResult(a);
        } catch (Exception e) {
            logger.error("——————addBuildSite：", e);
            return ResultUtil.createFailResult(ERROR_SERVER.desc);
        }
    }

    @RequestMapping("/testQRCode")
    @ResponseBody
    public Map  getCloudAutQRCode(HttpServletResponse response ) throws IOException {
        String url = "https://www.baidu.com";


        int width = 150, height = 150;
        int margin = 0;//边框值
        ByteArrayOutputStream out = QRCode.from(url).
                withHint(EncodeHintType.MARGIN, margin).to(ImageType.PNG).withSize(width, height).stream();

        String a = new String(Base64.encodeBase64(out.toByteArray()));
        logger.info(a);
        Map<String, Object> result = ResultUtil.createSuccessResult(a);
        logger.info("..............{}.........", JSONObject.toJSONString(result));

        OutputStream outStream = response.getOutputStream();
        outStream.write(out.toByteArray());
        outStream.flush();
        outStream.close();


        return result;
    }


    @RequestMapping("/playMp4")
    @ResponseBody
    public void playMp4(HttpServletResponse response ) throws IOException {
        FileInputStream inputStream = new FileInputStream("D:\\Video_Decrypt\\11.mp4");
        byte[] data = FileToolUtil.inputStreamToByte(inputStream);
        String diskfilename = "final.mp4";
        //response.setContentType("video/mp4");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"" );
        System.out.println("data.length " + data.length);
        response.setContentLength(data.length);
        response.setHeader("Content-Range", "" + Integer.valueOf(data.length-1));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Etag", "W/\"9767057-1323779115364\"");
        byte[] content = new byte[1024];
        BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        OutputStream os = response.getOutputStream();
        while (is.read(content) != -1) {
            os.write(content);
        }
        is.close();
        os.close();





    }
}
