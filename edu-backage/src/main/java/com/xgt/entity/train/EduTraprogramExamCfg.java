package com.xgt.entity.train;


import org.apache.log4j.Logger;

/**
 *  培训自动组卷 ，所需配置表
 * @author liuao
 * @date 2018/6/27 9:32
 */
public class EduTraprogramExamCfg {

    //主键id
    private Integer id;
    // 培训方案id
    private Integer trainProgramId;
    // 简单-单选-总题数
    private Integer singleSimpleTotal	;
    // 简单-单选-选中题数
    private Integer singleSimpleSelect ;
    // 简单-单选-分数权重
    private Integer singleSimpleRatio	;
    // 中等-单选-总题数
    private Integer singleMediumTotal	;
    // 中等-单选-选中题数
    private Integer singleMediumSelect	;
    // 中等-单选-分数权重
    private Integer singleMediumRatio ;
    // 困难-单选-总题数
    private Integer singleDifficultyTotal;
    // 困难-单选-选中题数
    private Integer singleDifficultySelect ;
    // 困难-单选-分数权重
    private Integer singleDifficultyRatio;
    // 简单-多选-总题数
    private Integer multiSimpleTotal	;
    // 简单-多选-选中题数
    private Integer multiSimpleSelect	;
    // 简单-多选-分数权重
    private Integer multiSimpleRatio	;
    // 中等-多选-总题数
    private Integer multiMediumTotal	;
    // 中等-多选-选中题数
    private Integer multiMediumSelect ;
    // 中等-多选-分数权重
    private Integer multiMediumRatio ;
    // 困难-多选-总题数
    private Integer multiDifficultyTotal ;
    // 困难-多选-选中题数
    private Integer multiDifficultySelect	;
    // 困难-多选-分数权重
    private Integer multiDifficultyRatio	;

    //题目总数
    private Integer questionAmount ;

