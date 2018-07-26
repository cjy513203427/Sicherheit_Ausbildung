package com.xgt.entity;

/**
 *  课程题目关系表
 * @author liuao
 * @date 2018/7/20 15:46
 */
public class EduVideoQuestionRel {

    private Integer id ;
    private Integer chapterContentId ;
    private Integer questionId ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterContentId() {
        return chapterContentId;
    }

    public void setChapterContentId(Integer chapterContentId) {
        this.chapterContentId = chapterContentId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


}
