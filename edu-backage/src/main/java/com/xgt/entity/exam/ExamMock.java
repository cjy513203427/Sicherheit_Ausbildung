package com.xgt.entity.exam;

/**
 * 模拟测试实体类
 *
 * @author liuao
 * @date 2018/6/5 17:19
 */
public class ExamMock {


    // 主键id
    private Integer id;
    // 试卷名称
    private String examName;
    // 题目数量
    private Integer questionNumber;
    // 总分
    private Integer totalScore;
    //及格分
    private Integer passScore;
    // 耗时（单位：分钟）
    private Integer consumeMinute;
    // 岗位(1:管理人员、2：作业人员、3：普通工人、9：其他)
    private String postType;
    private String createTime;

    //创建人ID
    private Integer createUserId;

    /**
     * 查询题目列表字段
     */
    //题库ID
    private Integer bankId;
    //题库标题
    private String bankTitle;
    //题目ID
    private Integer questionId;
    //题目标题
    private String questionTitle;
    //题目类型（1：单选、2：判断、3：多选）
    private String questionType;


    //是否通过状态值
    //1：通过、2：不通过
    private String passStatus;

    //得分
    private Integer examScore;

    public Integer getExamScore() {
        return examScore;
    }

    public void setExamScore(Integer examScore) {
        this.examScore = examScore;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankTitle() {
        return bankTitle;
    }

    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getConsumeMinute() {
        return consumeMinute;
    }

    public void setConsumeMinute(Integer consumeMinute) {
        this.consumeMinute = consumeMinute;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }
}
