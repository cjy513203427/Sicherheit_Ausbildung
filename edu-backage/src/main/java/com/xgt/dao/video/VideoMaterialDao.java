package com.xgt.dao.video;

import com.xgt.base.DaoClient;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.Video;
import com.xgt.entity.video.VideoComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-01 16:07
 **/
@Repository
public class VideoMaterialDao {

    private static final Logger logger = LoggerFactory.getLogger(VideoMaterialDao.class);

    private static final String name_space = "video." ;

    @Autowired
    private DaoClient daoClient ;

    /**
     * @Description 视频数量（视频集锦，即视频总括）
     * @author Joanne
     * @create 2018/6/1 17:18
     */
    public Integer countVideo(Video video) {
        String sqlId = name_space + "countVideo" ;
        Long countVideo = daoClient.queryForObject(sqlId,video,Long.class);
        return countVideo.intValue();
    }

    /**
     * @Description 视频列表
     * @author Joanne
     * @create 2018/6/1 17:18
     */
    public List<Video> queryVideoList(Video video) {
        String sqlId = name_space + "queryVideoList" ;
        return daoClient.queryForList(sqlId,video,Video.class);
    }

    /**
     * @Description 添加视频（视频集锦，类似于视频课程总括）
     * @author Joanne
     * @create 2018/6/2 14:50
     */
    public Integer addVideoInfo(Video video) {
        String sqlId = name_space + "addVideoInfo" ;
        return daoClient.insertAndGetId(sqlId,video);
    }

    /**
     * @Description 修改视频集锦
     * @author Joanne
     * @create 2018/6/2 15:34
     */
    public void updateVideoInfo(Video video) {
        String sqlId = name_space + "updateVideoInfo" ;
        daoClient.excute(sqlId,video);
    }

    /**
     * @Description 根据集锦id查询视频集锦
     * @author Joanne
     * @create 2018/6/4 14:36
     */
    public Video queryVideoByEduVideoId(Integer eduVideoId,Integer labourId) {
        String sqlId = name_space + "queryVideoByEduVideoId" ;
        Map param = new HashMap() ;
        param.put("id",eduVideoId);
        param.put("labourId",labourId);
        return daoClient.queryForObject(sqlId,param,Video.class);
    }

    /**
     * @Description 移动端获取集锦信息
     * @author Joanne
     * @create 2018/6/7 15:18
     */
    public List<Video> videoCollectionInfo(Video video) {
        String sqlId = name_space + "videoCollectionInfo" ;
        return daoClient.queryForList(sqlId,video,Video.class);
    }

    /**
     * @Description 根据集锦id更新集锦热度
     * @author Joanne
     * @create 2018/6/7 17:05
     */
    public void updateCollectionHeatByCollectionId(Integer collectionId) {
        String sqlId = name_space + "updateCollectionHeatByCollectionId" ;
        Map param = new HashMap() ;
        param.put("collectionId",collectionId);
        daoClient.excute(sqlId,param);
    }

    /**
     * @Description 根据集锦id查询评论
     * @author Joanne
     * @create 2018/6/7 19:44
     */
    public List<VideoComment> queryCommentsByCollectionId(VideoComment videoComment) {
        String sqlId = name_space + "queryCommentsByCollectionId" ;
        return daoClient.queryForList(sqlId,videoComment,VideoComment.class);
    }

    /**
     * @Description 用户添加评论
     * @author Joanne
     * @create 2018/6/26 14:28
     */
    public Integer addComments(VideoComment videoComment) {
        String sqlId = name_space + "addComments" ;
        return daoClient.insertAndGetId(sqlId,videoComment);
    }

    /**
     *  根据工人id 、最后一个id 分页查询出该用户收藏的视频
     * @author liuao
     * @date 2018/6/11 9:53
     */
    public List<Video> queryCollectVideoBy(Integer labourId,Integer lastVideoId,
                                                   Integer pageSize, String collectionType
                                                   ){
        String sqlId = name_space + "queryCollectVideoBy" ;
        Map params = new HashMap();
        params.put("labourId",labourId);
//        params.put("lastVideoId",lastVideoId);
//        params.put("pageSize",pageSize);
        params.put("collectionType", collectionType);
        return daoClient.queryForList(sqlId,params,Video.class);
    }
    /**
     *  根据工人id 、最后一个id 分页查询该用户的学习记录
     * @author liuao
     * @date 2018/6/11 14:55
     */
    public List queryStudyReorcdBy(Integer labourId, Integer lastId, Integer pageSize) {

        String sqlId = name_space + "queryStudyReorcdBy" ;
        Map params = new HashMap();
        params.put("labourId",labourId);
//        params.put("lastId",lastId);
//        params.put("pageSize",pageSize);
        return daoClient.queryForList(sqlId,params,Video.class);
    }

