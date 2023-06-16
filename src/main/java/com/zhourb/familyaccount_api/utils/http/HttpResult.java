package com.zhourb.familyaccount_api.utils.http;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/11 13:25
 * @description 返回前端结果封装
 **/
public class HttpResult {
    private int code = HttpStatus.SC_OK;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static HttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static HttpResult error(String message) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, message);
    }

    public static HttpResult error(int code, String message) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static HttpResult ok(String message) {
        HttpResult r = new HttpResult();
        r.setMessage(message);
        return r;
    }

    public static HttpResult ok(Object data) {
        HttpResult r = new HttpResult();
        r.setMessage("success");
        r.setData(data);
        return r;
    }

    public static HttpResult ok() {
        return new HttpResult();
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
