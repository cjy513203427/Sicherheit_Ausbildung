package com.xgt.entity;

import java.util.List;

/**
 * 附件
 * @author cjy
 * @Date 2018/6/26 14:50.
 */
public class BuildAttachment {
    //主键id
    private Integer id;
    //外键id
    private Integer foreignId;
    //附件名称
    private String attachmentName;
    //附件地址
    private String attachmentPath;
    //文件流
    private byte[] attachmentByte;
    //附件类型
    private String attachmentType;
    //附件编号，证件编号
    private String attachmentCode;
    //附件来源
    private String source;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;

    private List<String> attachmentPaths;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
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

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public byte[] getAttachmentByte() {
        return attachmentByte;
    }

    public void setAttachmentByte(byte[] attachmentByte) {
        this.attachmentByte = attachmentByte;
    }

    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    public List<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void setAttachmentPaths(List<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }
}
