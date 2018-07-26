package com.xgt.entity;

import java.math.BigDecimal;

/**
 *  试卷题目关系
 * @author liuao
 * @date 2018/6/29 10:20
 */
public class EduExamQuestionRel {

    private Integer questionId ;
    private BigDecimal questionScore ;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }
}
