package com.xgt.dto;

import com.xgt.entity.BuildLabourer;

import java.util.List;

public class BuildLabourerDto extends BuildLabourer {
    //工人真实姓名
    private String realname;
    //附件名称
    private String attachmentName;
    //附件地址
    private String attachmentPath;
    //附件类型
    private String attachmentType;
    //附件编号，证件编号
    private String attachmentCode;
    //工地名
    private String siteName;

    private List<BuildLabourerDto> attachmentList;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<BuildLabourerDto> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<BuildLabourerDto> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public String getRealname() {
        return realname;
    }

    @Override
    public void setRealname(String realname) {
        this.realname = realname;
    }
}
