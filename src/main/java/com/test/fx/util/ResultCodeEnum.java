package com.test.fx.util;


import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
public enum ResultCodeEnum {

    SUCCESS(true, 20000, "成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),

    USER_NAME_REPEAT(false, 23001, "Please enter the correct password.");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}