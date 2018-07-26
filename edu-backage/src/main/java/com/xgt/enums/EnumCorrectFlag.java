package com.xgt.enums;

/**
 * all service error code config
 */
public enum EnumCorrectFlag {
    /**
     * sys enum config
     */
    RIGHT("1", "对"),

    WRONG("2", "错"),
    ;

    public final String code;

    public final String desc;

    private EnumCorrectFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumCorrectFlag enumOfByCode(String code) {
        for (EnumCorrectFlag returnCode : values()) {
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
    public static EnumCorrectFlag enumOfByDesc(String desc) {
        for (EnumCorrectFlag returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }

}
