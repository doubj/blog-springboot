package com.guojunjie.springbootblog.common;

import lombok.Data;
import lombok.ToString;

/**
 * @Date： 2020/4/6 11:50
 * @author： guojunjie
 * TODO
 */
@Data
@ToString
public class BlogListQueryAdmin {
    private int page;
    private int limit;
    private String title;
    private int blogCategoryId;
    /**
     * '+id'顺序，'-id'逆序Id排序
     */
    private String sort;
    private boolean deleted;
}
