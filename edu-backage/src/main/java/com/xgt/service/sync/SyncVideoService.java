package com.xgt.service.sync;

import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.sync.SyncDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.entity.video.VideoChapter;
import com.xgt.enums.EnumSyncType;
import com.xgt.util.FileToolUtil;
import com.xgt.util.HttpClientUtil;
import com.xgt.util.VideoEncodeUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.xgt.constant.SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY;
import static com.xgt.constant.SystemConstant.VIDEO_TEMP_PATH;

/**
 *  同步视频service
 * @author liuao
 * @date 2018/7/18 10:36
 */
@Service
public class SyncVideoService extends  AbstractSyncService{
    private static final Logger logger = LoggerFactory.getLogger(SyncVideoService.class);

    @Autowired
    private SyncDao syncDao;

    @Autowired
    private VideoMaterialDao videoMaterialDao;

    @Autowired
    private ChapterContentDao chapterContentDao;

    @Autowired
    private TransactionTemplate transactionTemplate ;



    /**
     * 根据同步开始id，同步数据, 返回结束时的id
     *
     * @param data
     * @author liuao
     * @date 2018/7/18 11:32
     */
    @Override
    protected Integer syncData(Object data) throws Exception {

        // 待同步视频章节内容信息
        ChapterContent chapterContent = (ChapterContent)data;

        // 服务器上的 视频章节id ,本条信息同步成功后 ，返回该id
        Integer chapterContentId = chapterContent.getId();

        //  视频文件地址
        String videoPath = chapterContent.getVideoPath();

        // 同步服务器视频到本地，并加密
        video2Local(videoPath);

        // 发送请求到远程服务器，查询视频集锦
        Video remoteVideo = queryVideoByIdFromRemote(chapterContent.getEduVideoId());


        // 根据视频集锦名称，查询本地视频集锦表是否有该视频集锦
        Video  localVideo = videoMaterialDao.queryVideoByTitle(remoteVideo.getTitle());

        // 保存 视频集锦 和 视频章节内容
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {

                Integer localVideoId = null;
                // 如果视频集锦不存在，则保存该集锦
                if(null == localVideo){
                    localVideoId = videoMaterialDao.addVideoInfo(remoteVideo);
                }else{
                    localVideoId = localVideo.getId();
                }

                // 保存视频章节内容
                chapterContent.setEduVideoId(localVideoId);
                chapterContentDao.addChapterContent(chapterContent);

                logger.info("....视频课程同步成功.视频名称：{}..id:{}..", chapterContent.getTitle(), chapterContentId);

                // 返回同步成功的id
                return chapterContentId;
            }
        });
    }



    /**
     * 根据同步id ，查询是否有需要同步的数据,true 表示有， false 表示没有
     *
     * @param previousId
     * @author liuao
     * @date 2018/7/18 16:21
     */
    @Override
    protected Pair<Boolean, Object> exsitNeedSync(Integer previousId) {
        // 发送请求到远程服务器，查询是否有需要同步的数据
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.
                video_queryNextChapterContentById ;


        // 设置参数
        Map<String,Object> param = new HashMap<>();
        param.put("previousId", previousId);

        // 发送请求
        Map result = HttpClientUtil.doPost(url, param);

        //  返回成功，且返回数据不是空
        String data = MapUtils.getString(result, "data");
        if(MapUtils.getIntValue(result, "code") == 0
                && StringUtils.isNotBlank(data)){

            ChapterContent chapterContent = JSONObject.parseObject(data, ChapterContent.class) ;

            return  Pair.of(true, chapterContent);
        }else{
            return Pair.of(false, null);
        }
    }


    /**
     *  服务器视频 同步到本地 ,并加密
     * @author liuao
     * @date 2018/7/20 11:07
     */
    private  void video2Local(String videoPath) throws Exception {
        // 获取视频文件流，保存到本地
        File localVideoFile  = FileToolUtil.transUrlFileToLocal(ALIYUNOSS_IMAGEADDRESS + videoPath,
                SAFETY_EDU_LOCAL_PATH + videoPath);

        //
        if(null == localVideoFile){
            throw new RuntimeException("服务器文件写入到本地异常");
        }

        // 视频加密
        VideoEncodeUtil.encrypt(SAFETY_EDU_LOCAL_PATH + videoPath,
                SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY);
        logger.info("....服务器文件写入到本地加密成功.....");
    }

    /**
     *  从远程服务器查询视频集锦
     * @author liuao
     * @date 2018/7/20 15:05
     */
    private Video queryVideoByIdFromRemote(Integer videoId){
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.
                video_queryVideoById ;
        // 设置参数
        Map<String,Object> queryVideoParam = new HashMap<>();
        queryVideoParam.put("videoId", videoId);
        // 发送请求
        Map queryVideoResult = HttpClientUtil.doPost(url, queryVideoParam);

        // 远程服务器上的 视频集锦信息
        String videoData = MapUtils.getString(queryVideoResult, "data");
        return JSONObject.parseObject(videoData, Video.class);
    }







    public static void main(String[] args) throws Exception {
//        SyncVideoService a = new SyncVideoService();
//        a.video2Local("edu/video/6689AC1A506133C13FDA593135E2D923.mp4");

        String path = "edu/video/6689AC1A506133C13FDA593135E2D923.mp4";
        VideoEncodeUtil.decrypt("D:/" + path, VIDEO_TEMP_PATH + path , VIDEO_ENCRYPT_DECRYPT_KEY.length());



    }
}
