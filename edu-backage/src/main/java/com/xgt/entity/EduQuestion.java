package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 题目 实体类
 *
 * @author cjy
 * @Date 2018/6/1 19:24.
 */
public class EduQuestion extends PageQueryEntity {
    //主键id
    private Integer id;
    //题库id
    private Integer questionBankId;
    //题干
    private String title;
    //题目类型（1：单选、2：判断、3：多选）
    private String questionType;
    //创建人id
    private Integer createUserId;
    //创建时间
    private String createTime;
    //删除状态（1：未删除、2：已删除）
    private Integer deleteStatus;
    //答案解析
    private String answerAnalysis;
    // 难易程度（1：简单、2：一般、3：困难）
    private String difficultyDegree;

    // 课程名称
    private String chapterContentTitle;


    //创建人名称
    private String realName;
    //正确答案
    private String trueAnswer;
    //我的答案
    private String myAnswer;
    //试卷名称
    private String examName;
    //答案集合
    private List<String> myAnswerList;
    //拼接答案
    private String answerString ;
    //考试剩余时间
    private Integer hasedTime;
    //题目对应视频地址
    private String questionVideoPath ;
    //课程id
    private Integer chapterContentId ;


    public String getChapterContentTitle() {
        return chapterContentTitle;
    }

    public void setChapterContentTitle(String chapterContentTitle) {
        this.chapterContentTitle = chapterContentTitle;
    }

    public Integer getHasedTime() {
        return hasedTime;
    }

    public void setHasedTime(Integer hasedTime) {
        this.hasedTime = hasedTime;
    }

    private boolean collectFlag;

    public boolean isCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(boolean collectFlag) {
        this.collectFlag = collectFlag;
    }

    public List<String> getMyAnswerList() {
        return myAnswerList;
    }

    public void setMyAnswerList(List<String> myAnswerList) {
        this.myAnswerList = myAnswerList;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    private List<EduQuestionAnswer> answers;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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

    public String getAnswerAnalysis() {
        return answerAnalysis;
    }

    public void setAnswerAnalysis(String answerAnalysis) {
        this.answerAnalysis = answerAnalysis;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<EduQuestionAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<EduQuestionAnswer> answers) {
        this.answers = answers;
    }

    public String getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(String difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }

    public String getQuestionVideoPath() {
        return questionVideoPath;
    }

    public void setQuestionVideoPath(String questionVideoPath) {
        this.questionVideoPath = questionVideoPath;
    }

    public Integer getChapterContentId() {
        return chapterContentId;
    }

    public void setChapterContentId(Integer chapterContentId) {
        this.chapterContentId = chapterContentId;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }
}
