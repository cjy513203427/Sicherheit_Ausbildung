package com.xgt.dto;

/**
 * Created by HeLiu on 2018/6/30.
 * 培训记录详情数据传输对象
 */
public class EduPlanDetailsDto {

    //学员姓名
    private String realname;
    //受训单位
    private String sitename;
    //培训时间
    private String startDate;
    //考试时间
    private String examDate;
    //考试得分
    private Integer examScore;
    //是否合格（1：合格、2：不合格）
    private String passStatus;
    //培训方案名称
    private String programName;
    //状态
    private String status;
    //开始时间
    private String start;
    //结束时间
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
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

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
