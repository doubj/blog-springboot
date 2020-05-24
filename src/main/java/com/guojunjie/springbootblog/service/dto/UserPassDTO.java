package com.guojunjie.springbootblog.service.dto;

import lombok.Data;

/**
 * @Date： 2020/5/10 20:26
 * @author： guojunjie
 */
@Data
public class UserPassDTO {
    private String oldPass;
    private String newPass;
}
