package com.guojunjie.springbootblog.controller.blog;

import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.controller.vo.BlogDetailVo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class MyBlogController {

    @Resource
    BlogService blogService;


    @GetMapping("/page/{currentPage}")
    @ResponseBody
    public Result getPostByPage(@PathVariable int currentPage){
        PageInfo pageInfo = blogService.getPostByPage(currentPage);
        if(pageInfo != null){
            return ResultGenerator.genSuccessResult(pageInfo);
        }
        return ResultGenerator.genErrorResult("获取分页信息失败");
    }

    @GetMapping("/post/{blogId}")
    @ResponseBody
    public Result getPostById(@PathVariable int blogId){
        BlogDetailVo blogDetailVo = blogService.selectPostDetailById(blogId);
        if(blogDetailVo != null){
            return ResultGenerator.genSuccessResult(blogDetailVo);
        }
        return ResultGenerator.genErrorResult("获取博客信息失败");
    }

    @GetMapping("/categories")
    @ResponseBody
    public Result getCategories(){
        List<BlogCategoryVo> blogCategoryVos = blogService.getCategories();
        if(blogCategoryVos != null){
            return ResultGenerator.genSuccessResult(blogCategoryVos);
        }
        return ResultGenerator.genErrorResult("获取分类信息失败");
    }

    @GetMapping("/posts")
    @ResponseBody
    public Result getAllPosts(){
        List<Blog> blogList = blogService.getPublishPostsList();
        if(blogList != null){
            return ResultGenerator.genSuccessResult(blogList);
        }
        return ResultGenerator.genErrorResult("获取博客信息失败");
    }

    @GetMapping("/categories/{categoryId}")
    @ResponseBody
    public Result getBlogByCategory(@PathVariable int categoryId){
        List<Blog> blogList = blogService.getPostByCategoryStatus(categoryId);
        if(blogList != null){
            return ResultGenerator.genSuccessResult(blogList);
        }
        return ResultGenerator.genErrorResult("获取博客信息失败");
    }

    @GetMapping("/tags")
    @ResponseBody
    public Result getAllTags(){
        List<BlogTagVo> blogTagVos = blogService.getTags();
        if(blogTagVos != null){
            return ResultGenerator.genSuccessResult(blogTagVos);
        }
        return ResultGenerator.genErrorResult("获取标签信息失败");
    }

    @GetMapping("/tags/{tagId}")
    @ResponseBody
    public Result getBlogByTag(@PathVariable int tagId){
        List<Blog> blogList = blogService.getPostByTagStatus(tagId);
        if(blogList != null){
            return ResultGenerator.genSuccessResult(blogList);
        }
        return ResultGenerator.genErrorResult("获取博客信息失败");
    }

    @GetMapping("/links")
    @ResponseBody
    public Result getFriendLinks(){
        List<FriendLink> friendLinks = blogService.getFriendLinks();
        if(friendLinks != null){
            return ResultGenerator.genSuccessResult(friendLinks);
        }
        return ResultGenerator.genErrorResult("获取友链信息失败");
    }

    @GetMapping("/user")
    @ResponseBody
    public Result getUser(){
        UserExtra userExtra = blogService.getUser();
        if(userExtra != null){
            return ResultGenerator.genSuccessResult(userExtra);
        }
        return ResultGenerator.genErrorResult("获取用户信息失败");
    }

    @GetMapping("/visits")
    @ResponseBody
    public Result getVisits(){
        int totalVisits = blogService.getTotalVisits();
        if(totalVisits >= 0){
            return ResultGenerator.genSuccessResult(totalVisits);
        }
        return ResultGenerator.genErrorResult("获取总访问人数失败");
    }
}
