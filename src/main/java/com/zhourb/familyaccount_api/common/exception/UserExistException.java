package com.zhourb.familyaccount_api.common.exception;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/16 14:09
 * @description 账号已存在异常
 **/


public class UserExistException extends RuntimeException {

    public String getMessage;

    public UserExistException() {
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistException(Throwable cause) {
        super(cause);
    }

    public UserExistException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getGetMessage() {
        return getMessage;
    }

    public void setGetMessage(String getMessage) {
        this.getMessage = getMessage;
    }
}
