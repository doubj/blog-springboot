package com.guojunjie.springbootblog.controller.blog;

import com.guojunjie.springbootblog.service.dto.BlogListQuery;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.service.*;
import com.guojunjie.springbootblog.service.dto.BlogCardDTO;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;
import com.guojunjie.springbootblog.aop.log.Log;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guojunjie
 */
@RestController
public class MyBlogController implements InitializingBean {

    @Resource
    BlogService blogService;

    @Resource
    BlogCategoryService blogCategoryService;

    @Resource
    BlogTagService blogTagService;

    @Resource
    LinkService linkService;

    @Resource
    UserService userService;

    @Resource
    CommentService commentService;

    @PostMapping("/blog/query")
    @ResponseBody
    @Log("访问首页")
    public Result getBlogByQuery(@RequestBody BlogListQuery blogListQuery) {
        List<BlogCardDTO> list = blogService.getBlogByQuery(blogListQuery);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public Result getBlogDetailById(@PathVariable int blogId) {
        BlogDetailDTO res = blogService.getBlogDetailById(blogId);
        return ResultGenerator.genSuccessResult(res);
    }

    @GetMapping("/user")
    @ResponseBody
    public Result getUserInfo() {
        User user = userService.getUserInfo();
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping("/links")
    @ResponseBody
    public Result getFriendLinks() {
        List<FriendLink> list = linkService.getFriendLinks();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/search")
    @ResponseBody
    public Result search(@RequestParam(value = "keywords") String keyWords, @RequestParam(value = "page") int page) {
        Map<String, Object> map = blogService.searchBlogByKeywords(keyWords, page);
        return ResultGenerator.genSuccessResult(map);
    }

    @GetMapping("/categories")
    @ResponseBody
    public Result getCategories(){
        List<BlogCategory> list = blogCategoryService.getCategories();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/tags")
    @ResponseBody
    public Result getTags(){
        List<BlogTag> list = blogTagService.getTags();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/recommend")
    @ResponseBody
    public Result getRecommendList(){
        final int recommendSize = 8;
        Set<ZSetOperations.TypedTuple<String>> set = blogService.getRecommendList();
        return ResultGenerator.genSuccessResult(set.stream().limit(recommendSize));
    }

    @PostMapping("/comment")
    @ResponseBody
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/comment")
    @ResponseBody
    public Result getCommentByPage(@RequestParam("page")int page,
    @RequestParam("type") String type,
    @RequestParam(value = "relationId",required = false,defaultValue = "0") int relationId){
        final int limit = 6;
        Map<String, Object> map = commentService.getCommentByPage(page,limit,type,relationId);
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 初始化推荐文章列表
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        blogService.setRecommend();
    }
}