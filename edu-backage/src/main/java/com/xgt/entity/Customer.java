package com.xgt.entity;


import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * 客户信息实体类
 * @author liuao
 * @date 2018/3/29 19:37
 */
public class Customer extends BuildLabourer implements Serializable {
    // 验证码
    private String validateCode ;

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
