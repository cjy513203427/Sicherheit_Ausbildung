package com.xgt.dto;

/**
 * Created by HeLiu on 2018/7/6.
 * 培训方案数据传输对象
 */
public class EduProgramDto {

    //方案编号
    private Integer programId;
    //方案名称
    private String programName;
    //方案类型
    private String programType;
    //方案类型名称
    private String programTypeName;
    //课程时长
    private Integer timeLength;
    //封面路径
    private String coverPath;

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
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

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
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
}
