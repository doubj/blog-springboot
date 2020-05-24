package com.guojunjie.springbootblog.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guojunjie.springbootblog.entity.Blog;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Date： 2020/2/4 14:21
 * @author： guojunjie
 * TODO 博客页面的blog详细数据
 */

@Data
public class BlogDetailDTO {
    private Integer blogId;
    private String blogTitle;
    private String blogCoverImage;
    private Integer blogCategoryId;
    private String blogCategoryName;
    private String blogTags;
    private String blogStatus;
    private Long blogVisits;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    private BlogCardDTO previousBlog;
    private BlogCardDTO nextBlog;
    private String blogContent;
}
