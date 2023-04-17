package com.hncboy.chatgpt.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 账号状态
 */
public enum GptUserStatusEnum {

   ACTIVED(0,"正常"),BLOCKED_LIMIT(1,"限时封禁"),BLOCKED_FOREVER(2,"永久封禁");

    @EnumValue
   private int code;
    @JsonValue
   private String label;
    GptUserStatusEnum(int code, String label) {
        this.code=code;
        this.label=label;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
