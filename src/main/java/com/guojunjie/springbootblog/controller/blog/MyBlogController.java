package com.guojunjie.springbootblog.controller.blog;

import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.controller.vo.BlogByMonthVo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.dto.BlogCardDTO;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guojunjie
 */
@RestController
public class MyBlogController {

    @Resource
    BlogService blogService;


    @GetMapping("/page/{currentPage}")
    @ResponseBody
    public Result getPostByPage(@PathVariable int currentPage) {
        PageInfo pageInfo = blogService.getBlogByPage(currentPage);
        return pageInfo == null ? ResultGenerator.genErrorResult("获取分页信息失败") : ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public Result getBlogDetailById(@PathVariable int blogId) {
        BlogDetailDTO blogDetailDTO = blogService.getBlogDetailById(blogId);
        return blogDetailDTO == null ? ResultGenerator.genErrorResult("获取博客信息失败") : ResultGenerator.genSuccessResult(blogDetailDTO);
    }

    @GetMapping("/blog")
    @ResponseBody
    public Result getBlogList() {
        List<BlogCardDTO> blogCardDTOS = blogService.getPublishedBlogList();
        return blogCardDTOS == null ? ResultGenerator.genErrorResult("获取博客信息失败") : ResultGenerator.genSuccessResult(blogCardDTOS);
    }

    @GetMapping("/statistics/categories")
    @ResponseBody
    public Result getCategoryAndCount() {
        List<BlogCategoryVo> blogCategoryVos = blogService.getCategoryAndCountWithStatus(true);
        return blogCategoryVos == null ? ResultGenerator.genErrorResult("获取分类信息失败") : ResultGenerator.genSuccessResult(blogCategoryVos);
    }

    @GetMapping("/categories/{categoryName}")
    @ResponseBody
    public Result getBlogListByCategoryName(@PathVariable String categoryName) {
        List<BlogCardDTO> blogCardDTOS = blogService.getBlogByCategoryAndStatus(categoryName);
        return blogCardDTOS == null ? ResultGenerator.genErrorResult("获取博客信息失败") : ResultGenerator.genSuccessResult(blogCardDTOS);
    }

    @GetMapping("/statistics/tags")
    @ResponseBody
    public Result getTagAndCount() {
        List<BlogTagVo> blogTagVos = blogService.getTagAndCountWithStatus(true);
        return blogTagVos == null ? ResultGenerator.genErrorResult("获取标签信息失败") : ResultGenerator.genSuccessResult(blogTagVos);
    }

    @GetMapping("/tags/{tagName}")
    @ResponseBody
    public Result getBlogListByTagName(@PathVariable String tagName) {
        List<BlogCardDTO> blogCardDTOS = blogService.getBlogByTagAndStatus(tagName);
        return blogCardDTOS == null ? ResultGenerator.genErrorResult("获取博客信息失败") : ResultGenerator.genSuccessResult(blogCardDTOS);
    }

    @GetMapping("/links")
    @ResponseBody
    public Result getFriendLinks() {
        List<FriendLink> friendLinks = blogService.getFriendLinks();
        return friendLinks == null ? ResultGenerator.genErrorResult("获取友链信息失败") : ResultGenerator.genSuccessResult(friendLinks);
    }

    @GetMapping("/user")
    @ResponseBody
    public Result getUserExtra() {
        UserExtra userExtra = blogService.getUserExtra();
        return userExtra == null ? ResultGenerator.genErrorResult("获取用户信息失败") : ResultGenerator.genSuccessResult(userExtra);
    }

    @GetMapping("/visits")
    @ResponseBody
    public Result getVisits() {
        int totalVisits = blogService.getTotalVisits();
        return totalVisits < 0 ? ResultGenerator.genErrorResult("获取总访问人数失败") : ResultGenerator.genSuccessResult(totalVisits);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(value = "keywords") String keyWords) {
        return new ResponseEntity(blogService.searchBlogByKeywords(keyWords), HttpStatus.OK);
    }

    @GetMapping("/statistics/blog")
    @ResponseBody
    public Result getPublishedBlogCountWithMonth() {
        List<BlogByMonthVo> list = blogService.getBlogCountInRecentlySixMonthWithStatus(true);
        return ResultGenerator.genSuccessResult(list);
    }

}