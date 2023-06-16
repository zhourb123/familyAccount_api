package com.zhourb.familyaccount_api.common.aspect.log;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/3/10 17:30
 * @description：操作日志实体
 **/
public class LogOperationEntity {
    // id
    private String operId;
    // 功能模块
    private String operModul;
    // 操作类型
    private String operType;
    // 操作描述
    private String operDesc;
    private String operRequParam;
    private String operRequHeader;
    private String operUserName;
    private String operMethod;
    private String operMethodType;
    private String operRespParam;
    private String operUri;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operCreateTime;

    private Integer page = 1;
    private Integer size = 10;

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperModul() {
        return operModul;
    }

    public void setOperModul(String operModul) {
        this.operModul = operModul;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
    }

    public String getOperRequParam() {
        return operRequParam;
    }

    public void setOperRequParam(String operRequParam) {
        this.operRequParam = operRequParam;
    }

    public String getOperRespParam() {
        return operRespParam;
    }

    public void setOperRespParam(String operRespParam) {
        this.operRespParam = operRespParam;
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
        return "LogOperationEntity{" +
                "operId='" + operId + '\'' +
                ", operModul='" + operModul + '\'' +
                ", operType='" + operType + '\'' +
                ", operDesc='" + operDesc + '\'' +
                ", operRequParam='" + operRequParam + '\'' +
                ", operRequHeader='" + operRequHeader + '\'' +
                ", operUserName='" + operUserName + '\'' +
                ", operMethod='" + operMethod + '\'' +
                ", operMethodType='" + operMethodType + '\'' +
                ", operUri='" + operUri + '\'' +
                ", operCreateTime=" + operCreateTime +
                ", operRespParam='" + operRespParam + '\'' +
                '}';
    }
}
