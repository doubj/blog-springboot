package com.guojunjie.springbootblog.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guojunjie.springbootblog.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Date： 2020/5/5 12:17
 * @author： guojunjie
 */
@Data
public class CommentItemDTO {
    private Integer commentId;
    private String content;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private List<CommentSubItemDTO> children;
}
