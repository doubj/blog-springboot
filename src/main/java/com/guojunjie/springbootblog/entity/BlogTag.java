package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class BlogTag {
    private Integer tagId;

    private String tagName;
}