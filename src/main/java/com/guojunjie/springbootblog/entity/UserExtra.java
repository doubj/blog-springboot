package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class UserExtra {
    private Integer uid;

    private String nickName;

    private String avatar;

    private String introduce;
}