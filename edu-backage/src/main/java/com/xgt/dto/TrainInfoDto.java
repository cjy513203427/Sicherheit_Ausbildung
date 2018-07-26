package com.xgt.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by HeLiu on 2018/7/2.
 * 单位信息数据传输对象
 */
public class TrainInfoDto {

    //公司编号
    private Integer CompanyId;
    //公司名称
    private String CompanyName;
    //单位编号和单位名称集合
    private List<Map<String, Object>> siteNames;

    public Integer getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Integer companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public List<Map<String, Object>> getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(List<Map<String, Object>> siteNames) {
        this.siteNames = siteNames;
    }
}
