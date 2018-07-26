package com.xgt.dto;


import java.util.List;

/**
 * Created by HeLiu on 2018/7/17.
 * 导出word试卷信息数据传输对象
 */
public class ExamDto {

    private String title;

    private List<QuestionsDto> unitList;

    private List<QuestionsDto> duoList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionsDto> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<QuestionsDto> unitList) {
        this.unitList = unitList;
    }

    public List<QuestionsDto> getDuoList() {
        return duoList;
    }

    public void setDuoList(List<QuestionsDto> duoList) {
        this.duoList = duoList;
    }
}
