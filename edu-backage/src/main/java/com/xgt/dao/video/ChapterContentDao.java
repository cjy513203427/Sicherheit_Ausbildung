package com.xgt.dao.video;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduVideoQuestionRel;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.EduVideoLabourRel;
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
 * @create 2018-06-02 9:33
 **/
@Repository
public class ChapterContentDao {

    private final static Logger logger = LoggerFactory.getLogger(ChapterContentDao.class);

    private final static String NAME_SPACE = "chapterContent." ;

    @Autowired
    private DaoClient daoClient ;

    /**
     * @Description 查询视频内容列表（章节下视频小节）
     * @author Joanne
     * @create 2018/6/2 14:47
     */
    public List<ChapterContent> queryContentList(ChapterContent chapterContent) {
        String sqlId = NAME_SPACE + "queryContentList" ;
        return daoClient.queryForList(sqlId,chapterContent,ChapterContent.class);
    }

    /**
     * @Description 查询视频总数（章节下视频小节）
     * @author Joanne
     * @create 2018/6/2 14:48
     */
    public Integer countChapterContent(ChapterContent chapterContent) {
        String sqlId = NAME_SPACE + "countChapterContent" ;
        Long total = daoClient.queryForObject(sqlId,chapterContent,Long.class);
        return total.intValue();
    }

    /**
     * @Description 添加视频（章节下视频，真正添加视频）
     * @author Joanne
     * @create 2018/6/2 14:49
     */
    public Integer addChapterContent(ChapterContent chapterContent) {
        String sqlId = NAME_SPACE + "addChapterContent" ;
        return daoClient.insertAndGetId(sqlId,chapterContent);
    }

    /**
     * @Description 更新章节视频信息 todo
     * @author Joanne
     * @create 2018/6/7 11:05
     */
    public void updateContent(ChapterContent chapterContent) {
        String sqlId = NAME_SPACE + "modifyChapterContent";
        daoClient.excute(sqlId,chapterContent);
    }

    /**
     * @Description 根据章节id查询视频内容
     * @author Joanne
     * @create 2018/6/7 17:53
     */
    public List<ChapterContent> queryContentListByChapterId(Integer id,Integer labourerId) {
        String sqlId = NAME_SPACE + "queryContentListByChapterId" ;
        Map params = new HashMap() ;
        params.put("chapterId",id);
        params.put("labourerId",labourerId);
        return daoClient.queryForList(sqlId,params,ChapterContent.class);
    }

    /**
     * @Description 根据用户id和视频内容id查询观看记录
     * @author Joanne
     * @create 2018/6/8 17:04
     */
    public EduVideoLabourRel queryVideoLabourRelByLabourIdAndContentId(EduVideoLabourRel eduVideoLabourRel) {
        String sqlId = NAME_SPACE + "queryVideoLabourRelByLabourIdAndContentId" ;
        return daoClient.queryForObject(sqlId,eduVideoLabourRel,EduVideoLabourRel.class);
    }

    /**
     * @Description 添加视频观看记录
     * @author Joanne
     * @create 2018/6/8 17:46
     */
    public void markWatchVideo(EduVideoLabourRel eduVideoLabourRel) {
        String sqlId = NAME_SPACE + "markWatchVideo" ;
        daoClient.insertAndGetId(sqlId,eduVideoLabourRel);
    }

    /**
     * @Description 更新视频观看记录
     * @author Joanne
     * @create 2018/6/8 18:15
     */
    public void updateWatchMark(EduVideoLabourRel eduVideoLabourRel) {
        String sqlId = NAME_SPACE + "updateWatchMark" ;
        daoClient.excute(sqlId,eduVideoLabourRel);
    }

    /**
     * @Description 根据章节视频id查询章节节点内容
     * @author Joanne
     * @create 2018/6/8 21:20
     */
    public ChapterContent queryContentByContentId(Integer chapterContentId) {
        String sqlId = NAME_SPACE + "queryContentByContentId" ;
        Map param = new HashMap() ;
        param.put("chapterContentId",chapterContentId);
        return daoClient.queryForObject(sqlId,param,ChapterContent.class);
    }

    /**
     * @Description 删除内容
     * @author Joanne
     * @create 2018/6/14 18:08
     */
    public Integer deleteContent(String ids) {
        String sqlId = NAME_SPACE + "deleteContent" ;
        Map param = new HashMap();
        param.put("ids",ids);
        return daoClient.excute(sqlId,param);
    }

    /**
     * @Description 根据章节id查询章节视频
     * @author Joanne
     * @create 2018/6/15 11:51
     */
    public List<ChapterContent> queryContentListByIds(String ids) {
        String sqlId = NAME_SPACE + "queryContentByContentId" ;
        Map map = new HashMap();
        map.put("chapterContentId",ids);
        return daoClient.queryForList(sqlId,map,ChapterContent.class);
    }

