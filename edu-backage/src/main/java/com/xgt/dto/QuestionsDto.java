package com.xgt.dto;

import java.util.List;

/**
 * Created by HeLiu on 2018/7/17.
 * 导出word查询题目信息数据传输对象
 */
public class QuestionsDto {

    private Integer questionId;

    private String titleName;

    private String trueAnswer;

    private List<OptionsDto> options;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<OptionsDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsDto> options) {
        this.options = options;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
