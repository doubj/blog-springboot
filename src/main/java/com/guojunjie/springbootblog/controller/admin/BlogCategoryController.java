package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.BlogCategory;
import com.guojunjie.springbootblog.service.BlogCategoryService;
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
public class BlogCategoryController {
    @Resource
    BlogCategoryService blogCategoryService;

    @GetMapping("/categories")
    @ResponseBody
    public Result getCategories(){
        List<BlogCategory> list = blogCategoryService.getCategories();
        return ResultGenerator.genSuccessResult(list);
    }
}
