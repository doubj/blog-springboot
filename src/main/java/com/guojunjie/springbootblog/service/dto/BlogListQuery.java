package com.guojunjie.springbootblog.service.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Date： 2020/4/12 14:51
 * @author： guojunjie
 * TODO
 */
@Data
@ToString
public class BlogListQuery {
    private int page;
    private int limit;
    private String blogCategoryName;
    private String[] blogTags;
}
