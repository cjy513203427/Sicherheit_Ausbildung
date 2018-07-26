package com.xgt.enums;

/**
 * @Auther: 谷天乐
 * @Date: 2018/7/20 10:55
 * @Description:
 */
public enum EnumAttachmentType {
    /**
     * sys enum config
     */
    CERTIFICATE("1", "证书"),

    MEDICAL_REPORT("2", "体检报告"),

    LIABILITY_FORMS("3","安全责任状"),

    LABOUR_CONTRACT("4","劳务合同"),

    IDCARD_SCAN("5","身份证扫描件")
    ;

    public final String code;

    public final String desc;

    private EnumAttachmentType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumAttachmentType enumOfByCode(String code) {
        for (EnumAttachmentType returnCode : values()) {
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
    public static EnumAttachmentType enumOfByDesc(String desc) {
        for (EnumAttachmentType returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }
}
