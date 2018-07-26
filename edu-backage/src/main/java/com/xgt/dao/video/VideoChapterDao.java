package com.xgt.dao.video;

import com.xgt.base.DaoClient;
import com.xgt.entity.video.ChapterContent;
import com.xgt.entity.video.VideoChapter;
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
 * @create 2018-06-02 10:03
 **/
@Repository
public class VideoChapterDao {
    private static final Logger logger = LoggerFactory.getLogger(VideoChapterDao.class);
    private static final String NAME_SPACE = "videoChapter.";

    @Autowired
    private DaoClient daoClient ;

    /**
     * @Description 查询章节列表
     * @author Joanne
     * @create 2018/6/2 14:45
     */
    public List<VideoChapter> queryChapterList(VideoChapter videoChapter) {
        String sqlId = NAME_SPACE + "queryChapterList" ;
        return daoClient.queryForList(sqlId,videoChapter,VideoChapter.class);
    }

    /**
     * @Description 查询章节数
     * @author Joanne
     * @create 2018/6/2 14:46
     */
    public Integer countChapter(VideoChapter videoChapter) {
        String sqlId = NAME_SPACE + "countChapter" ;
        Long total = daoClient.queryForObject(sqlId,videoChapter,Long.class);
        return total.intValue();
    }

    /**
     * @Description 添加章节
     * @author Joanne
     * @create 2018/6/2 14:46
     */
    public void addChapter(VideoChapter videoChapter) throws Exception {
        String sqlId = NAME_SPACE + "addChapter" ;
        daoClient.insertAndGetId(sqlId,videoChapter);
    }

    /**
     * @Description 更新章节信息
     * @author Joanne
     * @create 2018/6/7 11:00
     */
    public void updateChapter(VideoChapter videoChapter) {
        String sqlId = NAME_SPACE + "updateChapter" ;
        daoClient.excute(sqlId,videoChapter);
    }

    /**
     * @Description 根据集锦id查询章节信息
     * @author Joanne
     * @create 2018/6/7 17:37
     */
    public List<VideoChapter> queryChapterListByCollectionId(Integer collectionId) {
        String sqlId = NAME_SPACE + "queryChapterListByCollectionId" ;
        Map param = new HashMap();
        param.put("collectionId",collectionId);
        return daoClient.queryForList(sqlId,param,VideoChapter.class);
    }

    /**
     * @Description 根据章节id删除章节所有视频
     * @author Joanne
     * @create 2018/6/15 10:09
     */
    public void deleteVideoByChapterId(String ids) {
        String sqlId = NAME_SPACE + "deleteVideoByChapterId" ;
        Map param = new HashMap();
        param.put("ids",ids);
        daoClient.excute(sqlId,param);
    }

    public void deleteChapter(String ids) {
        String sqlId = NAME_SPACE + "deleteChapter" ;
        Map param = new HashMap();
        param.put("ids",ids);
        daoClient.excute(sqlId,param);
    }

    public List<ChapterContent> queryContentByChapterIds(String ids) {
        String sqlId = NAME_SPACE + "queryContentByChapterIds" ;
        Map map = new HashMap();
        map.put("ids",ids);
        return daoClient.queryForList(sqlId,map,ChapterContent.class);
    }

    /**
     * @Description 培训系统PC版 根据集锦id查询全部章节
     * @author Joanne
     * @create 2018/6/26 15:12
     */
    public List<VideoChapter> queryAllChapter(Integer id) {
        String sqlId = NAME_SPACE + "queryAllChapter" ;
        Map param = new HashMap();
        param.put("materialId",id);
        return daoClient.queryForList(sqlId,param,VideoChapter.class);
    }
}
