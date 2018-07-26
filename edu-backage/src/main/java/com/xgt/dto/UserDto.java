package com.xgt.dto;

/**
 * Created by HeLiu on 2018/7/16.
 * 学员数据传输对象
 */
public class UserDto {
    private Integer index;

    private Integer labourerId;

    private String realname;

    private String idCard;

    private String basePhoto;

    private String workType;

    private String siteName;

    private Integer examScore;

    private String passStatus;

    private String answers;

    private String answerStatus;

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Integer getLabourerId() {
        return labourerId;
    }

    public void setLabourerId(Integer labourerId) {
        this.labourerId = labourerId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBasePhoto() {
        return basePhoto;
    }

    public void setBasePhoto(String basePhoto) {
        this.basePhoto = basePhoto;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getExamScore() {
        return examScore;
    }

    public void setExamScore(Integer examScore) {
        this.examScore = examScore;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }


    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
