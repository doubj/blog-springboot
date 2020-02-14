package com.guojunjie.springbootblog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author guojunjie
 */
@Data
@ToString
public class FriendLink {
    private Integer linkId;

    private String linkName;

    private String linkUrl;

    private String avatar;
}