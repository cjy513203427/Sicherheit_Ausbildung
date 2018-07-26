package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * 建筑工地学员信息实体类
 *
 * @author HL
 * @date 2018/6/1.
 */
public class BuildLabourer extends PageQueryEntity implements Serializable {

    private Integer id;
    //工地编码
    private String buildSiteCode;
    //姓名
    private String realname;
    //身份证号码
    private String idCard;
    //性别
    private String sex;
    //地址
    private String address;
    //手机号
    private String phone;
    //密码
    private String password;
    //微信openId
    private String openId;
    //岗位
    private String postType;
    //岗位文字
    private String postTypeTxt ;
    //上级领导
    private String leaderName;
    //上级领导手机号
    private String leaderPhone;
    //累计学习天数
    private Integer cumulativeDay;
    //可用状态（1：启用，2：禁用）
    private String status;
    //创建人ID
    private Integer createUserId;
    //创建人
    private String createUserName;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //账户数量
    private Integer accountNumber;
    //建筑工地状态
    private String siteStatus;
    //民族
    private String nation;
    //生日
    private String birthday;
    //签发机关
    private String idIssued;
    //有效开始日期
    private String issuedDate;
    //有效截至日期
    private String validDate;
    //图片Base64内容
    private String base64Photo;
    //通过考试次数
    private Integer passCount;
    //通知总数
    private Integer noticeCount;
    //权限工工地编码
    private String roleCode;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //所属公司id
    private Integer buildCompanyId ;
    //工种
    private String workType;
    // 指纹1base64
    private String finger1Base64;
    // 指纹2base64
    private String finger2Base64;
    // 指纹3base64
    private String finger3Base64;
    //学历
    private String eduLevel;
    //施工区域
    private String constructionArea;
    //入场时间
    private String entranceTime;
    //紧急联系人
    private String emergencyContact;
    //紧急联系人电话
    private String emergencyContactPhone;
    //合同编号
    private String contractCode;
    //保险详情
    private String insuranceDetail;
    //离场时间
    private String leaveTime;
    //健康状况
    private String healthyStatus;
    //婚姻状况
    private String maritalStatus;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuildSiteCode() {
        return buildSiteCode;
    }

    public void setBuildSiteCode(String buildSiteCode) {
        this.buildSiteCode = buildSiteCode;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
    }

    public Integer getCumulativeDay() {
        return cumulativeDay;
    }

    public void setCumulativeDay(Integer cumulativeDay) {
        this.cumulativeDay = cumulativeDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdIssued() {
        return idIssued;
    }

    public void setIdIssued(String idIssued) {
        this.idIssued = idIssued;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public Integer getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(Integer noticeCount) {
        this.noticeCount = noticeCount;
    }

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }

    public Integer getBuildCompanyId() {
        return buildCompanyId;
    }

    public void setBuildCompanyId(Integer buildCompanyId) {
        this.buildCompanyId = buildCompanyId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getFinger1Base64() {
        return finger1Base64;
    }

    public void setFinger1Base64(String finger1Base64) {
        this.finger1Base64 = finger1Base64;
    }

    public String getFinger2Base64() {
        return finger2Base64;
    }

    public void setFinger2Base64(String finger2Base64) {
        this.finger2Base64 = finger2Base64;
    }

    public String getFinger3Base64() {
        return finger3Base64;
    }

    public void setFinger3Base64(String finger3Base64) {
        this.finger3Base64 = finger3Base64;
    }

    public String getPostTypeTxt() {
        return postTypeTxt;
    }

    public void setPostTypeTxt(String postTypeTxt) {
        this.postTypeTxt = postTypeTxt;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getConstructionArea() {
        return constructionArea;
    }

    public void setConstructionArea(String constructionArea) {
        this.constructionArea = constructionArea;
    }

    public String getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(String entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getHealthyStatus() {
        return healthyStatus;
    }

    public void setHealthyStatus(String healthyStatus) {
        this.healthyStatus = healthyStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuildLabourer{");
        sb.append("id=").append(id);
        sb.append(", buildSiteCode='").append(buildSiteCode).append('\'');
        sb.append(", realname='").append(realname).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", openId='").append(openId).append('\'');
        sb.append(", postType='").append(postType).append('\'');
        sb.append(", leaderName='").append(leaderName).append('\'');
        sb.append(", leaderPhone='").append(leaderPhone).append('\'');
        sb.append(", cumulativeDay=").append(cumulativeDay);
        sb.append(", status='").append(status).append('\'');
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createUserName='").append(createUserName).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", siteStatus='").append(siteStatus).append('\'');
        sb.append(", nation='").append(nation).append('\'');
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", idIssued='").append(idIssued).append('\'');
        sb.append(", issuedDate='").append(issuedDate).append('\'');
        sb.append(", validDate='").append(validDate).append('\'');
        sb.append(", base64Photo='").append(base64Photo).append('\'');
        sb.append(", passCount=").append(passCount);
        sb.append(", noticeCount=").append(noticeCount);
        sb.append(", roleCode='").append(roleCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
