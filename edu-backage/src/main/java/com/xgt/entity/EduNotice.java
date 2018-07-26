package com.xgt.entity;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-11 17:12
 **/
public class EduNotice {
    private Integer id ;
    //通知类型 1.课程通知 2.考试通知
    private String noticeType;
    //外键id 课程/考试id
    private Integer referenceId ;
    //通知类容
    private String noticeContent ;
    //状态 1.未读 2.已读
    private String status ;
    //人员id
    private Integer labourId ;
    private String createTime ;
    private String updateTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
