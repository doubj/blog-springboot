package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.CommentService;
import com.guojunjie.springbootblog.service.dto.CommentQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Date： 2020/5/10 21:01
 * @author： guojunjie
 */
@RestController
@RequestMapping("/admin")
public class CommentController {
    @Resource
    CommentService commentService;

    @PostMapping("/comment/query")
    @ResponseBody
    public Result getCommentByQuery(@RequestBody CommentQuery commentQuery){
        Map<String, Object> map = commentService.getCommentByQuery(commentQuery);
        return ResultGenerator.genSuccessResult(map);
    }

    @PostMapping("/comment")
    @ResponseBody
    public Result addCommentAdmin(@RequestBody Comment comment){
        comment.setNickName("doubj");
        commentService.addComment(comment);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/comment/{id}")
    @ResponseBody
    public Result deleteFriendLink(@PathVariable int id){
        commentService.deleteComment(id);
        return ResultGenerator.genSuccessResult();
    }
}
