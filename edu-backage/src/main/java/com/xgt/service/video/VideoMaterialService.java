package com.xgt.service.video;

import com.xgt.common.PageQueryEntity;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.CollectionDao;
import com.xgt.dao.DictionaryDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.NoticeDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.train.EduTraprogramExamCfg;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.entity.video.VideoComment;
import com.xgt.service.EduQuestionService;
import com.xgt.service.UploadService;
import com.xgt.service.train.ProgramService;
import com.xgt.util.FileToolUtil;
import com.xgt.util.VideoEncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.CollectionType.VIDEO;
import static com.xgt.constant.SystemConstant.NoticeType.VIDEO_COURSE_NOTICE;
import static com.xgt.constant.SystemConstant.TypeCode.POST_TYPE;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-01 16:07
 **/
@Service
public class VideoMaterialService {

    private static final Logger logger = LoggerFactory.getLogger(VideoMaterialService.class);

    //   安全教育 文件下载到本地路径
    @Value("${safety.edu.local.path}")
    public String SAFETY_EDU_LOCAL_PATH;

    @Autowired
    private VideoMaterialDao videoMaterialDao ;

    @Autowired
    private ChapterContentDao chapterContentDao ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private UploadService uploadService ;

    @Autowired
    private CollectionDao collectionDao ;

    @Autowired
    private NoticeDao noticeDao ;

    @Autowired
    private EduQuestionService eduQuestionService ;

    @Autowired
    private EduQuestionDao eduQuestionDao ;

    @Autowired
    private ProgramService programService ;

    public Map<String,Object> videoInfo(Video video) {
        //视频总数
        Integer countVideo = videoMaterialDao.countVideo(video);
        //视频列表
        List<Video> videoList = videoMaterialDao.queryVideoList(video);
        for (Video v:videoList){
            v.setPostTypeTxt(DictionaryDao.getDictionaryText(POST_TYPE + v.getPostType()));
        }
        Map videoInfo = new HashMap();
        videoInfo.put("total",countVideo);
        videoInfo.put("list",videoList);
        return videoInfo ;
    }

    /**
     * @Description 添加视频集锦
     * @author Joanne
     * @create 2018/6/2 15:33
     */
    public void addVideoInfo(Video video) {
        videoMaterialDao.addVideoInfo(video);
    }

    /**
     * @Description 修改视频集锦
     * @author Joanne
     * @create 2018/6/2 15:33
     */
    public void updateVideoInfo(Video video) {
        videoMaterialDao.updateVideoInfo(video) ;
    }


    /**
     * @Description 视频集锦
     * @author Joanne
     * @create 2018/6/7 15:10
     */
    public List<Video> videoCollectionInfo(Video video) {
        List<Video> videoList = videoMaterialDao.videoCollectionInfo(video);
        for (Video v:videoList){
            v.setPostTypeTxt(DictionaryDao.getDictionaryText(POST_TYPE + v.getPostType()));
        }
        return videoList ;
    }

    /**
     * @Description 根据集锦idg查询视频细节
     * @author Joanne
     * @create 2018/6/8 15:37
     */
    public Video queryDetialByCollectionId(Integer collectionId,Integer labourerId) {
        //更新集锦热度（浏览次数）
        videoMaterialDao.updateCollectionHeatByCollectionId(collectionId);
        Video video = videoMaterialDao.queryVideoByEduVideoId(collectionId,labourerId);
        video.setPostTypeTxt(DictionaryDao.getDictionaryText(POST_TYPE+video.getPostType()));
        //章节信息
//        List<VideoChapter> chapterList = videoChapterDao.queryChapterListByCollectionId(collectionId);
//        for (VideoChapter videoChapter:chapterList){
//            //视频信息
//            List<ChapterContent> contentList = chapterContentDao.queryContentListByChapterId(videoChapter.getId(),labourerId);
//            videoChapter.setContentList(contentList);
//        }
//        video.setChapterList(chapterList);
        //根据集锦id查询视频信息
        List<ChapterContent> contentList = chapterContentDao.queryContentListBycollectionId(collectionId,labourerId);
        video.setContentList(contentList);
        return video ;
    }

    /**
     * @Description 根据集锦id获取用户评论
     * @author Joanne
     * @create 2018/6/8 9:14
     */
    public List<VideoComment> queryCommentsByCollectionId(VideoComment videoComment) {
        return videoMaterialDao.queryCommentsByCollectionId(videoComment);
    }

    /**
     * @Description 用户添加评论
     * @author Joanne
     * @create 2018/6/8 9:14
     */
    public Integer addComments(VideoComment videoComment) {
        return videoMaterialDao.addComments(videoComment);
    }

    /**
     * @Description 根据评论id查询评论
     * @author Joanne
     * @create 2018/6/11 14:55
     */
    public VideoComment queryCommentsByCommentId(Integer id) {
        return videoMaterialDao.queryCommentsByCommentId(id);
    }

