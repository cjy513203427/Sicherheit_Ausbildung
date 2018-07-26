package com.xgt.entity.train;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-27 9:40
 **/
public class EduTrainLessonRel {
    private Integer id ;
    //方案id
    private Integer trainProgramId ;
    //视频集锦id
    private Integer videoId ;
    //视频id
    private Integer chapterContentId ;
    private String crateTime ;
    private String updateTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Integer trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getChapterContentId() {
        return chapterContentId;
    }

    public void setChapterContentId(Integer chapterContentId) {
        this.chapterContentId = chapterContentId;
    }

    public String getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
