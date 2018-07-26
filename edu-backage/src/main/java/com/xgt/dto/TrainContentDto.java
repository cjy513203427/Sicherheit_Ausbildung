package com.xgt.dto;

/**
 * Created by HeLiu on 2018/7/16.
 * 培训内容数据传输对象
 */
public class TrainContentDto {
    private Integer index;

    private String videoTitle;

    private String chapterTitle;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }
}
