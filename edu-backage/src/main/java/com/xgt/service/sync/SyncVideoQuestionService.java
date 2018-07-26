package com.xgt.service.sync;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xgt.constant.SystemConstant;
import com.xgt.dao.EduQuestionAnswerDao;
import com.xgt.dao.EduQuestionDao;
import com.xgt.dao.sync.SyncDao;
import com.xgt.dao.video.ChapterContentDao;
import com.xgt.dao.video.VideoMaterialDao;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.EduQuestionAnswer;
import com.xgt.entity.EduVideoQuestionRel;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.util.FileToolUtil;
import com.xgt.util.HttpClientUtil;
import com.xgt.util.VideoEncodeUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xgt.constant.SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY;
import static com.xgt.constant.SystemConstant.VIDEO_TEMP_PATH;

/**
 *  同步视频题目service
 * @author liuao
 * @date 2018/7/18 10:36
 */
@Service
public class SyncVideoQuestionService extends  AbstractSyncService{
    private static final Logger logger = LoggerFactory.getLogger(SyncVideoQuestionService.class);


    @Autowired
    private ChapterContentDao chapterContentDao;

    @Autowired
    private TransactionTemplate transactionTemplate ;

    @Autowired
    private EduQuestionDao eduQuestionDao ;

    @Autowired
    private EduQuestionAnswerDao eduQuestionAnswerDao;



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
        EduVideoQuestionRel videoQuestionRel = (EduVideoQuestionRel)data;

        // 服务器上的 视频章节id ,本条信息同步成功后 ，返回该id
        Integer syncDataId = videoQuestionRel.getId();

        // 远程查询题目信息和选项
        Pair<EduQuestion, List<EduQuestionAnswer>> questionAndOption = queryQuestionAndOptionByIdFromRemote(
                videoQuestionRel.getQuestionId());

        // 题目信息
        EduQuestion question = questionAndOption.getLeft();

        // 同步服务器视频到本地，并加密
        video2Local(question.getQuestionVideoPath());


        // 远程查询视频章节内容信息 ，比对视频内容名称，获得本地同名的视频
        ChapterContent remoteChapterContent = queryChapterContentById(videoQuestionRel.getChapterContentId());
        ChapterContent localChapterContent = chapterContentDao.queryChapterContentByTitle(remoteChapterContent.getTitle());

        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {

                // 保存题目信息
                Integer localQuestionId = eduQuestionDao.addEduQuestion(question);

                List<EduQuestionAnswer> questionOptionList = questionAndOption.getRight();
                for (EduQuestionAnswer option : questionOptionList) {
                    option.setQuestionId(localQuestionId);
                }

                // 保存题目选项信息
                eduQuestionAnswerDao.batchAddEduQuestionAnswer(questionOptionList);

                // 保存题目和视频关系
                chapterContentDao.saveVideoQuestionRel(localChapterContent.getId(), localQuestionId);

                return syncDataId;
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
                question_queryNextQuestionRelById ;

        // 设置参数
        Map<String,Object> param = new HashMap<>();
        param.put("previousId", previousId);

        // 发送请求
        Map result = HttpClientUtil.doPost(url, param);

        //  返回成功，且返回数据不是空
        String data = MapUtils.getString(result, "data");
        if(MapUtils.getIntValue(result, "code") == 0
                && StringUtils.isNotBlank(data)){

            EduVideoQuestionRel videoQuestionRel = JSONObject.parseObject(data, EduVideoQuestionRel.class) ;

            return  Pair.of(true, videoQuestionRel);
        }else{
            return Pair.of(false, null);
        }
    }


    /**
     *  服务器视频 同步到本地
     * @author liuao
     * @date 2018/7/20 11:07
     */
    private  void video2Local(String videoPath) throws Exception {
        // 获取视频文件流，保存到本地

        String localPath = SAFETY_EDU_LOCAL_PATH + videoPath ;
        File localVideoFile  = FileToolUtil.transUrlFileToLocal(ALIYUNOSS_IMAGEADDRESS + videoPath, localPath);

        if(null == localVideoFile){
            throw new RuntimeException("服务器文件写入到本地异常");
        }

        // 视频加密
        VideoEncodeUtil.encrypt(localPath, SystemConstant.VIDEO_ENCRYPT_DECRYPT_KEY);
        logger.info("....服务器文件写入到本地成功.....");
    }

    /**
     *  远程服务器查询题目信息和题目的选项
     * @author liuao
     * @date 2018/7/20 15:05
     */
    private Pair<EduQuestion, List<EduQuestionAnswer>> queryQuestionAndOptionByIdFromRemote(Integer questionId){
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.
                question_queryQuestionAndOptionById ;
        // 设置参数
        Map<String,Object> queryParam = new HashMap<>();
        queryParam.put("questionId", questionId);
        // 发送请求
        Map queryResult = HttpClientUtil.doPost(url, queryParam);

        // 远程服务器上的 题目信息和题目选项
        String data = MapUtils.getString(queryResult, "data");

        JSONObject questionAndOption =  JSONObject.parseObject(data);

        // 题目json字符串
        String questionjson =  questionAndOption.getString("question");
        // 题目选项字符串
        String questionOptionjson =  questionAndOption.getString("questionOption");

        EduQuestion question = JSONObject.parseObject(questionjson, EduQuestion.class);
        List<EduQuestionAnswer> questionOptionList = JSONArray.parseArray(questionOptionjson, EduQuestionAnswer.class);

        Pair<EduQuestion, List<EduQuestionAnswer>> result = Pair.of(question, questionOptionList);
        return result;
    }

    /**
     *  从远程服务器查询视频集锦
     * @author liuao
     * @date 2018/7/20 16:30
     */
    private ChapterContent queryChapterContentById(Integer chapterContentId ){
        String url = SAFETY_EDU_REMOTE_URL + SystemConstant.RemoteServer.
                video_queryChapterContentById ;
        // 设置参数
        Map<String,Object> queryParam = new HashMap<>();
        queryParam.put("chapterContentId", chapterContentId);
        // 发送请求
        Map queryResult = HttpClientUtil.doPost(url, queryParam);

        // 远程服务器上的 视频集锦信息
        String data = MapUtils.getString(queryResult, "data");

        return JSONObject.parseObject(data, ChapterContent.class);
    }






    public static void main(String[] args) throws Exception {
//        SyncVideoService a = new SyncVideoService();
//        a.video2Local("edu/video/6689AC1A506133C13FDA593135E2D923.mp4");

        String path = "edu/video/6689AC1A506133C13FDA593135E2D923.mp4";
        VideoEncodeUtil.decrypt("D:/" + path, VIDEO_TEMP_PATH + path , VIDEO_ENCRYPT_DECRYPT_KEY.length());



    }
}
