package com.xgt.dto;
/**
 *  自动组卷参数，随机选题参数
 * @author liuao
 * @date 2018/6/29 11:40
 */
public class RandomQuestionParam {

    private String questionType ;
    private String difficultyDegree ;
    private Integer selectedNumber;

    public String getQuestionType() {
        return questionType;
    }

    public RandomQuestionParam(String questionType, String difficultyDegree, Integer selectedNumber) {
        this.questionType = questionType;
        this.difficultyDegree = difficultyDegree;
        this.selectedNumber = selectedNumber;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(String difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }

    public Integer getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(Integer selectedNumber) {
        this.selectedNumber = selectedNumber;
    }
}
