package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.controller.vo.BlogByMonthVo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.service.BlogService;
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
@RequestMapping("/admin")
public class DashboardController {
    @Resource
    BlogService blogService;

    @GetMapping("/statistics/tags")
    @ResponseBody
    @UserLoginToken
    public Result getTagAndCount() {
        List<BlogTagVo> blogTagVos = blogService.getTagAndCountWithStatus(false);
        return blogTagVos == null ? ResultGenerator.genErrorResult("获取标签信息失败") : ResultGenerator.genSuccessResult(blogTagVos);
    }

    @GetMapping("/statistics/categories")
    @ResponseBody
    @UserLoginToken
    public Result getCategoryAndCount() {
        List<BlogCategoryVo> blogCategoryVos = blogService.getCategoryAndCountWithStatus(false);
        return blogCategoryVos == null ? ResultGenerator.genErrorResult("获取分类信息失败") : ResultGenerator.genSuccessResult(blogCategoryVos);
    }

    @GetMapping("/statistics/blog")
    @ResponseBody
    @UserLoginToken
    public Result getPublishedBlogCountWithMonth() {
        List<BlogByMonthVo> list = blogService.getBlogCountInRecentlySixMonthWithStatus(false);
        return ResultGenerator.genSuccessResult(list);
    }
}
