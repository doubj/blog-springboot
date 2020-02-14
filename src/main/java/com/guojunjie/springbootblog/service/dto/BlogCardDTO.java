package com.guojunjie.springbootblog.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Date： 2020/2/4 14:55
 * @author： guojunjie
 * TODO BlogCard
 */
@Data
@ToString
public class BlogCardDTO{
    private Integer blogId;

    private String blogTitle;

    private String blogCoverImage;

    private Integer blogCategoryId;

    private String blogCategoryName;

    private String blogSummary;

    private String blogTags;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
