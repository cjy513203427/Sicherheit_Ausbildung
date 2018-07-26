package com.xgt.entity;

import com.xgt.common.BaseController;

import java.io.Serializable;

/**
 * @author cjy
 * @Date 2018/6/7 11:36.
 */
public class EduQuestionExercise extends BaseController implements Serializable{
    //主键id
    private Integer id;
    //题库id
    private Integer questionBankId;

    private Integer[] questionBankIds;
    //题目id
    private Integer questionId;
    //工人学员id
    private Integer labourId;
    //我的答案(多选答案，以逗号分隔)
    private String myAnswer;
    //是否正确(1：正确、2：错误)
    private String answerStatus;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //岗位(1:管理人员、2：作业人员、3：普通工人、9：其他)
    private String postType;
    //当前题库题目的总数
    private Integer totalCount = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Integer questionBankId) {
        this.questionBankId = questionBankId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
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

    public Integer[] getQuestionBankIds() {
        return questionBankIds;
    }

    public void setQuestionBankIds(Integer[] questionBankIds) {
        this.questionBankIds = questionBankIds;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
