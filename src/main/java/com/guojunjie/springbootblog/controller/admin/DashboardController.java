package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.CommentService;
import com.guojunjie.springbootblog.service.LogService;
import com.guojunjie.springbootblog.service.dto.BlogWithCategoryDTO;
import com.guojunjie.springbootblog.service.dto.BlogWithMonthDTO;
import com.guojunjie.springbootblog.service.dto.BlogWithTagDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @time： 2019/11/29 15:40
 * @author： guojunjie
 * TODO
 */
@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {
    @Resource
    BlogService blogService;

    @Resource
    LogService logService;

    @Resource
    CommentService commentService;
    @GetMapping("/visits")
    @ResponseBody
    public Result getTotalVisits() {
        int res = blogService.getTotalVisits();
        return ResultGenerator.genSuccessResult(res);
    }

    @GetMapping("/blog")
    @ResponseBody
    public Result getBlogCount() {
        int res = blogService.getBlogCount();
        return ResultGenerator.genSuccessResult(res);
    }

    @GetMapping("/blog/month")
    @ResponseBody
    public Result getPublishedBlogCountWithMonth() {
        List<BlogWithMonthDTO> list = blogService.getBlogCountInRecentlySixMonth();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/tags")
    @ResponseBody
    public Result getTagAndCount() {
        List<BlogWithTagDTO> list = blogService.getTagAndCount();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/categories")
    @ResponseBody
    public Result getCategoryAndCount() {
        List<BlogWithCategoryDTO> blogCategoryVos = blogService.getCategoryAndCount();
        return ResultGenerator.genSuccessResult(blogCategoryVos);
    }

    @GetMapping("/message")
    @ResponseBody
    public Result getCommentCount() {
        int res = commentService.getCommentCount();
        return ResultGenerator.genSuccessResult(res);
    }

    @GetMapping("/log")
    @ResponseBody
    public Result getLogCount() {
        int res = logService.getLogCount();
        return ResultGenerator.genSuccessResult(res);
    }
}