package com.xgt.enums;

/**
 * all service error code config
 */
public enum EnumQuestionType {
    /**
     * sys enum config 题目类型（1：单选、2：判断、3：多选）
     */
    SINGLE("1", "单选"),

    JUDGE("2", "判断"),

    MULTI_SELECTION("3", "多选"),
    ;

    public final String code;

    public final String desc;

    private EnumQuestionType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumQuestionType enumOfByCode(String code) {
        for (EnumQuestionType returnCode : values()) {
            if (code.equals(returnCode.code)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    /**
     *  根据desc 获得枚举
     * @param desc
     * @return enum pcs
     */
    public static EnumQuestionType enumOfByDesc(String desc) {
        for (EnumQuestionType returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }

}
