package com.guojunjie.springbootblog.controller.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guojunjie
 */
@Data
@ToString
public class BlogTagVo implements Serializable {
    private int tagId;
    private String tagName;
    private int blogCount;
}
