package com.xgt.enums;

public enum EnumSexStatus {
    /**
     * sys enum config 性别类型（1：男、2：女、3：其他）
     */
    MAN("1", "男"),

    WOMAN("2", "女"),

    OTHER("3", "其他"),

            ;

    public final String code;

    public final String desc;

    private EnumSexStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumSexStatus enumOfByCode(String code) {
        for (EnumSexStatus returnCode : values()) {
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
    public static EnumSexStatus enumOfByDesc(String desc) {
        for (EnumSexStatus returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }
}
