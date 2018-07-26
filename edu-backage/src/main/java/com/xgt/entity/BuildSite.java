package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 建筑工地信息实体类
 *
 * @author HL
 * @date 2018/6/1.
 */
public class BuildSite extends PageQueryEntity implements Serializable {

    private Integer id;
    //工地编号
    private String code;
    //工地地址
    private String address;
    //账户数量
    private Integer accountNumber;
    //账户单价
    private BigDecimal unitPrice;
    //工地联系人名称
    private String contactName;
    //联系电话
    private String contactPhone;
    //创建人(user表的id)
    private Integer createUserId;
    //创建人姓名
    private String createUserName;
    //状态
    private String status;
    //销售人员(user表的id)
    private Integer salemanId;
    //销售人员姓名
    private String salemanName;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //用于工地模糊查询
    private String superParam ;
    //公司id
    private Integer companyId;
    //单位名称
    private String siteName;
    //单位简介
    private String siteIntroduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(Integer salemanId) {
        this.salemanId = salemanId;
    }

    public String getSalemanName() {
        return salemanName;
    }

    public void setSalemanName(String salemanName) {
        this.salemanName = salemanName;
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

    public String getSuperParam() {
        return superParam;
    }

    public void setSuperParam(String superParam) {
        this.superParam = superParam;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteIntroduction() {
        return siteIntroduction;
    }

    public void setSiteIntroduction(String siteIntroduction) {
        this.siteIntroduction = siteIntroduction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuildSite{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", contactName='").append(contactName).append('\'');
        sb.append(", contactPhone='").append(contactPhone).append('\'');
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createUserName='").append(createUserName).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", salemanId='").append(salemanId).append('\'');
        sb.append(", salemanName='").append(salemanName).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
