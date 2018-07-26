package com.xgt.service.video;

import com.xgt.dao.NoticeDao;
import com.xgt.dao.video.VideoChapterDao;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.VideoChapter;
import com.xgt.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.NoticeType.VIDEO_COURSE_NOTICE;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-02 10:04
 **/
@Service
public class VideoChapterService {

    private static final Logger logger = LoggerFactory.getLogger(VideoChapterService.class);

    @Autowired
    private VideoChapterDao videoChapterDao ;

    @Autowired
    private UploadService uploadService ;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private NoticeDao noticeDao ;

    /**
     * @Description pc- 组装章节列表与数量
     * @author Joanne
     * @create 2018/6/2 14:45
     */
    public Map<String,Object> queryChapterList(VideoChapter videoChapter) {
        //查询章节列表
        List<VideoChapter> chaptersList = videoChapterDao.queryChapterList(videoChapter);
        //查询章节数
        Integer countChapter = videoChapterDao.countChapter(videoChapter);
        Map<String,Object> chapterMapInfo = new HashMap<>() ;
        chapterMapInfo.put("list",chaptersList);
        chapterMapInfo.put("total",countChapter);
        return chapterMapInfo ;
    }

    /**
     * @Description 添加章节
     * @author Joanne
     * @create 2018/6/2 14:45
     */
    public void addChapter(VideoChapter videoChapter) throws Exception {
        videoChapterDao.addChapter(videoChapter);
    }

    /**
     * @Description 更新章节信息
     * @author Joanne
     * @create 2018/6/7 10:59
     */
    public void updateChapter(VideoChapter videoChapter) throws Exception{
        videoChapterDao.updateChapter(videoChapter);
    }

    /**
     * @Description 删除章节及所有视频
     * @author Joanne
     * @create 2018/6/15 10:07
     */
    public void deleteChapter(String ids) {
        List<ChapterContent> contentList = videoChapterDao.queryContentByChapterIds(ids);
        Boolean ifDelete = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                //删除章节所有视频
                videoChapterDao.deleteVideoByChapterId(ids);
                //删除章节
                videoChapterDao.deleteChapter(ids);
                //删除通知(暂时没有想到更好的方法解决多条删除)
//                for (ChapterContent chapterContent:contentList){
//                    noticeDao.deleteNoticeByReferenceIdAndType(chapterContent.getEduVideoId().toString(),VIDEO_COURSE_NOTICE,1);
//                }
                return true;
            }
        });
        if(ifDelete){
            for (ChapterContent chapterContent:contentList){
                if(chapterContent.getVideoPath()!=null){
                    uploadService.deleteOssFile(chapterContent.getVideoPath());
                }
            }
        }
    }
}
