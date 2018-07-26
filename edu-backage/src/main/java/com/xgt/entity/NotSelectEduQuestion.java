package com.xgt.entity;

import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * 考试未选择的题目 实体类
 * @author CC
 * @Date 2018/6/8 14:24.
 */
public class NotSelectEduQuestion extends PageQueryEntity implements Serializable{
   private Integer examId;

   private Integer examType;

   private String questionType;

   private String questionTitle;

   private String bankTitle;

   public Integer getExamId() {
      return examId;
   }

   public void setExamId(Integer examId) {
      this.examId = examId;
   }

   public Integer getExamType() {
      return examType;
   }

   public void setExamType(Integer examType) {
      this.examType = examType;
   }

   public String getQuestionType() {
      return questionType;
   }

   public void setQuestionType(String questionType) {
      this.questionType = questionType;
   }

   public String getQuestionTitle() {
      return questionTitle;
   }

   public void setQuestionTitle(String questionTitle) {
      this.questionTitle = questionTitle;
   }

   public String getBankTitle() {
      return bankTitle;
   }

   public void setBankTitle(String bankTitle) {
      this.bankTitle = bankTitle;
   }
}
