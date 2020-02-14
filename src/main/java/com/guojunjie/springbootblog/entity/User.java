package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class User {
    private Integer userId;

    private String userName;

    private String password;
}