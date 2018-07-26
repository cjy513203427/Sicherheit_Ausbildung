package com.xgt.entity.exam;

/**
 * 工人模拟试卷考试实体类
 */
public class ExamLabourerRel {

    private Integer id;
    //工人id
    private Integer labourerId;
    //试卷id
    private Integer examId;
    //试卷类型（1：模拟试卷、2：正式考试）
    private String examType;
    //考试得分
    private Integer examScore;
    //是否通过（1：通过、2：不通过）
    private String passStatus;
    //实际考试耗时（单位：秒），这里需要精确知道用时，所以设置单位为秒
    private Integer consumeTime;
    //交卷时间
    private String completeTime;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //题目数量
    private Integer questionNumber;
    //正确题数
    private Integer trueQuestionCount;
    //用时
    private String strConsumeTime;
    //考试类型为3的时候，即培训考试时的方案id
    private Integer trainProgramId;

    public String getStrConsumeTime() {
        return strConsumeTime;
    }

    public void setStrConsumeTime(String strConsumeTime) {
        this.strConsumeTime = strConsumeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabourerId() {
        return labourerId;
    }

    public void setLabourerId(Integer labourerId) {
        this.labourerId = labourerId;
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

    public Integer getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Integer consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
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

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getTrueQuestionCount() {
        return trueQuestionCount;
    }

    public void setTrueQuestionCount(Integer trueQuestionCount) {
        this.trueQuestionCount = trueQuestionCount;
    }

    public Integer getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Integer trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExamLabourerRel{");
        sb.append("id=").append(id);
        sb.append(", labourerId=").append(labourerId);
        sb.append(", examId=").append(examId);
        sb.append(", examType='").append(examType).append('\'');
        sb.append(", examScore=").append(examScore);
        sb.append(", passStatus='").append(passStatus).append('\'');
        sb.append(", consumeTime=").append(consumeTime);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append(", questionNumber=").append(questionNumber);
        sb.append(", trueQuestionCount=").append(trueQuestionCount);
        sb.append(", strConsumeTime='").append(strConsumeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
