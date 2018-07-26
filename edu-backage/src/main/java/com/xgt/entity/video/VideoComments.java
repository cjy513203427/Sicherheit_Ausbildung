package com.xgt.entity.video;

/**
 * @author Joanne 评论实体类
 * @Description
 * @create 2018-06-07 19:25
 **/
public class VideoComments {

    //评论id
    private Integer id ;

    //集锦id
    private Integer eduVideoId ;

    //评论类容
    private String comment ;

    //评分
    private Integer score ;

    //创建人id
    private Integer createId ;

    //创建人姓名
    private String creatorName ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEduVideoId() {
        return eduVideoId;
    }

    public void setEduVideoId(Integer eduVideoId) {
        this.eduVideoId = eduVideoId;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
