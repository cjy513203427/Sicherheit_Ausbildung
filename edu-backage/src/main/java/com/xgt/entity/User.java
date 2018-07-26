package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户类
 *
 * @author liuao
 * @date 2018/4/1 10:31
 */
public class User extends PageQueryEntity implements Serializable {

    private Integer id;
    private String employeeNo;
    private String username;
    private String password;
    private String realname;
    private String telephone;
    private String qq;
    private String sex;
    private String modifyStatus;
    private String status;
    private String createTime;
    private String updateTime;
    private String userType;

    // 权限认证所需额外参数 start
    private List<Role> roleList;// 一个用户具有多个角色

    private List<Resource> menuList;//

    private List<Resource> buttonList;

    private String encryptSalt;
    // 权限认证所需额外参数 end
    private String imageAddressUrl;

    private String dingid;

    //工地编号
    private String siteCode;

    //建筑集团编号
    private Integer companyId;

    private String companyName ;


    public String getImageAddressUrl() {
        return imageAddressUrl;
    }

    public void setImageAddressUrl(String imageAddressUrl) {
        this.imageAddressUrl = imageAddressUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(String modifyStatus) {
        this.modifyStatus = modifyStatus;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Resource> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Resource> menuList) {
        this.menuList = menuList;
    }

    public List<Resource> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<Resource> buttonList) {
        this.buttonList = buttonList;
    }

    public String getEncryptSalt() {
        return username + encryptSalt;
    }

    public void setEncryptSalt(String encryptSalt) {
        this.encryptSalt = encryptSalt;
    }

    public String getDingid() {
        return dingid;
    }

    public void setDingid(String dingid) {
        this.dingid = dingid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", employeeNo='").append(employeeNo).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", realname='").append(realname).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", qq='").append(qq).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", modifyStatus='").append(modifyStatus).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", userType='").append(userType).append('\'');
        sb.append(", roleList=").append(roleList);
        sb.append(", menuList=").append(menuList);
        sb.append(", buttonList=").append(buttonList);
        sb.append(", encryptSalt='").append(encryptSalt).append('\'');
        sb.append(", imageAddressUrl='").append(imageAddressUrl).append('\'');
        sb.append(", dingid='").append(dingid).append('\'');
        sb.append(", siteCode='").append(siteCode).append('\'');
        sb.append(", companyId=").append(companyId);
        sb.append('}');
        return sb.toString();
    }
}
