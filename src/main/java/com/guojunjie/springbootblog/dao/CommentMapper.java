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
     * 分页获取评论列表
     * @param page
     * @param limit
     * @param type
     * @param relationId
     * @return
     */
    List<Comment> getCommentList(@Param("page") int page, @Param("limit") int limit,
                                 @Param("type") String type, @Param("relationId") int relationId);

    /**
     * 获取留言表记录数（分页用）
     * @return
     */
    int getParentMessageTotalCount();

    /**
     * 获取所有子评论
     * @param commentId
     * @return
     */
    List<Comment> getChildrenList(Integer commentId);

    /**
     * 根据查询条件获取评论列表
     * @param commentQuery
     * @return
     */
    List<Comment> getCommentByQuery(CommentQuery commentQuery);

    /**
     * 获取记录数，分页用
     * @param commentQuery
     * @return
     */
    int getMessageTotalCount(CommentQuery commentQuery);


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
}
