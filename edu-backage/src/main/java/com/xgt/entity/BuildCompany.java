package com.xgt.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cjy
 * @Date 2018/6/26 9:55.
 */
public class BuildCompany {
    //主键id
    private Integer id;
    //公司名称
    private String companyName;
    //账户数量
    private Integer accountNumber;
    //账户单价
    private BigDecimal unitPrice;
    //创建人id
    private Integer createUserId;
    //创建人名称
    private String createUsername;
    //是否有效（0:无效、1:有效）
    private String status;
    //销售人员id
    private Integer salesmanId;
    //销售人员名称
    private String salesmanName;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //是否为叶子，0否，1是
    public Boolean leaf = false;
    //孩子节点
    public List<BuildSite> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
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

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public List<BuildSite> getChildren() {
        return children;
    }

    public void setChildren(List<BuildSite> children) {
        this.children = children;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }
}
