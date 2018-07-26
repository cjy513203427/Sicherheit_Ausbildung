package com.xgt.service.imgtext;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.CollectionDao;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.NoticeDao;
import com.xgt.dao.UserDao;
import com.xgt.dao.imgtext.ImgtextDao;
import com.xgt.dto.Txt2Mp3Dto;
import com.xgt.entity.User;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.service.NoticeService;
import com.xgt.service.UploadService;
import com.xgt.util.FileToolUtil;
import com.xgt.util.OssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.*;
import static com.xgt.constant.SystemConstant.CollectionType.IMGTXT;
import static com.xgt.constant.SystemConstant.NoticeType.PIC_ARTICLE_COURSE_NOTICE;
import static com.xgt.constant.SystemConstant.TypeCode.POST_TYPE;
import static com.xgt.util.Base64Util.getImgBase64;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-05 10:25
 **/
@Service
public class ImgtextService {
    private final static Logger logger = LoggerFactory.getLogger(ImgtextService.class);
    @Autowired
    private ImgtextDao imgTextDao ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private UserDao userDao ;

    @Autowired
    private CollectionDao collectionDao ;

    @Autowired
    private NoticeService noticeService ;

    @Autowired
    private NoticeDao noticeDao ;

    @Autowired
    private ThreadPoolTaskExecutor txt2MP3Executor;

    @Autowired
    private UploadService uploadService ;

    @Value("${audio.appId}")
    private String AUDIO_APPID ;
    @Value("${audio.apiKey}")
    private String AUDIO_APIKEY ;
    @Value("${audio.secretKey}")
    private String AUDIO_SECRETKEY ;

    @Value("${aliyunOSS.imageAddress}")
    protected String imageAddress;


    /**
     * @Description pc -图文内容列表
     * @author Joanne
     * @create 2018/6/9 16:57
     */
    public Map imgtxtInfo(EduImgtext eduImgText) {
        List<EduImgtext> eduImgtextList = imgTextDao.queryImgtxtList(eduImgText);
        for (EduImgtext e :eduImgtextList){
            e.setPostTypeTxt(DictionaryDao.getDictionaryText(POST_TYPE+e.getPostType()));
        }
        //图文总数
        Integer total = imgTextDao.countImgtxt(eduImgText);
        Map imgtxtInfo = new HashMap();
        imgtxtInfo.put("list",eduImgtextList);
        imgtxtInfo.put("total",total);
        return imgtxtInfo ;
    }

    /**
     * @Description 移动端 图文内容列表
     * @author Joanne
     * @create 2018/6/9 16:58
     */
    public List<EduImgtext> imgTextCollectionInfo(EduImgtext eduImgText,Integer companyId) {
        //查询该人员的主管(即根据companyId查询人员)
        List<User> users = userDao.querySupervisorByCompanyId(companyId) ;
        String userIds = "(" ;
        for (User user:users){
            userIds += user.getId()+COMMA;
        }
        if(userIds.contains(COMMA)){
            userIds = userIds.substring(0,userIds.lastIndexOf(COMMA))+")";
        }else {
            userIds = null ;
        }
        eduImgText.setCreateUserIds(userIds);
        return imgTextDao.imgTextCollectionInfo(eduImgText);
    }

    /**
     * @Description 根据图文id标记图文浏览次数
     * @author Joanne
     * @create 2018/6/9 17:50
     */
    public void markLearnTimes(Integer imgTextId) {
        logger.info("标记浏览次数",imgTextId);
        imgTextDao.markLearnTimes(imgTextId);
    }

