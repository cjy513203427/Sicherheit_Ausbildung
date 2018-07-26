package com.xgt.dao.imgtext;

import com.xgt.base.DaoClient;
import com.xgt.entity.EduNotice;
import com.xgt.entity.EduQuestion;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import org.apache.commons.collections.map.HashedMap;
import org.apache.regexp.RE;
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
 * @create 2018-06-05 10:25
 **/
@Repository
public class ImgtextDao {
    private static final Logger logger = LoggerFactory.getLogger(ImgtextDao.class);

    private static final String NAME_SPACE = "imgtext.";

    @Autowired
    private DaoClient daoClient;

    /**
     * @Description 查询图文详情
     * @author Joanne
     * @create 2018/6/9 16:58
     */
    public List<EduImgtext> queryImgtxtList(EduImgtext eduImgText) {
        String sqlId = NAME_SPACE + "queryImgtxtList";
        return daoClient.queryForList(sqlId, eduImgText, EduImgtext.class);
    }


    /**
     * 根据id 更新文档的音频地址
     *
     * @author liuao
     * @date 2018/6/6 14:53
     */
    public int updateAudioPathById(Integer id, String audioPath) {
        String sqlId = NAME_SPACE + "updateAudioPathById";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("audioPath", audioPath);
        return daoClient.excute(sqlId, param);
    }

    /**
     * @Description 图文集锦信息
     * @author Joanne
     * @create 2018/6/9 16:59
     */
    public List<EduImgtext> imgTextCollectionInfo(EduImgtext eduImgText) {
        String sqlId = NAME_SPACE + "imgTextCollectionInfo";
        return daoClient.queryForList(sqlId, eduImgText, EduImgtext.class);
    }

    /**
     * @Description 标记图文浏览
     * @author Joanne
     * @create 2018/6/9 17:52
     */
    public void markLearnTimes(Integer imgTextId) {
        String sqlId = NAME_SPACE + "markLearnTimes";
        Map param = new HashMap();
        param.put("imgTextId", imgTextId);
        daoClient.excute(sqlId, param);
    }

    /**
     * 根据工人id 、 页面最后一个收藏内容的id 、 分页查询收藏的图文
     *
     * @author liuao
     * @date 2018/6/11 10:28
     */
    public List<EduImgtext> queryCollectImgtxtBy(Integer labourId, Integer lastId, Integer pageSize, String collectionType) {
        String sqlId = NAME_SPACE + "queryCollectImgtxtBy";
        Map params = new HashMap();
        params.put("labourId", labourId);
        params.put("lastId", lastId);
        params.put("pageSize", pageSize);
        params.put("collectionType", collectionType);
        return daoClient.queryForList(sqlId, params, EduImgtext.class);
    }

    /**
     * 根据工人id 、 页面最后一个收藏内容的id 、 分页查询图文的学习记录
     *
     * @author liuao
     * @date 2018/6/11 15:17
     */
    public List queryStudyReorcdBy(Integer labourId, Integer lastId, Integer pageSize) {
        String sqlId = NAME_SPACE + "queryStudyReorcdBy";
        Map params = new HashMap();
        params.put("labourId", labourId);
        params.put("lastId", lastId);
        params.put("pageSize", pageSize);
        return daoClient.queryForList(sqlId, params, EduImgtext.class);
    }

    /**
     * @Description 增加图文内容
     * @author Joanne
     * @create 2018/6/11 11:39
     */
    public Integer addPicArticle(EduImgtext eduImgText) {
        String sqlId = NAME_SPACE + "addPicArticle";
        return daoClient.insertAndGetId(sqlId, eduImgText);
    }

    /**
     * @Description 图文总数
     * @author Joanne
     * @create 2018/6/11 15:49
     */
    public Integer countImgtxt(EduImgtext eduImgText) {
        String sqlId = NAME_SPACE + "countImgtxt";
        Long value = daoClient.queryForObject(sqlId, eduImgText, Long.class);
        return value.intValue();
    }

    /**
     * @Description 获取图文详情
     * @author Joanne
     * @create 2018/6/12 14:56
     */
    public EduImgtext imgTextDetial(Integer imgTextId, Integer labourId) {
        String sqlId = NAME_SPACE + "imgTextDetial";
        Map params = new HashMap();
        params.put("imgTextId", imgTextId);
        params.put("labourId", labourId);
        return daoClient.queryForObject(sqlId, params, EduImgtext.class);
    }

    /**
     * @Description 根据id修改内容缩略图路径
     * @author Joanne
     * @create 2018/6/13 21:50
     */
    public void uploadLitimg(Integer picArticleId, String filePath) {
        String sqlId = NAME_SPACE + "uploadLitimg";
        Map params = new HashMap();
        params.put("picArticleId", picArticleId);
        params.put("filePath", filePath);
        daoClient.excute(sqlId, params);
    }

    /**
     * @Description 更新图文
     * @author Joanne
     * @create 2018/6/16 10:01
     */
    public void updatePicArticle(EduImgtext eduImgText) {
        String sqlId = NAME_SPACE + "updatePicArticle";
        daoClient.excute(sqlId, eduImgText);
    }

    /**
     * @Description 根据id查询图文信息
     * @author Joanne
     * @create 2018/6/16 10:01
     */
    public List<EduImgtext> queryImgtxtListById(String ids) {
        String sqlId = NAME_SPACE + "queryImgtxtList";
        Map map = new HashMap();
        map.put("ids", ids);
        return daoClient.queryForList(sqlId, map, EduImgtext.class);
    }

    /**
     * @Description 根据id删除图文
     * @author Joanne
     * @create 2018/6/16 10:02
     */
    public void delPicArticle(String ids) {
        String sqlId = NAME_SPACE + "delPicArticle";
        Map map = new HashMap();
        map.put("ids", ids);
        daoClient.excute(sqlId, map);
    }

    /**
     * @Description 根据图文id, 查询id和大于当前该图文id的下一个图文信息
     * @author HeLiu
     * @date 2018/7/19 16:34
     */
    public EduImgtext queryNextImgTestById(Integer imgTextId) {
        Map<String, Object> map = new HashMap<>();
        map.put("imgTextId", imgTextId);
        String sqlId = NAME_SPACE + "queryNextImgTestById";
        return daoClient.queryForObject(sqlId, map, EduImgtext.class);
    }

}
