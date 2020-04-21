package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class BlogCategory {
    private Integer categoryId;
    private String categoryName;
}