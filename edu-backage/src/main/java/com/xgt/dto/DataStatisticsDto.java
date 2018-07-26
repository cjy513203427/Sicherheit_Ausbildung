package com.xgt.dto;

/**
 * Created by HeLiu on 2018/7/9.
 * 数据统计数据传输对象
 */
public class DataStatisticsDto {

    //单位名称
    private String siteName;
    //学员数量（不含离职）
    private Integer labourerCount;
    //总人数
    private Integer totalCount;
    //培训数量
    private Integer trainCount;
    //培训率
    private String trainLv;
    //合格率
    private String passLv;
    //总学时
    private Integer totalTime;
    //人均学时
    private Integer percapitaTime;
    //培训人次
    private Integer trainLabourerCount;
    //人均培训次数
    private Integer percapitaCount;

    private String code;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getLabourerCount() {
        return labourerCount;
    }

    public void setLabourerCount(Integer labourerCount) {
        this.labourerCount = labourerCount;
    }

    public Integer getTrainCount() {
        return trainCount;
    }

    public void setTrainCount(Integer trainCount) {
        this.trainCount = trainCount;
    }

    public String getTrainLv() {
        return trainLv;
    }

    public void setTrainLv(String trainLv) {
        this.trainLv = trainLv;
    }

    public String getPassLv() {
        return passLv;
    }

    public void setPassLv(String passLv) {
        this.passLv = passLv;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getPercapitaTime() {
        return percapitaTime;
    }

    public void setPercapitaTime(Integer percapitaTime) {
        this.percapitaTime = percapitaTime;
    }

    public Integer getTrainLabourerCount() {
        return trainLabourerCount;
    }

    public void setTrainLabourerCount(Integer trainLabourerCount) {
        this.trainLabourerCount = trainLabourerCount;
    }

    public Integer getPercapitaCount() {
        return percapitaCount;
    }

    public void setPercapitaCount(Integer percapitaCount) {
        this.percapitaCount = percapitaCount;
    }
}
