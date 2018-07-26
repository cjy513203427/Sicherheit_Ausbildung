package com.xgt.enums;

public enum EnumPassStatus {
    /**
     * sys enum config 题目类型（1：通过、2：不通过）
     */
    PASS("1", "通过"),

    NOTPASS("2", "不通过"),

    ;

    public final String code;

    public final String desc;

    private EnumPassStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumPassStatus enumOfByCode(String code) {
        for (EnumPassStatus returnCode : values()) {
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
    public static EnumPassStatus enumOfByDesc(String desc) {
        for (EnumPassStatus returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }

}
