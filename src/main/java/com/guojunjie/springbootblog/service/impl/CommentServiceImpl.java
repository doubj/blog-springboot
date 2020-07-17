package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.CommentMapper;
import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.CommentService;
import com.guojunjie.springbootblog.service.dto.CommentItemDTO;
import com.guojunjie.springbootblog.service.dto.CommentQuery;
import com.guojunjie.springbootblog.service.mapper.CommentItemMapper;
import com.guojunjie.springbootblog.service.mapper.CommentSubItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date： 2020/5/5 11:24
 * @author： guojunjie
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentItemMapper commentItemMapper;
    @Autowired
    CommentSubItemMapper commentSubItemMapper;

    @Override
    public void addComment(Comment comment) {
        final String COMMENT_LIST = "commentList";
        final String MESSAGE_LIST = "messageList";
    }

    @Override
    public Map<String, Object> getCommentByPage(int page, int limit, String type, int relationId) {
        Map<String, Object> res = new HashMap<>(2);
        List<Comment> list = commentMapper.getCommentList(page, limit, type, relationId);
        for(Comment comment : list){
            comment.setChildren(commentSubItemMapper.toDto(commentMapper.getChildrenList(comment.getCommentId())));
        }
        // 如果是评论，会通过点击按钮获取下一页的评论，不需要总数，而如果是留言则返回总数分页用
        int totalCount = 0;
        if("message".equals(type)){
            totalCount = commentMapper.getParentMessageTotalCount();
        }
        List<CommentItemDTO> items = commentItemMapper.toDto(list);
        res.put("items", items);
        res.put("count",totalCount);
        return res;
    }

    @Override
    public int getMessageCount() {
        return commentMapper.getParentMessageTotalCount();
    }

    @Override
    public Map<String, Object> getCommentByQuery(CommentQuery commentQuery) {
        Map<String, Object> res = new HashMap<>(2);
        List<Comment> list = commentMapper.getCommentByQuery(commentQuery);
        int totalCount = commentMapper.getMessageTotalCount(commentQuery);
        res.put("items", list);
        res.put("count",totalCount);
        return res;
    }

    @Override
    public void deleteComment(int id) {
        commentMapper.deleteCommentById(id);
    }

    @Override
    public int getCommentCount() {

        return commentMapper.getNotBeRepliedAdmin();
    }

}
