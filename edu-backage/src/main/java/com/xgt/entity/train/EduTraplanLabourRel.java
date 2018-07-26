package com.xgt.entity.train;

/**
 * @program: safety_edu
 * @description: 计划人员关系
 * @author: Joanne
 * @create: 2018-07-16 20:34
 **/
public class EduTraplanLabourRel {
    //关系ID
    private Integer id ;
    //计划ID
    private Integer trainPlanId ;
    //人员ID
    private Integer labourId ;
    private String createTime ;
    private String updateTime ;
    //人员姓名
    private String realName ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainPlanId() {
        return trainPlanId;
    }

    public void setTrainPlanId(Integer trainPlanId) {
        this.trainPlanId = trainPlanId;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
