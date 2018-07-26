package com.xgt.dto;

import java.util.List;

public class EduQuestionDto {
    private Integer index;
    //主键id
    private Integer id;
    //题目标题
    private String title;
    //选项
    private String optionCode;
    //选项内容
    private String optionContent;
    //选项和选项内容
    private List<EduQuestionDto> codeContent;
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

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<EduQuestionDto> getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(List<EduQuestionDto> codeContent) {
        this.codeContent = codeContent;
    }
}
