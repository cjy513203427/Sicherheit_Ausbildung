package com.xgt.dao.imgtext;

import com.xgt.base.DaoClient;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import org.apache.poi.ss.formula.functions.Na;
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
 * @create 2018-06-05 11:16
 **/
@Repository
public class ImgtxtClassifyDao {

    private static final Logger logger = LoggerFactory.getLogger(ImgtxtClassifyDao.class);

    @Autowired
    private DaoClient daoClient;

    private static final String NAME_SPACE = "imgtxtClassify.";

    /**
     * @Description 查询分类树
     * @author Joanne
     * @create 2018/6/5 17:11
     */
    public List<EduImgtxtClassify> queryTreeList() {
        String sqlId = NAME_SPACE + "queryTreeList";
        Map map = new HashMap();
        return daoClient.queryForList(sqlId, map, EduImgtxtClassify.class);
    }

    /**
     * @Description 添加分类树
     * @author Joanne
     * @create 2018/6/5 17:11
     */
    public Integer addClassify(EduImgtxtClassify eduImgTxt) {
        String sqlId = NAME_SPACE + "addClassify";
        return daoClient.insertAndGetId(sqlId, eduImgTxt);
    }

    /**
     * @Description 更新分类树
     * @author Joanne
     * @create 2018/6/5 17:11
     */
    public void updateClassify(EduImgtxtClassify eduImgTxt) {
        String sqlId = NAME_SPACE + "updateClassify";
        daoClient.excute(sqlId, eduImgTxt);
    }

    /**
     * @Description 删除分类
     * @author Joanne
     * @create 2018/6/9 14:16
     */
    public void deleteClassify(Integer classifyId) {
        String sqlId = NAME_SPACE + "deleteClassify";
        Map param = new HashMap();
        param.put("id", classifyId);
        daoClient.excute(sqlId, param);
    }

    /**
     * @Description 查询热门分类
     * @author Joanne
     * @create 2018/6/12 14:15
     */
    public List<EduImgtext> queryHotClassify() {
        String sqlId = NAME_SPACE + "queryHotClassify";
        return daoClient.queryForList(sqlId, EduImgtext.class);
    }

    /**
     * @Description 根据图文分类的id查询图文分类信息
     * @author HeLiu
     * @date 2018/7/19 20:56
     */
    public EduImgtxtClassify queryClassifyById(Integer classifyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifyId", classifyId);
        String sqlId = NAME_SPACE + "queryClassifyById";
        return daoClient.queryForObject(sqlId, map, EduImgtxtClassify.class);
    }

    /**
     * @Description 根据ClassifyName查询是否存在
     * @author HeLiu
     * @date 2018/7/20 9:55
     */
    public Integer isExistsToLocalByClassifyName(String classifyName) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifyName", classifyName);
        String sqlId = NAME_SPACE + "isExistsToLocalByClassifyName";
        return daoClient.queryForObject(sqlId, map, Integer.class);
    }

    /**
     * @Description 根据ClassifyName查询图文分类信息
     * @author HeLiu
     * @date 2018/7/20 11:16
     */
    public EduImgtxtClassify queryClassifyByNameToLocal(String classifyName) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifyName", classifyName);
        String sqlId = NAME_SPACE + "queryClassifyByNameToLocal";
        return daoClient.queryForObject(sqlId, map, EduImgtxtClassify.class);
    }
}
