package com.guojunjie.springbootblog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guojunjie.springbootblog.entity.Blog;

import java.io.Serializable;
import java.util.Date;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDetailVo implements Serializable {
    private Integer blogId;

    private String blogTitle;

    private String blogCoverImage;

    private Integer blogCategoryId;

    private String blogCategoryName;

    private String blogTags;

    private Byte blogStatus;

    private Long blogVisits;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private Blog previousBlog;

    private Blog nextBlog;

    private String blogContent;

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle == null ? null : blogTitle.trim();
    }

    public String getBlogCoverImage() {
        return blogCoverImage;
    }

    public void setBlogCoverImage(String blogCoverImage) {
        this.blogCoverImage = blogCoverImage == null ? null : blogCoverImage.trim();
    }

    public Integer getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(Integer blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }

    public String getBlogCategoryName() {
        return blogCategoryName;
    }

    public void setBlogCategoryName(String blogCategoryName) {
        this.blogCategoryName = blogCategoryName == null ? null : blogCategoryName.trim();
    }

    public String getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(String blogTags) {
        this.blogTags = blogTags == null ? null : blogTags.trim();
    }

    public Byte getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(Byte blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Long getBlogVisits() {
        return blogVisits;
    }

    public void setBlogVisits(Long blogVisits) {
        this.blogVisits = blogVisits;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent == null ? null : blogContent.trim();
    }

    public Blog getPreviousBlog() {
        return previousBlog;
    }

    public void setPreviousBlog(Blog previousBlog) {
        this.previousBlog = previousBlog;
    }

    public Blog getNextBlog() {
        return nextBlog;
    }

    public void setNextBlog(Blog nextBlog) {
        this.nextBlog = nextBlog;
    }
}