    /**
     * @Description 根据评论id查询评论
     * @author Joanne
     * @create 2018/6/11 14:54
     */
    public VideoComment queryCommentsByCommentId(Integer id) {
        String sqlId = name_space + "queryCommentsByCommentId" ;
        Map param = new HashMap();
        param.put("id",id);
        return daoClient.queryForObject(sqlId,param,VideoComment.class);
    }

    /**
     * @Description 根据视频集锦id查询集锦信息
     * @author Joanne
     * @create 2018/6/15 10:58
     */
    public List<Video> queryVideoListByVideoCollectionId(String ids) {
        String sqlId = name_space + "queryVideoListByVideoCollectionId" ;
        Map map = new HashMap();
        map.put("ids",ids);
        return daoClient.queryForList(sqlId,map,Video.class);
    }

    /**
     * @Description 根据集锦id查询视频内容
     * @author Joanne
     * @create 2018/6/15 11:18
     */
    public List<ChapterContent> queryContentByVideoMaterialId(String ids) {
        String sqlId = name_space + "queryContentByVideoMaterialId" ;
        Map param = new HashMap() ;
        param.put("ids",ids);
        return daoClient.queryForList(sqlId,param,ChapterContent.class);
    }

    /**
     * @Description 删除视频内容
     * @author Joanne
     * @create 2018/6/15 11:26
     */
    public void deleteVideoContent(String ids) {
        String sqlId = name_space + "deleteVideoContent" ;
        Map map = new HashMap();
        map.put("ids",ids);
        daoClient.excute(sqlId,map);
    }

    /**
     * @Description 删除章节
     * @author Joanne
     * @create 2018/6/15 11:37
     */
    public void deleteVideoChapter(String ids) {
        String sqlId = name_space + "deleteVideoChapter";
        Map map = new HashMap();
        map.put("ids",ids);
        daoClient.excute(sqlId,map);
    }

    /**
     * @Description 删除视频集锦
     * @author Joanne
     * @create 2018/6/26 14:30
     */
    public void deleteVideoMaterial(String ids) {
        String sql= name_space + "deleteVideoMaterial" ;
        Map map = new HashMap() ;
        map.put("ids",ids);
        daoClient.excute(sql,map);
    }

    /**
     * @Description 培训系统PC版 获取视全部频集锦
     * @author Joanne
     * @create 2018/6/26 15:05
     */
    public List<Video> queryMaterialList() {
        String sql = name_space + "queryAllCourses";
        return daoClient.queryForList(sql,Video.class);
    }

    /**
     * @Description 培训系统PC版 根据方案ID获取集锦
     * @author Joanne
     * @create 2018/7/16 16:32
     */
    public List<Video> queryMaterialByProgramId(Integer programId) {
        String sqlId = name_space + "queryMaterialByProgramId" ;
        Map map = new HashMap() ;
        map.put("programId",programId);
        return daoClient.queryForList(sqlId,map,Video.class);
    }

    /**
     * @Description  培训系统PC版 - 查询视频集锦(即视频目录)
     * @author Joanne
     * @create 2018/7/18 16:51
     */
    public List<Video> queryVideoCatalog() {
        String sqlId = name_space + "queryVideoCatalog" ;
        return daoClient.queryForList(sqlId,null,Video.class);
    }

    /**
     *  根据id ，查询视频集锦，只是单表数据
     * @author liuao
     * @date 2018/7/19 16:17
     */
    public Video queryVideoById(Integer id){
        String sqlId = name_space + "queryVideoById" ;
        Map map = new HashMap() ;
        map.put("id",id);
        return daoClient.queryForObject(sqlId,map,Video.class);
    }

    /**
     *  根据视频集锦 名称，查询出视频集锦
     * @author liuao
     * @date 2018/7/19 20:09
     */
    public Video queryVideoByTitle(String title){
        String sqlId = name_space + "queryVideoByTitle" ;
        Map map = new HashMap() ;
        map.put("title",title);
        return daoClient.queryForObject(sqlId,map,Video.class);
    }
}
