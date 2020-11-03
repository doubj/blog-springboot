package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.dto.CommentQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date： 2020/4/20 14:48
 * @author： guojunjie
 * TODO
 */
@Component
public interface CommentMapper {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int addComment(Comment comment);

    /**
     * 获取评论列表
     * @param commentQuery
     * @return
     */
    List<Comment> getCommentsByQuery(CommentQuery commentQuery);

    /**
     * 获取所有子评论
     * @param commentId
     * @return
     */
    List<Comment> getChildrenList(Integer commentId);


    /**
     * 删除评论
     * @param id
     */
    void deleteCommentById(int id);

    /**
     * 获取除博主回复外的所有评论数
     * @return
     */
    int getNotBeRepliedAdmin();

    void setCheckedByBlogIds(@Param("ids") List<Integer> ids);

    int getBlogCommentCount(int blogId);
}
