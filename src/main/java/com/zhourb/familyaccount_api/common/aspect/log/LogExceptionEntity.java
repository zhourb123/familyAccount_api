package com.zhourb.familyaccount_api.common.aspect.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/3/10 17:28
 * @description 异常日志操作对象
 **/
public class LogExceptionEntity {
    private String excId;
    private String excRequParam;
    private String operRequHeader;
    private String operUserName;
    private String operMethod;
    private String operMethodType;
    private String operUri;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operCreateTime;
    private String excName;
    private String excMessage;

    private Integer page = 1;
    private Integer size = 10;

    public String getExcId() {
        return excId;
    }

    public void setExcId(String excId) {
        this.excId = excId;
    }

    public String getExcRequParam() {
        return excRequParam;
    }

    public void setExcRequParam(String excRequParam) {
        this.excRequParam = excRequParam;
    }

    public String getExcName() {
        return excName;
    }

    public void setExcName(String excName) {
        this.excName = excName;
    }

    public String getExcMessage() {
        return excMessage;
    }

    public void setExcMessage(String excMessage) {
        this.excMessage = excMessage;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getOperMethod() {
        return operMethod;
    }

    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    public String getOperUri() {
        return operUri;
    }

    public void setOperUri(String operUri) {
        this.operUri = operUri;
    }

    public Date getOperCreateTime() {
        return operCreateTime;
    }

    public void setOperCreateTime(Date operCreateTime) {
        this.operCreateTime = operCreateTime;
    }

    public String getOperMethodType() {
        return operMethodType;
    }

    public void setOperMethodType(String operMethodType) {
        this.operMethodType = operMethodType;
    }

    public String getOperRequHeader() {
        return operRequHeader;
    }

    public void setOperRequHeader(String operRequHeader) {
        this.operRequHeader = operRequHeader;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "LogExceptionEntity{" +
                "excId='" + excId + '\'' +
                ", excRequParam='" + excRequParam + '\'' +
                ", operRequHeader='" + operRequHeader + '\'' +
                ", operUserName='" + operUserName + '\'' +
                ", operMethod='" + operMethod + '\'' +
                ", operMethodType='" + operMethodType + '\'' +
                ", operUri='" + operUri + '\'' +
                ", operCreateTime=" + operCreateTime +
                ", excName='" + excName + '\'' +
                ", excMessage='" + excMessage + '\'' +
                '}';
    }
}
