package com.zhourb.familyaccount_api.utils.http;

import java.util.List;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/16 18:26
 * @description 分页类
 **/
public class PageHelperEntity<T> {
    private Long total;
    private Integer pages;
    private List<T> content;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PageHelperEntity{" +
                "total=" + total +
                ", pages=" + pages +
                ", content=" + content +
                '}';
    }
}
