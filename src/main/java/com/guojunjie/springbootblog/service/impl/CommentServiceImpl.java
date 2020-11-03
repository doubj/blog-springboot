package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.CommentMapper;
import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.CommentService;
import com.guojunjie.springbootblog.service.dto.CommentItemDTO;
import com.guojunjie.springbootblog.service.dto.CommentQuery;
import com.guojunjie.springbootblog.service.mapper.CommentItemMapper;
import com.guojunjie.springbootblog.service.mapper.CommentSubItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Lazy
    @Autowired
    CommentService commentService;
    @Autowired
    CommentItemMapper commentItemMapper;
    @Autowired
    CommentSubItemMapper commentSubItemMapper;
    @Autowired
    BlogService blogService;

    @Override
    public void addComment(Comment comment) {
        // todo: 添加事务
        comment.setChecked(!(comment.getPid() == 0 || comment.isBeRepliedAdmin()));
        if (!comment.isChecked()) {
            blogService.incrNewCommentCount(comment.getBlogId(), 1);
        }
        commentMapper.addComment(comment);
    }


    @Override
    public Map<String, Object> getCommentByQuery(CommentQuery commentQuery, Boolean reset) {
        Map<String, Object> res = new HashMap<>(16);
        List<Comment> items = commentMapper.getCommentsByQuery(commentQuery);
        for(Comment comment : items){
            comment.setChildren(commentMapper.getChildrenList(comment.getCommentId()));
        }
        int totalCount = commentMapper.getBlogCommentCount(commentQuery.getBlogId());
        res.put("items", items);
        res.put("count",totalCount);
        // 异步重置评论状态与文章新评论数
//        if (reset) {
//            commentService.modifyCheckedState(items);
//        }
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

    /**
     * 后台获取评论列表后，设置已读标记位并减少博客的未读评论数
     * todo: 添加事务
     * @param comments 评论列表
     */
    @Override
    @Async
    public void modifyCheckedState(List<Comment> comments) {
        List<Integer> ids = new ArrayList<>();
        Integer count = getUncheckedIds(comments, ids);
        // 切换状态并减少未读评论
        commentMapper.setCheckedByBlogIds(ids);
        if (count > 0) {
            blogService.incrNewCommentCount(comments.get(0).getBlogId(), -count);
        }
    }

    private Integer getUncheckedIds(List<Comment> comments, List<Integer> ids) {
        Integer count = 0;
        for (Comment comment : comments) {
            if(!comment.isChecked()){
                // 未读评论
                ids.add(comment.getCommentId());
                count++;
            }
            if (comment.getChildren() != null && comment.getChildren().size() > 0){
                count += getUncheckedIds(comment.getChildren(), ids);
            }
        }
        return count;
    }

}
