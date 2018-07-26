package com.xgt.enums;

public enum EnumDifficultyDegree {

    /**
     *  1：简单、2：一般、3：困难
     * @author liuao
     * @date 2018/6/15 10:48
     */
    SIMPLE("1", "简单"),

    MEDIUM("2", "一般"),

    DIFFICULTY("3", "困难"),
            ;

    public final String code;

    public final String desc;


    private EnumDifficultyDegree(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumDifficultyDegree enumOfByCode(String code) {
        for (EnumDifficultyDegree returnCode : values()) {
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
    public static EnumDifficultyDegree enumOfByDesc(String desc) {
        for (EnumDifficultyDegree returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }
}