    /**
     * @Description 添加图文
     * @author Joanne
     * @create 2018/6/11 15:32
     */
    public void addPicArticle(EduImgtext eduImgText) {
        //根据岗位查询出人员id列表信息
        List<Integer> labourIds = userDao.queryLabourIdsByPostType(eduImgText.getPostType());

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //新增图文
                Integer id = imgTextDao.addPicArticle(eduImgText);
                //新增通知
                noticeService.assamblePicArticleNotice(labourIds,id,eduImgText.getTitle());
                //存储音频
                saveAudio(id,eduImgText.getContent());
                return true;
            }
        });
    }

    /**
     * @Description 存储音频
     * @author Joanne
     * @create 2018/6/12 14:34
     */
    public void saveAudio(Integer textId,String content){
        Txt2Mp3Dto txt2Mp3Dto = new Txt2Mp3Dto();
        txt2Mp3Dto.setText(content);
        txt2Mp3Dto.setImgTextId(textId);

        txt2Mp3Dto.setApiKey(AUDIO_APIKEY);
        txt2Mp3Dto.setAppId(AUDIO_APPID);
        txt2Mp3Dto.setSecretKey(AUDIO_SECRETKEY);

        txt2Mp3Dto.setImgtextDao(imgTextDao);
        txt2Mp3Dto.setUploadService(uploadService);

        txt2MP3Executor.execute(txt2Mp3Dto);
    }

    /**
     * @Description 获取图文详情
     * @author Joanne
     * @create 2018/6/12 14:55
     */
    public EduImgtext imgTextDetial(Integer imgTextId,Integer labourId) {
        return imgTextDao.imgTextDetial(imgTextId,labourId);
    }

    /**
     * @Description 添加PDF图文
     * @author Joanne
     * @create 2018/6/12 17:23
     */
    public void addPdfPicArticle(EduImgtext eduImgText, MultipartFile file) {
        //根据岗位查询出人员id列表信息
        List<Integer> labourIds = userDao.queryLabourIdsByPostType(eduImgText.getPostType());
        //存储PDF至OSS并返回路径 e.g. edu/pdf/a.pdf
        String pdfPath = uploadService.uploadToOSS(file,TXT_PDG_FOLDER);
        if(pdfPath!=null){
            //填充PDF路径
            eduImgText.setPdfPath(pdfPath);
            //目标截图文件名（自设）将pdf后缀改为JPG e.g. edu/pdf/a.jpg
            String targetFetchPath = pdfPath.substring(0,pdfPath.lastIndexOf("."))+ SystemConstant.PicFormat.JPG ;
            //创建本地PDF文件
            File pdfFile = FileToolUtil.transUrlFileToLocal(imageAddress+pdfPath,CUTPIC_FOLDER+pdfPath);
            //对pdf第一页进行截图并获取本地路径 e.g. /opt/edu/pdf/a.jpg
            String fetchPdfPath = FileToolUtil.fetchPDF(CUTPIC_FOLDER+pdfPath,CUTPIC_FOLDER+targetFetchPath);
            //删除本地pdf
            pdfFile.delete();
            if(fetchPdfPath!=null){
                //上传截图至OSS并删除本地文件
                uploadService.uploadToOssAndDeleteLocalFile(targetFetchPath,CUTPIC_FOLDER);
                //填充截图路径
                eduImgText.setImagePath(targetFetchPath);
            }
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //新增图文
                Integer id = imgTextDao.addPicArticle(eduImgText);
                //新增通知
                noticeService.assamblePicArticleNotice(labourIds,id,eduImgText.getTitle());
                return true;
            }
        });
    }

    /**
     * @Description 上传图文内容缩略图
     * @author Joanne
     * @create 2018/6/13 21:39
     */
    public void uploadLitimg(Integer picArticleId, MultipartFile file) {
        //上传缩略图至OSS并返回路径 e.g edu/content/a.jpg
        String filePath = uploadService.uploadToOSS(file,LITIMG_FOLDER);
        imgTextDao.uploadLitimg(picArticleId,filePath);
    }

    /**
     * @Description 修改HTML信息
     * @author Joanne
     * @create 2018/6/13 21:54
     */
    public void updatePicArticle(EduImgtext eduImgText) {
        //修改图文信息
        imgTextDao.updatePicArticle(eduImgText);
        //存储音频
        saveAudio(eduImgText.getId(),eduImgText.getContent());
    }

    /**
     * @Description 更新PDF图文信息
     * @author Joanne
     * @create 2018/6/13 22:05
     */
    public void updatePDFPicArticle(EduImgtext eduImgText,MultipartFile file,Integer userId) {
        //查询PDF路径
        EduImgtext ed = imgTextDao.imgTextDetial(eduImgText.getId(),userId);
        String oldPdfPath= ed.getPdfPath();
        if(oldPdfPath!=null){
            //删除OSS文件
            uploadService.deleteOssFile(oldPdfPath);
        }
        //上传新的文件至OSS并获取路径
        String pdfPath = uploadService.uploadToOSS(file,TXT_PDG_FOLDER);
        if(pdfPath!=null){
            //填充PDF路径
            eduImgText.setPdfPath(pdfPath);

        }
        //更新图文信息
        imgTextDao.updatePicArticle(eduImgText);
    }

    /**
     * @Description 删除图文
     * @author Joanne
     * @create 2018/6/15 19:12
     */
    public void delPicArticle(String ids) {
        List<EduImgtext> eduImgtextList = imgTextDao.queryImgtxtListById(ids);
        Boolean isDelete = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    //删除图文
                    imgTextDao.delPicArticle(ids);
                    //删除收藏
                    collectionDao.deleteCollectionByReferenceIdAndType(ids,IMGTXT);
                    //删除通知
                    noticeDao.deleteNoticeByReferenceIdAndType(ids,PIC_ARTICLE_COURSE_NOTICE,null);
                    return true;
                }catch (Exception e){
                    return false ;
                }
            }
        });
        //删除OSS文件
        if(isDelete){
            for (EduImgtext eduImgtext:eduImgtextList){
                if(eduImgtext.getAudioPath()!=null){
                    //删除音频
                    uploadService.deleteOssFile(eduImgtext.getAudioPath());
                }
                if(eduImgtext.getImagePath()!=null){
                    //删除封面
                    uploadService.deleteOssFile(eduImgtext.getImagePath());
                }
                if(eduImgtext.getPdfPath()!=null){
                    //删除PDF
                    uploadService.deleteOssFile(eduImgtext.getPdfPath());
                }
            }
        }
    }

    /**
     * @Description 培训系统PC版 - 新增知识库
     * @author Joanne
     * @create 2018/7/2 14:07
     */
    public void newSafeKnowledgeArticle(EduImgtext eduImgText,MultipartFile article,MultipartFile cover)throws Exception {
        //上传文章
        logger.info("上传文件至本地。。。");
        String articlePath = uploadService.uploadToLocal(article,TXT_PDG_FOLDER);
        //上传封面
        String coverPath = uploadService.uploadToLocal(cover,LITIMG_FOLDER);
        if( articlePath != null){
            eduImgText.setPdfPath(articlePath);
            eduImgText.setImagePath(coverPath != null ? coverPath : DEFAULT_PIC);
            //新增文章
            imgTextDao.addPicArticle(eduImgText);
        }
    }
}
