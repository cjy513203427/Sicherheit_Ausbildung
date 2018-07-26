package com.xgt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 权限资源 实体类
 * @author liuao
 * @date 2018/4/1 10:30
 */
public class Resource implements Serializable{

    private Integer id ;
    private Integer resourceId ;

    private String name;
    private String type;
    private String icon;

    private Integer parentId;
    private String permission;
    private String url;
    private String status;
    private String createTime;
    private String updateTime;

    // Add
    private List<Resource> children;// 子节点
    private boolean leaf = false;// 是否为叶子
    private boolean checked = false;// 角色是否已经拥有该权限
    private String text;
    private String iconCls;

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId=" + parentId +
                ", permission='" + permission + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", children=" + children +
                ", leaf=" + leaf +
                ", checked=" + checked +
                ", text='" + text + '\'' +
                ", iconCls='" + iconCls + '\'' +
                '}';
    }
}
