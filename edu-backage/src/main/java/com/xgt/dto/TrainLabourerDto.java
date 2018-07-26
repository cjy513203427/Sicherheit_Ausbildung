package com.xgt.dto;

/**
 * Created by HeLiu on 2018/7/3.
 * 培训学员信息数据传输对象
 */
public class TrainLabourerDto {

    //单位编号
    private Integer siteId;
    //受训单位
    private String siteName;
    //人员编号
    private Integer labourerId;
    //姓名
    private String realName;
    //考试次数
    private Integer examCount;
    //考试通过次数
    private Integer passCount;
    //总培训时长（分钟）
    private Integer totalTime;
    //试题学习时长（分钟）
    private Integer testTime;
    //考试合格率
    private String testPass;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getExamCount() {
        return examCount;
    }

    public void setExamCount(Integer examCount) {
        this.examCount = examCount;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    public String getTestPass() {
        return testPass;
    }

    public void setTestPass(String testPass) {
        this.testPass = testPass;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getLabourerId() {
        return labourerId;
    }

    public void setLabourerId(Integer labourerId) {
        this.labourerId = labourerId;
    }
}
