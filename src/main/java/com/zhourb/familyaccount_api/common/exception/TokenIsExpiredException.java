package com.zhourb.familyaccount_api.common.exception;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/14 11:27
 * @description jwt过期异常
 **/
public class TokenIsExpiredException extends Exception {

    private static final long serialVersionUID = 1L;

    public TokenIsExpiredException() {
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
