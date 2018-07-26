package com.xgt.entity.train;

import java.util.List;

/**
 * @program: safety_edu
 * @description: 培训计划
 * @author: Joanne
 * @create: 2018-07-12 18:15
 **/
public class EduTrainPlan {

    private Integer id ;

    //计划名称
    private String planName ;

    //方案ID
    private Integer programId ;

    //方案名称
    private String programName ;

    //培训开始时间
    private String startDate ;

    //培训结束时间
    private String endDate ;

    //题目视频播放次数
    private Integer questionPlayTimes ;

    private List<EduTraplanLabourRel> eduTraplanLabourRels ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate.contains(" 00:00:00")?startDate:startDate + " 00:00:00";
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate.contains(" 23:59:59")?endDate:endDate + " 23:59:59";
    }

    public Integer getQuestionPlayTimes() {
        return questionPlayTimes;
    }

    public void setQuestionPlayTimes(Integer questionPlayTimes) {
        this.questionPlayTimes = questionPlayTimes;
    }

    public List<EduTraplanLabourRel> getEduTraplanLabourRels() {
        return eduTraplanLabourRels;
    }

    public void setEduTraplanLabourRels(List<EduTraplanLabourRel> eduTraplanLabourRels) {
        this.eduTraplanLabourRels = eduTraplanLabourRels;
    }
}