    /**
     * @Description 删除视频集锦及其附属信息
     * @author Joanne
     * @create 2018/6/15 11:40
     */
    public void deleteVideoCollection(String ids) {
        //根据集锦id查询集锦信息以获取缩略图信息
        List<Video> videoList = videoMaterialDao.queryVideoListByVideoCollectionId(ids);
        //根据集锦id查询视频内容信息以获取视频存储路径
        List<ChapterContent> contentList = videoMaterialDao.queryContentByVideoMaterialId(ids);

        Boolean ifDelete = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //删除视频内容
                videoMaterialDao.deleteVideoContent(ids);
                //删除章节
                videoMaterialDao.deleteVideoChapter(ids);
                //删除集锦
                videoMaterialDao.deleteVideoMaterial(ids);
                //删除通知(全部)
                noticeDao.deleteNoticeByReferenceIdAndType(ids, VIDEO_COURSE_NOTICE,null);
                //删除收藏
                collectionDao.deleteCollectionByReferenceIdAndType(ids,VIDEO);
                return true;
            }
        });
        if(ifDelete){
            for (Video video:videoList){
                //OSS删除视频集锦缩略图
                if(video.getThumbnailPath()!=null){
                    uploadService.deleteOssFile(video.getThumbnailPath());
                }
            }
            for (ChapterContent chapterContent:contentList){
                //OSS删除视频
                if(chapterContent.getVideoPath()!=null){
                    uploadService.deleteOssFile(chapterContent.getVideoPath());
                }
            }
        }
    }

    /**
     * @Description 培训系统PC版 获取课程树
     * @author Joanne
     * @create 2018/6/26 14:21
     */
    public List<Video> queryAllCourses() {
        //获取全部视频集锦（只包含标题、id）
        List<Video> allCourses = videoMaterialDao.queryMaterialList();
        for (Video video:allCourses){
            //获取全部视频（只包含章节id、内容标题、内容id）
            List<ChapterContent> contentList = chapterContentDao.queryAllContentNameAndId(video.getId());
            video.setContentList(contentList);
        }
        return allCourses;
    }

    /**
     * @Description 根据课程id查询课程视频详情
     * 所谓课程ID就是chapterContent的ID，查的表是edu_chapter_contrent
     * @author Joanne
     * @create 2018/7/11 15:47
     */
    public Map<String,Object> queryContentByCourseIds(String ids, PageQueryEntity pageQueryEntity) {
        //查询自动组卷题目配置
        EduTraprogramExamCfg eduTraprogramExamCfg = eduQuestionService.queryEduTraprogramExamCfg(ids);
        //查询ids查询全部题目以供手动选择
        List<ChapterContent> contents = chapterContentDao.queryContentByCourseIds(ids) ;
        Map courseInfo = programService.queryQuestionInfo(ids,pageQueryEntity,null);
        Map<String,Object> map = new HashMap<>();
        map.put("eduTraprogramExamCfg",eduTraprogramExamCfg);
        map.put("contents",contents);
        map.put("courseInfo",courseInfo);
        return map ;
    }

    /**
     * 加密视频
     * @param fileName
     */
    /*public void encryptVideo(String fileName){
        try {
            File file = new File(fileUrl);
            FileToolUtil.judeDirExists(file);
            *
             * 加密不可进行多次
             * 判断是否有加密，加密过无需在加密

            if(!VideoEncodeUtil.readFileLastByte(fileUrl+"\\"+fileName,key.length()).equals(key)) {
                VideoEncodeUtil.encrypt(fileUrl + "\\" + fileName, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 解密视频
     * @param fileName
     */
    public void decryptVideo(String fileName){
        try {
            File file = new File(SystemConstant.VIDEO_TEMP_PATH);
            FileToolUtil.judeDirExists(file);
            VideoEncodeUtil.decrypt(SAFETY_EDU_LOCAL_PATH +  fileName,
                    SystemConstant.VIDEO_TEMP_PATH +  fileName, SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description
     * 培训系统PC端 - 根据方案ID查询课程树
     * @author Joanne
     * @create 2018/7/16 15:47
     */
    public Map queryCoursesWithVideo(Integer programId) {
        //获取视频集锦
        List<Video> courses = videoMaterialDao.queryMaterialByProgramId(programId);
        //视频总时长
        Long totalVideoLong = 0l;
        for (Video video:courses){
            //获取集锦下符合条件视频
            List<ChapterContent> contentList = chapterContentDao.queryContentByProgramId(video.getId(),programId);
            for (ChapterContent c:contentList){
                totalVideoLong += c.getTimeLength();
            }
            video.setContentList(contentList);
        }
        Map map = new HashMap() ;
        map.put("videoInfo",courses);
        map.put("totalVideoLong",totalVideoLong);
        return map;
    }


    /**
     * @Description 培训系统PC版 - 查询视频集锦(即视频目录)
     * @author Joanne
     * @create 2018/7/18 16:49
     */
    public List<Video> queryCatelog() {
        return videoMaterialDao.queryVideoCatalog();
    }
}
