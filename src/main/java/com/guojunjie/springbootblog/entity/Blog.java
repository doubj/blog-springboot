package com.guojunjie.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author guojunjie
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Blog {
    private Integer blogId;

    private String blogTitle;

    private String blogCoverImage;

    private Integer blogCategoryId;

    private String blogCategoryName;

    private String blogSummary;

    private String blogTags;

    private String blogStatus;

    private Long blogVisits;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String blogContent;

    private Integer newCommentCount;
}