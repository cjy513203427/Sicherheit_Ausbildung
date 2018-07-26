package com.xgt.entity.video;

import java.util.List;

/**
 * @author Joanne
 * @Description 视频章节
 * @create 2018-06-01 16:51
 **/
public class VideoChapter {
    private Integer id ;
    //视频集锦id
    private Integer eduVideoId ;

    //集锦名称
    private String eduVideoName ;
    //章节id
    private String chapterName ;

    private Integer createUserId ;
    private String createTime ;

    private List<ChapterContent> contentList ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getEduVideoId() {
        return eduVideoId;
    }

    public void setEduVideoId(Integer eduVideoId) {
        this.eduVideoId = eduVideoId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getEduVideoName() {
        return eduVideoName;
    }

    public void setEduVideoName(String eduVideoName) {
        this.eduVideoName = eduVideoName;
    }

    public List<ChapterContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<ChapterContent> contentList) {
        this.contentList = contentList;
    }
}
