package com.guojunjie.springbootblog.service.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Date： 2020/2/4 21:49
 * @author： guojunjie
 * TODO
 */
@Data
@ToString
public class UserDetail {
    private Integer userId;

    private String userName;

    private String password;

    private String nickName;

    private String avatar;

    private String introduce;
}
