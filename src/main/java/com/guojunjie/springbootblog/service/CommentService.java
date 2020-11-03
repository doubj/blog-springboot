package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.dto.CommentQuery;

import java.util.List;
import java.util.Map;

/**
 * @Date： 2020/5/5 11:23
 * @author： guojunjie
 */
public interface CommentService {
    /**
     * 添加一项评论
     * @param comment
     */
    void addComment(Comment comment);


    /**
     * 后台获取留言列表
     * @param commentQuery
     * @return
     */
    Map<String, Object> getCommentByQuery(CommentQuery commentQuery, Boolean reset);

    /**
     * 删除评论
     * @param id
     */
    void deleteComment(int id);

    /**
     * 获取除博主回复的所有评论数
     * @return
     */
    int getCommentCount();

    void modifyCheckedState(List<Comment> comments);
}
