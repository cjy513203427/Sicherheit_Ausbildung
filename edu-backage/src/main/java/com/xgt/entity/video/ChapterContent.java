package com.xgt.entity.video;

import java.math.BigDecimal;

/**
 * @author Joanne
 * @Description 视频内容实体类
 * @create 2018-06-01 21:31
 **/
public class ChapterContent {
    //视频id
    private Integer id ;

    //视频教材id
    private Integer eduVideoId;
    //视频教材名称（集锦名称）
    private String eduVideoName ;

    //教材章节id
    private Integer videoChapterId ;
    private String videoChapterName ;

    //标题说明
    private String title ;

    //视频路径
    private String videoPath ;

    //视频长度
    private Long timeLength ;

    //视频观看进度
    private BigDecimal playProgress;

    //视频观看状态 已看完，未看完
    private String playStatus ;

    //观看比列
    private String timeRatio ;

    //试题数
    private Integer testCount ;

    private Integer createUserId ;

    private String createTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEduVideoName() {
        return eduVideoName;
    }

    public void setEduVideoName(String eduVideoName) {
        this.eduVideoName = eduVideoName;
    }

    public String getVideoChapterName() {
        return videoChapterName;
    }

    public void setVideoChapterName(String videoChapterName) {
        this.videoChapterName = videoChapterName;
    }

    public Integer getEduVideoId() {
        return eduVideoId;
    }

    public void setEduVideoId(Integer eduVideoId) {
        this.eduVideoId = eduVideoId;
    }

    public Integer getVideoChapterId() {
        return videoChapterId;
    }

    public void setVideoChapterId(Integer videoChapterId) {
        this.videoChapterId = videoChapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
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

    public String getTimeRatio() {
        return timeRatio;
    }

    public void setTimeRatio(String timeRatio) {
        this.timeRatio = timeRatio;
    }

    public Integer getTestCount() {
        return testCount;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }
}