    public EduTraprogramExamCfg(){
        this.singleSimpleTotal = 0 ;
        this.singleMediumTotal = 0 ;
        this.multiSimpleTotal = 0 ;
        this.multiMediumTotal = 0 ;
        this.singleDifficultyTotal = 0 ;
        this.multiDifficultyTotal = 0 ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Integer trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    public Integer getSingleSimpleTotal() {
        return singleSimpleTotal;
    }

    public void setSingleSimpleTotal(Integer singleSimpleTotal) {
        this.singleSimpleTotal = singleSimpleTotal==null?0:singleSimpleTotal;
    }

    public Integer getSingleSimpleSelect() {
        return singleSimpleSelect;
    }

    public void setSingleSimpleSelect(Integer singleSimpleSelect) {
        this.singleSimpleSelect = singleSimpleSelect;
    }

    public Integer getSingleSimpleRatio() {
        return singleSimpleRatio;
    }

    public void setSingleSimpleRatio(Integer singleSimpleRatio) {
        this.singleSimpleRatio = singleSimpleRatio;
    }

    public Integer getSingleMediumTotal() {
        return singleMediumTotal;
    }

    public void setSingleMediumTotal(Integer singleMediumTotal) {
        this.singleMediumTotal = singleMediumTotal==null?0:singleMediumTotal;
    }

    public Integer getSingleMediumSelect() {
        return singleMediumSelect;
    }

    public void setSingleMediumSelect(Integer singleMediumSelect) {
        this.singleMediumSelect = singleMediumSelect;
    }

    public Integer getSingleMediumRatio() {
        return singleMediumRatio;
    }

    public void setSingleMediumRatio(Integer singleMediumRatio) {
        this.singleMediumRatio = singleMediumRatio;
    }

    public Integer getSingleDifficultyTotal() {
        return singleDifficultyTotal;
    }

    public void setSingleDifficultyTotal(Integer singleDifficultyTotal) {
        this.singleDifficultyTotal = singleDifficultyTotal==null?0:singleDifficultyTotal;
    }

    public Integer getSingleDifficultySelect() {
        return singleDifficultySelect;
    }

    public void setSingleDifficultySelect(Integer singleDifficultySelect) {
        this.singleDifficultySelect = singleDifficultySelect;
    }

    public Integer getSingleDifficultyRatio() {
        return singleDifficultyRatio;
    }

    public void setSingleDifficultyRatio(Integer singleDifficultyRatio) {
        this.singleDifficultyRatio = singleDifficultyRatio;
    }

    public Integer getMultiSimpleTotal() {
        return multiSimpleTotal;
    }

    public void setMultiSimpleTotal(Integer multiSimpleTotal) {
        this.multiSimpleTotal = multiSimpleTotal==null?0:multiSimpleTotal;
    }

    public Integer getMultiSimpleSelect() {
        return multiSimpleSelect;
    }

    public void setMultiSimpleSelect(Integer multiSimpleSelect) {
        this.multiSimpleSelect = multiSimpleSelect;
    }

    public Integer getMultiSimpleRatio() {
        return multiSimpleRatio;
    }

    public void setMultiSimpleRatio(Integer multiSimpleRatio) {
        this.multiSimpleRatio = multiSimpleRatio;
    }

    public Integer getMultiMediumTotal() {
        return multiMediumTotal;
    }

    public void setMultiMediumTotal(Integer multiMediumTotal) {
        this.multiMediumTotal = multiMediumTotal==null?0:multiMediumTotal;
    }

    public Integer getMultiMediumSelect() {
        return multiMediumSelect;
    }

    public void setMultiMediumSelect(Integer multiMediumSelect) {
        this.multiMediumSelect = multiMediumSelect;
    }

    public Integer getMultiMediumRatio() {
        return multiMediumRatio;
    }

    public void setMultiMediumRatio(Integer multiMediumRatio) {
        this.multiMediumRatio = multiMediumRatio;
    }

    public Integer getMultiDifficultyTotal() {
        return multiDifficultyTotal;
    }

    public void setMultiDifficultyTotal(Integer multiDifficultyTotal) {
        this.multiDifficultyTotal = multiDifficultyTotal==null?0:multiDifficultyTotal;
    }

    public Integer getMultiDifficultySelect() {
        return multiDifficultySelect;
    }

    public void setMultiDifficultySelect(Integer multiDifficultySelect) {
        this.multiDifficultySelect = multiDifficultySelect;
    }

    public Integer getMultiDifficultyRatio() {
        return multiDifficultyRatio;
    }

    public void setMultiDifficultyRatio(Integer multiDifficultyRatio) {
        this.multiDifficultyRatio = multiDifficultyRatio;
    }

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EduTraprogramExamCfg{");
        sb.append("id=").append(id);
        sb.append(", trainProgramId=").append(trainProgramId);
        sb.append(", singleSimpleTotal=").append(singleSimpleTotal);
        sb.append(", singleSimpleSelect=").append(singleSimpleSelect);
        sb.append(", singleSimpleRatio=").append(singleSimpleRatio);
        sb.append(", singleMediumTotal=").append(singleMediumTotal);
        sb.append(", singleMediumSelect=").append(singleMediumSelect);
        sb.append(", singleMediumRatio=").append(singleMediumRatio);
        sb.append(", singleDifficultyTotal=").append(singleDifficultyTotal);
        sb.append(", singleDifficultySelect=").append(singleDifficultySelect);
        sb.append(", singleDifficultyRatio=").append(singleDifficultyRatio);
        sb.append(", multiSimpleTotal=").append(multiSimpleTotal);
        sb.append(", multiSimpleSelect=").append(multiSimpleSelect);
        sb.append(", multiSimpleRatio=").append(multiSimpleRatio);
        sb.append(", multiMediumTotal=").append(multiMediumTotal);
        sb.append(", multiMediumSelect=").append(multiMediumSelect);
        sb.append(", multiMediumRatio=").append(multiMediumRatio);
        sb.append(", multiDifficultyTotal=").append(multiDifficultyTotal);
        sb.append(", multiDifficultySelect=").append(multiDifficultySelect);
        sb.append(", multiDifficultyRatio=").append(multiDifficultyRatio);
        sb.append('}');
        return sb.toString();
    }
}
