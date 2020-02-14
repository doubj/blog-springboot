package com.guojunjie.springbootblog.common;

/**
 * @author guojunjie
 */
public class PageQueryUtil {

    private int currentPage;
    private int totalCount;
    private int pageSize;
    private int totalPage;

    public PageQueryUtil(int currentPage, int totalCount) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = 3;
    }

    public int getStart(){
        return (currentPage - 1) * pageSize;
    }

    public int getTotalPage(){
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getLimit(){return this.pageSize;}
}
