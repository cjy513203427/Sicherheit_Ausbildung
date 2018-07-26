package com.xgt.entity.train;

/**
 * @program: safety_edu
 * @description: 题目统计信息
 * @author: Joanne
 * @create: 2018-07-12 10:56
 **/
public class QuestionStatistics {

    //题目类型
    private String difficultyDegree;

    //题目难易程度
    private String questionType ;

    //题目数目
    private Integer questionAmount ;


    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    public String getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(String difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }
}
