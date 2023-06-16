package com.zhourb.familyaccount_api.common.exception;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/21 8:52
 * @description 数据已存在异常
 **/
public class DataExistException extends Exception {
    public String getMessage;

    public DataExistException() {
    }

    public DataExistException(String message) {
        super(message);
    }

    public DataExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataExistException(Throwable cause) {
        super(cause);
    }

    public DataExistException(String message, Throwable cause, boolean enableSuppression,
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
