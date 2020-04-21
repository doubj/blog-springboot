package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.BlogListQueryAdmin;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.util.RandomUtil;
import com.guojunjie.springbootblog.util.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author guojunjie
 */
@RestController
@RequestMapping("/admin")
public class BlogController {

    @Resource
    BlogService blogService;

    @PostMapping("/blog/query")
    @ResponseBody
    public Result getBlogListAndCountByQuery(@RequestBody BlogListQueryAdmin query) {
        Map<String, Object> res = blogService.getBlogListAndCountByQuery(query);
        return ResultGenerator.genSuccessResult(res);
    }

    @GetMapping("/blog/{id}")
    @ResponseBody
    public Result getBlogById(@PathVariable int id) {
        Blog blog = blogService.getBlogById(id);
        return ResultGenerator.genSuccessResult(blog);
    }

    @PostMapping("/blog")
    @ResponseBody
    public Result addBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/blog")
    @ResponseBody
    public Result updateBlog(@RequestBody Blog blog) {
        blogService.updateBlog(blog);
        return ResultGenerator.genSuccessResult();
    }

    @PatchMapping("/blog/status/{id}")
    @ResponseBody
    public Result modifyBlogStatus(@RequestParam String status, @PathVariable int id) {
        blogService.modifyBlogStatus(status, id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/blog/file")
    @ResponseBody
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "title", required = false) String title) throws IOException {
        // todo：这里还有些问题，就是上传了图片后又修改了标题咋办，又或者更新时修改了标题
        if (title.length() < 0) {
            return ResultGenerator.genErrorResult("请先写博客标题");
        }
        String fileName = RandomUtil.getRandomFileName();
        String res = UploadUtil.uploadFile(file, title + "/" + fileName);
        if (res.length() > 0) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genErrorResult("上传文件失败");
    }
}