package com.xgt.entity.train;

import com.xgt.common.PageQueryEntity;
import com.xgt.entity.video.ChapterContent;

import java.util.List;

/**
 * @author Joanne
 * @Description
 * @create 2018-06-27 9:26
 **/
public class EduTrainProgram extends PageQueryEntity {
    private Integer id ;
    //方案用户公司id
    private Integer companyId ;
    //方案名称
    private String programName ;
    //培训类型
    private String programType ;
    //类型文本
    private String programTypeTxt ;
    //封面路径
    private String coverPath ;
    private String createTime ;
    private String updateTime ;

    private String coverBase64 ;

    //方案课程
    private List<ChapterContent> chapterContents ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProgramTypeTxt() {
        return programTypeTxt;
    }

    public void setProgramTypeTxt(String programTypeTxt) {
        this.programTypeTxt = programTypeTxt;
    }

    public List<ChapterContent> getChapterContents() {
        return chapterContents;
    }

    public void setChapterContents(List<ChapterContent> chapterContents) {
        this.chapterContents = chapterContents;
    }

    public String getCoverBase64() {
        return coverBase64;
    }

    public void setCoverBase64(String coverBase64) {
        this.coverBase64 = coverBase64;
    }
}
