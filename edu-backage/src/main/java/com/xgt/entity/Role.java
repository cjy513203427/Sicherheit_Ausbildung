package com.xgt.entity;


import com.xgt.common.PageQueryEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色类
 * @author liuao
 * @date 2018/4/1 10:30
 */
public class Role extends PageQueryEntity implements Serializable {

    private Integer id;
    // 角色名称
    private String name;
    private String description;
    // 角色有效性 （0：无效 1:有效）
    private String status;
    private String createTime;
    private String updateTime;

    private String permissions;

    private List<Integer> resourceIdList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<Integer> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Integer> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", permissions='" + permissions + '\'' +
                ", resourceIdList=" + resourceIdList +
                '}';
    }
}
