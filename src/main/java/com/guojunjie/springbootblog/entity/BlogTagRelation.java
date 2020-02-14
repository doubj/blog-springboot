package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class BlogTagRelation {
    private Integer relationId;

    private Integer blogId;

    private Integer tagId;
}