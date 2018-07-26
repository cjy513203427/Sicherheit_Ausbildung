package com.xgt.entity;

import com.xgt.common.BaseController;
import com.xgt.common.PageQueryEntity;

import java.io.Serializable;

/**
 * @author cjy
 * @Date 2018/6/6 10:57.
 */
public class EduQuestionAnswer  implements Serializable{
    //主键id
    private Integer id;
    //题目id
    private Integer questionId;
    //选项编码（eg: A、B、C）
    private String optionCode;
    //选项内容
    private String optionContent;
    //是否正确（1：正确、2：错误）
    private String correctFlag;
    //创建时间
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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

    public String getCorrectFlag() {
        return correctFlag;
    }

    public void setCorrectFlag(String correctFlag) {
        this.correctFlag = correctFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
