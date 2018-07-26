package com.xgt.enums;

/**
 * 同步类型
 */
public enum EnumSyncType {
    /**
     * sys enum config
     */
    VIDEO("1", "视频同步"),

    IMGTXT("2", "图文知识库同步"),

    Labour("3","学员信息") ,

    VIDEO_QUESTION("4","视频题目")

    ;

    public final String code;

    public final String desc;

    EnumSyncType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *  根据 code 获得枚举
     * @param code
     * @return enum pcs
     */
    public static EnumSyncType enumOfByCode(String code) {
        for (EnumSyncType returnCode : values()) {
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
    public static EnumSyncType enumOfByDesc(String desc) {
        for (EnumSyncType returnCode : values()) {
            if (returnCode.desc.equals(desc)) {
                return returnCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + desc + "]");
    }

}
