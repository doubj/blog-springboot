package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class BlogController {

    @Resource
    BlogService blogService;

    @GetMapping("/posts")
    @UserLoginToken
    @ResponseBody
    public Result getPosts(){
        List<Blog> list = blogService.getPostsList();
        if(!list.isEmpty()){
            return ResultGenerator.genSuccessResult(list);
        }
        return ResultGenerator.genErrorResult("获取博客集合失败");
    }

    @GetMapping("/posts/{id}")
    @UserLoginToken
    @ResponseBody
    public Result getPostById(@PathVariable int id){
        Blog blog = blogService.selectPostsById(id);
        if(blog != null){
            return ResultGenerator.genSuccessResult(blog);
        }
        return ResultGenerator.genErrorResult("获取id为" + id + "的博客失败");
    }

    @PostMapping("/posts")
    @UserLoginToken
    @ResponseBody
    public Result addPost(@RequestBody Blog blog){
        boolean res = blogService.addPost(blog);
        if(res){
            blog.setBlogVisits(0L);
            blog.setBlogStatus((byte)0);
            blog.setCreateTime(new Date());
            blog.setBlogContent("");
            return ResultGenerator.genSuccessResult("添加博客成功",blog);
        }
        return ResultGenerator.genErrorResult("添加博客失败");
    }

    @PutMapping("/posts")
    @UserLoginToken
    @ResponseBody
    public Result updatePost(@RequestBody Blog blog){
        boolean res = blogService.updatePost(blog);
        if(res){
            return ResultGenerator.genSuccessResult("修改博客成功",blog);
        }
        return ResultGenerator.genErrorResult("修改博客失败");
    }

    @DeleteMapping("/posts/{id}")
    @UserLoginToken
    @ResponseBody
    public Result deletePost(@PathVariable int id){
        boolean res = blogService.deletePost(id);
        if(res){
            return ResultGenerator.genSuccessResultMsg("删除博客成功");
        }
        return ResultGenerator.genErrorResult("删除博客失败");
    }
    @PatchMapping("/posts/{status}/{id}")
    @UserLoginToken
    @ResponseBody
    public Result publishPost(@PathVariable int status,@PathVariable int id){
        boolean res = blogService.switchStatus(status,id);
        if(res){
            return ResultGenerator.genSuccessResultMsg("修改状态成功");
        }
        return ResultGenerator.genErrorResult("修改状态失败");
    }
}