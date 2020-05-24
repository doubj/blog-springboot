package com.guojunjie.springbootblog.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Date： 2020/5/5 12:17
 * @author： guojunjie
 */
@Data
public class CommentSubItemDTO {
    private Integer commentId;
    private String content;
    private String nickName;
    private String replyNickName;
    private Boolean replyAdmin;
    private Boolean beRepliedAdmin;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
