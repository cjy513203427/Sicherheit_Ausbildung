package com.xgt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * @author Joanne
 * @Description 字典实体类
 * @create 2018-03-30 17:41
 **/
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dictionary extends PageQueryEntity implements Serializable {

    private Integer id ;

    //类型描述
    private String typeDesc ;

    //类型编码
    private String typeCode ;

    //字典值
    private String value ;

    //字典值描述
    private String text ;

    private Integer sort ;

    private String param1;

    private String param2;

    private Integer operatorId ;

    private String realname ;

    private String expectCompleteTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getExpectCompleteTime() {
        return expectCompleteTime;
    }

    public void setExpectCompleteTime(String expectCompleteTime) {
        this.expectCompleteTime = expectCompleteTime;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", typeDesc='" + typeDesc + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", sort=" + sort +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", operatorId=" + operatorId +
                ", realname='" + realname + '\'' +
                ", expectCompleteTime='" + expectCompleteTime + '\'' +
                '}';
    }
}
