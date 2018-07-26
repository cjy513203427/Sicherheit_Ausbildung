package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * 题库 实体类
 * @author cjy
 * @date 2018/6/1 17:42
 */
public class EduQuestionBank extends PageQueryEntity implements Serializable {
    //主键id
    private Integer id;
    //题库名称
    private String title;
    //岗位(1:管理人员、2：作业人员、3：普通工人、9：其他)
    private String postType;
    //创建人id
    private Integer createUserId;
    //创建时间
    private String createTime;
    //删除状态（1：未删除、2：已删除）
    private Integer deleteStatus;
    //真实姓名
    private String realName;
    //已答题目数
    private Integer answeredCount = 0;
    //该题库题目总数
    private Integer totalCount = 0;
    //正确问题数
    private Integer correctCount =0;
    //正确率
    private Integer accuracy;
    // 该题库收藏的题目数量
    private Integer questionCount ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAnsweredCount() {
        return answeredCount;
    }

    public void setAnsweredCount(Integer answeredCount) {
        this.answeredCount = answeredCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }
}
