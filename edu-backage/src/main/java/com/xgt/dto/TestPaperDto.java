package com.xgt.dto;

import java.util.List;
/*
    试卷DTO
 */
public class TestPaperDto {
    //答题list
    private List<UserDto> answerList;
    //正确答案list
    private List<AnswerKeyDto> answerKeyList;
    //单选题list
    private List<EduQuestionDto> eduQuestionDtoSingleTitleList;
    //多选题list
    private List<EduQuestionDto> eduQuestionDtoMultiTitleList;

    public List<UserDto> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<UserDto> answerList) {
        this.answerList = answerList;
    }

    public List<AnswerKeyDto> getAnswerKeyList() {
        return answerKeyList;
    }

    public void setAnswerKeyList(List<AnswerKeyDto> answerKeyList) {
        this.answerKeyList = answerKeyList;
    }

    public List<EduQuestionDto> getEduQuestionDtoSingleTitleList() {
        return eduQuestionDtoSingleTitleList;
    }

    public void setEduQuestionDtoSingleTitleList(List<EduQuestionDto> eduQuestionDtoSingleTitleList) {
        this.eduQuestionDtoSingleTitleList = eduQuestionDtoSingleTitleList;
    }

    public List<EduQuestionDto> getEduQuestionDtoMultiTitleList() {
        return eduQuestionDtoMultiTitleList;
    }

    public void setEduQuestionDtoMultiTitleList(List<EduQuestionDto> eduQuestionDtoMultiTitleList) {
        this.eduQuestionDtoMultiTitleList = eduQuestionDtoMultiTitleList;
    }
}
