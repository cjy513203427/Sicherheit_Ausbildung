package com.xgt.service.video;

import com.xgt.constant.SystemConstant;
import com.xgt.dao.CollectionDao;
import com.xgt.dao.NoticeDao;
import com.xgt.dao.UserDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.EduVideoLabourRel;
import com.xgt.entity.video.Video;
import com.xgt.service.NoticeService;
import com.xgt.service.UploadService;
import com.xgt.util.FileToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.CUTPIC_FOLDER;
import static com.xgt.constant.SystemConstant.NoticeType.VIDEO_COURSE_NOTICE;
import static com.xgt.constant.SystemConstant.PlayStatus.FINISHED;
import static com.xgt.constant.SystemConstant.VIDEO_CONTENT_FOLDER;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-02 9:33
 **/
@Service
public class ChapterContentService {

    private static final Logger logger = LoggerFactory.getLogger(ChapterContentService.class);

    @Autowired
    TransactionTemplate transactionTemplate ;

    @Autowired
    private ChapterContentDao chapterContentDao ;

    @Autowired
    private VideoMaterialDao videoMaterialDao ;

    @Autowired
    private UserDao userDao ;

    @Autowired
    private UploadService uploadService ;

    @Autowired
    private NoticeService noticeService ;

    @Autowired
    private NoticeDao noticeDao ;

    @Autowired
    private CollectionDao collectionDao ;

    @Value("${aliyunOSS.imageAddress}")
    protected String imageAddress;

    /**
     * @Description 查询视频内容列表
     * @author Joanne
     * @create 2018/6/7 11:02
     */
    public Map<String,Object> queryContentList(ChapterContent chapterContent) {
        List<ChapterContent> chaptersContentList = chapterContentDao.queryContentList(chapterContent);
        Integer countChapter = chapterContentDao.countChapterContent(chapterContent);
        Map<String,Object> conrtentMapInfo = new HashMap<>() ;
        conrtentMapInfo.put("list",chaptersContentList);
        conrtentMapInfo.put("total",countChapter);
        return conrtentMapInfo ;
    }

    /**
     * @Description 添加章节视频
     * @author Joanne
     * @create 2018/6/11 19:46
     */
    public void addChapterContent(ChapterContent chapterContent, MultipartFile file) {
        //根据章节视频节点id获取视频信息
        Video video = videoMaterialDao.queryVideoByEduVideoId(chapterContent.getEduVideoId(),null);

        //根据岗位查询出人员id列表信息
        List<Integer> labourIds = userDao.queryLabourIdsByPostType(video.getPostType());

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {

                //上传文件并获得路径名称 e.g.edu/video/video.mp4
                String filePath = uploadService.uploadToOSS(file,VIDEO_CONTENT_FOLDER);

                if(filePath!=null){
                    //视频信息填充至章节节点内容
                    chapterContent.setVideoPath(filePath);
                    //获取视频长度并填充至章节节点视频
                    chapterContent.setTimeLength(FileToolUtil.videoLength(imageAddress,filePath,CUTPIC_FOLDER));
                    //如果视频集锦无缩略图,则填充当前上传的视频截图
                    if(video.getThumbnailPath()==null){
                        //目标截图文件名（自设）
                        String targetFetchPath = filePath.substring(0,filePath.lastIndexOf("."))+ SystemConstant.PicFormat.JPG ;
                        //视频截图获取截图路径 framefile = CUTPIC_FOLDER + filePath
                        String fetchPath = FileToolUtil.fetchFrame(imageAddress+filePath,CUTPIC_FOLDER + targetFetchPath);
                        if(fetchPath!=null){
                            //上传截图至OSS并删除本地截图
                            uploadService.uploadToOssAndDeleteLocalFile(targetFetchPath,CUTPIC_FOLDER);
                            //设置集锦缩略图
                            video.setThumbnailPath(targetFetchPath);
                            //更新集锦信息
                            videoMaterialDao.updateVideoInfo(video);
                        }
                    }
                }
                //新增视频内容
                chapterContentDao.addChapterContent(chapterContent);
                noticeService.assambleVideoNotice(labourIds,chapterContent.getEduVideoId(),chapterContent.getTitle());
                return true;
            }
        });
    }

    /**
     * @Description 更新章节视频 todo
     * @author Joanne
     * @create 2018/6/7 11:04
     */
    public void updateContent(ChapterContent chapterContent,MultipartFile multipartFile) {
        //获取原路径
        ChapterContent origenContent = chapterContentDao.queryContentByContentId(chapterContent.getId());
        //上传新视频
        String newFilePath = uploadService.uploadToOSS(multipartFile,VIDEO_CONTENT_FOLDER);
        if(newFilePath!=null){
            //删除原视频
            uploadService.deleteOssFile(origenContent.getVideoPath());
            //填充新路径
            chapterContent.setVideoPath(newFilePath);
            //获取视频长度并填充至章节节点视频
            chapterContent.setTimeLength(FileToolUtil.videoLength(imageAddress,newFilePath,CUTPIC_FOLDER));
        }
        chapterContentDao.updateContent(chapterContent);
    }

    /**
     * @Description 标记视频观看时长
     * @author Joanne
     * @create 2018/6/8 15:07
     */
    public void markWatchVideo(EduVideoLabourRel eduVideoLabourRel) {
        //查询是否观看过
        EduVideoLabourRel evlr = chapterContentDao.queryVideoLabourRelByLabourIdAndContentId(eduVideoLabourRel);

        if(evlr == null){
            //查询章节视频内容以获取章节id
            ChapterContent chapterContent = chapterContentDao.queryContentByContentId(eduVideoLabourRel.getChapterContentId());
            eduVideoLabourRel.setVideoId(chapterContent.getEduVideoId());
            //未观看过则添加一条记录
            chapterContentDao.markWatchVideo(eduVideoLabourRel);
        }else {
            if(!evlr.getPlayStatus().equals(FINISHED)){
                eduVideoLabourRel.setVideoId(evlr.getVideoId());
                //更新观看记录
                chapterContentDao.updateWatchMark(eduVideoLabourRel);
            }
        }
    }

    /**
     * @Description 视频播放完毕更新播放记录
     * @author Joanne
     * @create 2018/6/8 21:01
     */
    public void markVideoPlayEndByContentId(EduVideoLabourRel eduVideoLabourRel) {
        chapterContentDao.updateWatchMark(eduVideoLabourRel);
    }

    /**
     * @Description 删除视频内容
     * @author Joanne
     * @create 2018/6/14 18:08
     */
    public void deleteContent(String ids) {
        //暂时不做通知删除
        List<ChapterContent> contentList = chapterContentDao.queryContentListByIds(ids);
        if(chapterContentDao.deleteContent(ids)>0){
            for (ChapterContent chapterContent:contentList){
                if(chapterContent.getVideoPath()!=null){
                    uploadService.deleteOssFile(chapterContent.getVideoPath());
                }
            }
        }
    }

    /**
     * @Description 培训系统PC版 - 查询视频内容及试题概况
     * @author Joanne
     * @create 2018/7/18 17:14
     */
    public List<ChapterContent> queryContentByVideoId(Integer catalogId, String videoName) {
        return chapterContentDao.queryContentByVideoId(catalogId,videoName);
    }
}