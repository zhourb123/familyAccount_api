package com.zhourb.familyaccount_api.common.exception;


/**
 * @author ZhouGuiMing
 * @version 1.0
 * @date 2022/4/28 13:30
 * @description 日志添加失败异常
 **/
public class MyLogException extends Exception{

    public String getMessage;

    public MyLogException() {
        super();
    }

    public MyLogException(String message) {
        super(message);
    }

    public MyLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyLogException(Throwable cause) {
        super(cause);
    }

    protected MyLogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getGetMessage() {
        return getMessage;
    }

    public void setGetMessage(String getMessage) {
        this.getMessage = getMessage;
    }
}
