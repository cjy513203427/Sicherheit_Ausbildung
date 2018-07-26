package com.xgt.entity.video;

import java.math.BigDecimal;

/**
 * @author Joanne 人员观看视频记录
 * @Description
 * @create 2018-06-08 15:11
 **/
public class EduVideoLabourRel {
    private Integer id ;
    //人员id
    private Integer labourId ;
    //章节内容id
    private Integer chapterContentId ;
    //集锦id
    private Integer videoId ;
    //观看进度
    private BigDecimal playProgress ;
    //观看状态 已看完，未看完
    private String playStatus ;
    //视频时长
    private BigDecimal timeLength ;
    private String createTime ;
    private String updateTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
    }

    public Integer getChapterContentId() {
        return chapterContentId;
    }

    public void setChapterContentId(Integer chapterContentId) {
        this.chapterContentId = chapterContentId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public BigDecimal getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(BigDecimal playProgress) {
        this.playProgress = playProgress;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
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

    public BigDecimal getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(BigDecimal timeLength) {
        this.timeLength = timeLength;
    }
}
