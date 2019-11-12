package com.guojunjie.springbootblog.common;

import java.io.Serializable;
import java.util.List;

public class PageInfo {

    private List data;
    //当前页
    private int currentPage;
    //总页数
    private int totalPage;
    //每页记录数
    private int pageSize;
    //总记录数
    private int totalCount;

    public PageInfo(List data, int currentPage, int totalPage, int pageSize, int totalCount) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
