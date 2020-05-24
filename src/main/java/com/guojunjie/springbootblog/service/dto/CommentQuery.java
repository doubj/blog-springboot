package com.guojunjie.springbootblog.service.dto;

import lombok.Data;

/**
 * @Date： 2020/5/11 0:44
 * @author： guojunjie
 */
@Data
public class CommentQuery {
    private int page;
    private int limit;
    private String type;
    private String nickName;
}
