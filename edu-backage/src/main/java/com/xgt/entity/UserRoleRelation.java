package com.xgt.entity;

import java.io.Serializable;

/**
 *  用户角色关系表
 * @author liuao
 * @date 2018/4/2 20:56
 */
public class UserRoleRelation implements Serializable {
    private Integer id ;
    private Integer userId ;
    private Integer roleId ;
    private String createTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
