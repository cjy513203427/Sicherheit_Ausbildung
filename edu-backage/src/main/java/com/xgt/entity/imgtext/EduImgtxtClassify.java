package com.xgt.entity.imgtext;

import java.util.List;

/**
 * @author Joanne
 * @Description 图文分类
 * @create 2018-06-05 10:55
 **/
public class EduImgtxtClassify {
    private Integer id ;
    //分类名称
    private String classifyName ;
    private Integer parentId ;
    private String createTime ;
    //ext读树必有参数
    private List<EduImgtxtClassify> children;// 子节点
    private boolean leaf = false;// 是否为叶子

    //第三级子节点包含的文章数
    private Integer contentAmount ;
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<EduImgtxtClassify> getChildren() {
        return children;
    }

    public void setChildren(List<EduImgtxtClassify> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getContentAmount() {
        return contentAmount;
    }

    public void setContentAmount(Integer contentAmount) {
        this.contentAmount = contentAmount;
    }
}
