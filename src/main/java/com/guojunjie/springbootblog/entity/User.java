package com.guojunjie.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String nickName;

    private String avatar;

    private String introduce;

    private String roles;
}