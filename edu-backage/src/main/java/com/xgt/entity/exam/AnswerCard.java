package com.xgt.entity.exam;

/**
 * 答题卡实体类
 * Created by 5618 on 2018/6/9.
 */
public class AnswerCard {

    private Integer id;
    //试卷id
    private Integer examId;
    //试卷类型（1：模拟考试、2：正式考试）
    private String examType;
    //题目id
    private Integer questionId;
    //答案（多选题用逗号连接）
    private String myAnswer;
    //工人学员id
    private Integer labourId;
    //answer_status
    private String answerStatus;

    private String createTime;

    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public Integer getLabourId() {
        return labourId;
    }

    public void setLabourId(Integer labourId) {
        this.labourId = labourId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnswerCard{");
        sb.append("id=").append(id);
        sb.append(", examId=").append(examId);
        sb.append(", examType='").append(examType).append('\'');
        sb.append(", questionId=").append(questionId);
        sb.append(", myAnswer='").append(myAnswer).append('\'');
        sb.append(", labourId=").append(labourId);
        sb.append(", answerStatus='").append(answerStatus).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
