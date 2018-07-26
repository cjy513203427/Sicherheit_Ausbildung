package com.xgt.dto;

import com.xgt.entity.BuildSite;

import java.util.List;

public class BuildSiteDto extends BuildSite{
    //工地名称
    private String siteName;
    //附件名称
    private String attachmentName;
    //附件路径
    private String attachmentPath;

    private List<BuildSiteDto> attachmentList;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

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

    public List<BuildSiteDto> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<BuildSiteDto> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
