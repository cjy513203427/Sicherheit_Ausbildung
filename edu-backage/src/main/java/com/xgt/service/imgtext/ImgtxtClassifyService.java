package com.xgt.service.imgtext;

import com.xgt.dao.imgtext.ImgtxtClassifyDao;
import com.xgt.entity.imgtext.EduImgtext;
import com.xgt.entity.imgtext.EduImgtxtClassify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-05 11:18
 **/
@Service
public class ImgtxtClassifyService {
    private final static Logger logger = LoggerFactory.getLogger(ImgtextService.class);
    @Autowired
    private ImgtxtClassifyDao imgtxtClassifyDao ;

    /**
     * @Description 查询图文分类树
     * @author Joanne
     * @create 2018/6/9 14:16
     */
    public List<EduImgtxtClassify> queryTreeList() {
        List<EduImgtxtClassify> list = imgtxtClassifyDao.queryTreeList();
        return  buildResource(list);
    }

    /**
     * @Description 构建树
     * @author Joanne
     * @create 2018/6/9 14:17
     */
    private List<EduImgtxtClassify> buildResource(List<EduImgtxtClassify> list) {
        //树list
        List<EduImgtxtClassify> target = new ArrayList<>();
        if (!list.isEmpty()) {
            // 筛选出父节点为0的节点，循环list将子节点放入target，构建树
            list.stream().filter(eduImgtxtClassify -> eduImgtxtClassify.getParentId() == 0).forEach(eduImgtxtClassify -> {
                //获取子节点
                List<EduImgtxtClassify> children = getChildren(eduImgtxtClassify, list);
                eduImgtxtClassify.setChildren(children);
                target.add(eduImgtxtClassify);
            });
        }
        return target;
    }

    /**
     * @Description 构建子节点
     * @author Joanne
     * @create 2018/6/9 14:39
     */
    private List<EduImgtxtClassify> getChildren(EduImgtxtClassify eduImgtxtClassify, List<EduImgtxtClassify> list) {
        List<EduImgtxtClassify> children = new ArrayList<>();
        if (!list.isEmpty()) {
            //循环list，筛选出父节点是eduImgtxtClassify的id的记录
            list.stream().filter(child -> eduImgtxtClassify.getId() == child.getParentId()).forEach(child -> {
                List<EduImgtxtClassify> tmp = getChildren(child, list);
                child.setChildren(tmp);
                if (tmp.isEmpty()) {
                    child.setLeaf(true);
                }
                children.add(child);
            });
        }
        return children;
    }

    /**
     * @Description 添加分类树
     * @author Joanne
     * @create 2018/6/5 17:07
     */
    public Integer addClassify(EduImgtxtClassify eduImgTxt) {
        return imgtxtClassifyDao.addClassify(eduImgTxt);
    }

    /**
     * @Description 修改分类树
     * @author Joanne
     * @create 2018/6/5 17:08
     */
    public void updateClassify(EduImgtxtClassify eduImgTxt) {
        imgtxtClassifyDao.updateClassify(eduImgTxt);
    }

    /**
     * @Description 根据分类id删除类
     * @author Joanne
     * @create 2018/6/5 17:35
     */
    public void deleteClassify(Integer classifyId) {
        imgtxtClassifyDao.deleteClassify(classifyId);
    }

    /**
     * @Description 查询热门分类
     * @author Joanne
     * @create 2018/6/12 14:16
     */
    public List<EduImgtext> queryHotClassify() {
        return imgtxtClassifyDao.queryHotClassify();
    }
}
