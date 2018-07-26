package com.xgt.entity;

/**
 *  工人答题器对应关系
 * @author liuao
 * @date 2018/7/16 14:21
 */
public class EduAnswertoolRel {
    private Integer id ;
    // 答题器编号
    private String answerToolNum;
    // 工人id
    private Integer labourId;
    // 培训计划id
    private Integer trainPlanId;
    // 培训方案id
    private Integer trainProgramId;
    // 培训试卷id
    private Integer trainExamId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerToolNum() {
        return answerToolNum;
    }

    public void setAnswerToolNum(String answerToolNum) {
        this.answerToolNum = answerToolNum;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
    }

    public Integer getTrainPlanId() {
        return trainPlanId;
    }

    public void setTrainPlanId(Integer trainPlanId) {
        this.trainPlanId = trainPlanId;
    }

    public Integer getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Integer trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    public Integer getTrainExamId() {
        return trainExamId;
    }

    public void setTrainExamId(Integer trainExamId) {
        this.trainExamId = trainExamId;
    }
}