    /**
     * @Description 培训系统PC端 - 根据集锦id获取全部视频内容的名称及id
     * @author Joanne
     * @create 2018/6/26 15:21
     */
    public List<ChapterContent> queryAllContentNameAndId(Integer id) {
        String sqlId = NAME_SPACE + "queryAllContentNameAndId";
        Map param = new HashMap();
        param.put("videoId",id);
        return daoClient.queryForList(sqlId,param,ChapterContent.class);
    }

    /**
     * @Description 根据 集锦id 查询视频内容
     * @author Joanne
     * @create 2018/6/26 16:05
     */
    public List<ChapterContent> queryContentListBycollectionId(Integer collectionId, Integer labourerId) {
        String sqlId = NAME_SPACE + "queryContentListBycollectionId" ;
        Map param = new HashMap();
        //集锦id
        param.put("collectionId",collectionId);
        //人员id
        param.put("labourerId",labourerId);
        return daoClient.queryForList(sqlId,param,ChapterContent.class);
    }

    /**
     * @Description 管理后台 - 保存视频内容与题目的关系
     * @author Joanne
     * @create 2018/6/28 14:10
     */
    public void saveVideoQuestionRel(Integer courseId, Integer questionId) {
        String sqlId = NAME_SPACE + "saveVideoQuestionRel" ;
        Map params = new HashMap();
        //课程id（即视频内容id）
        params.put("courseId",courseId);
        params.put("questionId",questionId);
        daoClient.insertAndGetId(sqlId,params);
    }

    /**
     * @Description
     * 培训系统PC端 - 根据课程ID查询视频列表
     * @author Joanne
     * @create 2018/7/11 15:52
     */
    public List<ChapterContent> queryContentByCourseIds(String ids) {
        String sqlId = NAME_SPACE + "queryContentByCourseIds" ;
        Map param = new HashMap() ;
        param.put("ids",ids);
        return daoClient.queryForList(sqlId,param,ChapterContent.class);
    }

    /**
     * @Description
     * 培训系统PC端 - 根据集锦ID和方案ID查询视频内容
     * @author Joanne
     * @create 2018/7/16 16:15
     */
    public List<ChapterContent> queryContentByProgramId(Integer id, Integer programId) {
        String sqlId = NAME_SPACE + "queryContentByProgramId" ;
        Map map = new HashMap() ;
        map.put("programId",programId);
        map.put("id",id);
        return daoClient.queryForList(sqlId,map,ChapterContent.class);
    }

    /**
     * @Description
     * 培训系统PC端 - 根据方案ID查询课程
     * @author Joanne
     * @create 2018/7/16 19:51
     */
    public List<ChapterContent> queryContentByProgramId(Integer programId) {
        String sqlId = NAME_SPACE + "queryContentByProgramId" ;
        Map map = new HashMap() ;
        map.put("programId",programId);
        return daoClient.queryForList(sqlId,map,ChapterContent.class);
    }

    /**
     * @Description 培训系统PC版 - 查询视频内容及试题概况
     * @author Joanne
     * @create 2018/7/18 17:16
     */
    public List<ChapterContent> queryContentByVideoId(Integer catalogId, String videoName) {
        String sqlId = NAME_SPACE + "queryContentByVideoId";
        Map map = new HashMap();
        map.put("catalogId",catalogId);
        map.put("videoName",videoName);
        return daoClient.queryForList(sqlId,map,ChapterContent.class);
    }


    /**
     * @Description 根据id ,查询大于该id 的第一条视频章节内容数据
     * @author liuao
     * @create 2018/7/18 17:16
     */
    public ChapterContent queryNextChapterContentById(Integer chapterContentId) {
        String sqlId = NAME_SPACE + "queryNextChapterContentById";
        Map map = new HashMap();
        map.put("chapterContentId",chapterContentId);
        return daoClient.queryForObject(sqlId,map,ChapterContent.class);
    }

    /**
     *  根据id ,查询大于该id 的第一条视频题目关系 数据
     * @author liuao
     * @date 2018/7/20 15:51
     */
    public EduVideoQuestionRel queryNextQuestionRelById(Integer relId){
        String sqlId = NAME_SPACE + "queryNextQuestionRelById";
        Map map = new HashMap();
        map.put("relId",relId);
        return daoClient.queryForObject(sqlId,map,EduVideoQuestionRel.class);
    }

    /**
     *  根据
     * @author liuao
     * @date 2018/7/20 16:41
     */
    public ChapterContent queryChapterContentByTitle(String title) {
        String sqlId = NAME_SPACE + "queryChapterContentByTitle";
        Map map = new HashMap();
        map.put("title",title);
        return daoClient.queryForObject(sqlId,map,ChapterContent.class);
    }

}
