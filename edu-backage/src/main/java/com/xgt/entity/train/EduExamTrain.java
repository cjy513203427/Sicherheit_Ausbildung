package com.xgt.entity.train;

/**
 * @author Joanne
 * @Description 方案试卷
 * @create 2018-06-27 10:30
 **/
public class EduExamTrain {
    private Integer id ;
    //考试方案id
    private Integer trainProgramId ;
    //总分
    private Integer totalScore ;
    //及格分
    private Integer passScore ;
    //题目数量
    private Integer questionNumber;
    private String createTime ;
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

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
