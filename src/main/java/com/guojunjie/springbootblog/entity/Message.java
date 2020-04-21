package com.guojunjie.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Date： 2020/4/20 14:47
 * @author： guojunjie
 * TODO
 */
@Data
public class Message {
    private Integer messageId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
