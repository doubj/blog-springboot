package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.BlogTag;
import com.guojunjie.springbootblog.service.BlogTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guojunjie
 */
@RestController
@RequestMapping("/admin")
public class BlogTagController {
    @Resource
    BlogTagService blogTagService;

    @UserLoginToken
    @GetMapping("/tags")
    @ResponseBody
    public Result getTags(){
        List<BlogTag> blogTags = blogTagService.getTags();
        if(!blogTags.isEmpty()){
            return ResultGenerator.genSuccessResult(blogTags);
        }
        return ResultGenerator.genErrorResult("查找标签失败");
    }
}
