package com.guojunjie.springbootblog.controller.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guojunjie
 */
@Data
@ToString
public class BlogCategoryVo implements Serializable{
    private Integer categoryId;
    private String categoryName;
    private int blogCount;
}
