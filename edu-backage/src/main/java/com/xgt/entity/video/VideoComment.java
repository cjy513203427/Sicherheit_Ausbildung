package com.xgt.entity.video;

import com.xgt.common.PageQueryEntity;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-01 16:53
 **/
public class VideoComment extends PageQueryEntity {
    private Integer id ;
    //视频集锦ID
    private Integer eduVideoId ;
    //视频评论
    private String comment ;
    //视频评分
    private Integer score ;
    //创建人ID
    private Integer createId ;
    //创建时间
    private String createTime ;

    private String createUserName ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getEduVideoId() {
        return eduVideoId;
    }

    public void setEduVideoId(Integer eduVideoId) {
        this.eduVideoId = eduVideoId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
