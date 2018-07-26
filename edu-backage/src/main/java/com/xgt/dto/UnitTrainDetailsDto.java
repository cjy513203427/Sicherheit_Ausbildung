package com.xgt.dto;

import com.xgt.common.PageQueryEntity;

/**
 * Created by HeLiu on 2018/7/2.
 * 单位培训详情数据传输对象
 */
public class UnitTrainDetailsDto extends PageQueryEntity {

    //单位编号
    private Integer siteId;
    //单位名称
    private String siteName;
    //总人数
    private Integer totalCount;
    //单位code编号
    private String siteCode;
    //已受训人数
    private Integer hasedTrainCount;
    //培训率
    private String peiXunLv;
    //考试合格率
    private String passLv;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getHasedTrainCount() {
        return hasedTrainCount;
    }

    public void setHasedTrainCount(Integer hasedTrainCount) {
        this.hasedTrainCount = hasedTrainCount;
    }

    public String getPeiXunLv() {
        return peiXunLv;
    }

    public void setPeiXunLv(String peiXunLv) {
        this.peiXunLv = peiXunLv;
    }

    public String getPassLv() {
        return passLv;
    }

    public void setPassLv(String passLv) {
        this.passLv = passLv;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}
