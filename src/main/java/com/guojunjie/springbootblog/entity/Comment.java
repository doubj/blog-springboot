package com.guojunjie.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.guojunjie.springbootblog.service.dto.CommentItemDTO;
import com.guojunjie.springbootblog.service.dto.CommentSubItemDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Date： 2020/5/5 10:54
 * @author： guojunjie
 */
@Data
public class Comment {
    private Integer commentId;
    private String content;
    private String nickName;
    private String email;
    private String replyNickName;
    private boolean replyAdmin;
    private boolean beRepliedAdmin;
    private String type;
    private Integer relationId;
    private Integer pid;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private List<CommentSubItemDTO> children;
}
