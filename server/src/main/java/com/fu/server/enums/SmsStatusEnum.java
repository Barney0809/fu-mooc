package com.fu.server.enums;

public enum SmsStatusEnum {
    USED("U", "已使用"),
    NOT_USED("N", "未使用");

    private String code;

    private String desc;

    SmsStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
