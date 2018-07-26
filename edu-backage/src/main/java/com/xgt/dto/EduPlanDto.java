package com.xgt.dto;

import com.xgt.common.PageQueryEntity;

import java.util.List;

/**
 * Created by HeLiu on 2018/6/29.
 * 培训记录数据传输对象
 */
public class EduPlanDto extends PageQueryEntity {

    //建筑公司编号
    private Integer companyId;
    //公司名称
    private String companyName;
    //方案编号
    private Integer programId;
    //计划编号
    private Integer planId;
    //方案名称
    private String programName;
    //方案类型
    private String programType;
    //方案类型名称
    private String programTypeName;
    //培训开始时间
    private String startDate;
    //培训结束时间
    private String endDate;
    //人数
    private Integer labourCount;
    //合格率
    private String percentOfPass;
    //状态
    private String planStatus;
    //课程时长
    private Integer timeLength;
    //封面路径
    private String coverPath;
    //参训人数
    private Integer trainCount;
    //是否通过（1：通过、2：不通过）
    private String passStatus;
    //培训信息（上半部分）list
    private List<EduPlanDto> trainInfoList;
    //培训内容list
    private List<TrainContentDto> trainContentList;

    public Integer getTrainCount() {
        return trainCount;
    }

    public void setTrainCount(Integer trainCount) {
        this.trainCount = trainCount;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLabourCount() {
        return labourCount;
    }

    public void setLabourCount(Integer labourCount) {
        this.labourCount = labourCount;
    }

    public String getPercentOfPass() {
        return percentOfPass;
    }

    public void setPercentOfPass(String percentOfPass) {
        this.percentOfPass = percentOfPass;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public String getProgramTypeName() {
        return programTypeName;
    }

    public void setProgramTypeName(String programTypeName) {
        this.programTypeName = programTypeName;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public List<EduPlanDto> getTrainInfoList() {
        return trainInfoList;
    }

    public void setTrainInfoList(List<EduPlanDto> trainInfoList) {
        this.trainInfoList = trainInfoList;
    }

    public List<TrainContentDto> getTrainContentList() {
        return trainContentList;
    }

    public void setTrainContentList(List<TrainContentDto> trainContentList) {
        this.trainContentList = trainContentList;
    }
}
