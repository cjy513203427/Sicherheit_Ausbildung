package com.xgt.entity.exam;

/**
 * Created by HeLiu on 2018/7/18.
 * 答题器编号人员关系表实体类
 */
public class AnswertoolRel {

    private Integer id;
    //答题器编号
    private Integer answerToolNum;
    //工人id
    private Integer labourId;
    //培训计划id
    private Integer planId;
    //培训方案id
    private Integer programId;
    //培训试卷id
    private Integer examId;
    //创建时间
    private String createTime;
    //跟新时间
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswerToolNum() {
        return answerToolNum;
    }

    public void setAnswerToolNum(Integer answerToolNum) {
        this.answerToolNum = answerToolNum;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
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
