package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.util.RandomUtil;
import com.guojunjie.springbootblog.util.UploadUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    public Result getPosts() {
        List<Blog> list = blogService.getPostsList();
        if (!list.isEmpty()) {
            return ResultGenerator.genSuccessResult(list);
        }
        return ResultGenerator.genErrorResult("获取博客集合失败");
    }

    @GetMapping("/posts/{id}")
    @UserLoginToken
    @ResponseBody
    public Result getPostById(@PathVariable int id) {
        Blog blog = blogService.selectPostsById(id);
        if (blog != null) {
            return ResultGenerator.genSuccessResult(blog);
        }
        return ResultGenerator.genErrorResult("获取id为" + id + "的博客失败");
    }

    @PostMapping("/posts")
    @UserLoginToken
    @ResponseBody
    public Result addPost(@RequestParam(value = "blogId") Integer blogId,
                          @RequestParam(value = "blogTitle") String blogTitle,
                          @RequestParam(value = "file") MultipartFile file,
                          @RequestParam(value = "blogCategoryId") Integer blogCategoryId,
                          @RequestParam(value = "blogCategoryName") String blogCategoryName,
                          @RequestParam(value = "blogTags") String blogTags,
                          @RequestParam(value = "blogContent") String blogContent) throws IOException {

        //上传背景图
        String fileName = RandomUtil.getRandomFileName();
        String url = UploadUtil.uploadFile(file, blogTitle + "/" + fileName);

        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogCoverImage(url);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogCategoryName(blogCategoryName);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);

        //添加blog
        boolean res = blogService.addPost(blog);
        if (res) {
            blog.setBlogVisits(0L);
            blog.setBlogStatus((byte) 0);
            blog.setCreateTime(new Date());
            blog.setBlogContent("");
            return ResultGenerator.genSuccessResult("添加博客成功", blog);
        }
        return ResultGenerator.genErrorResult("添加博客失败");
    }

    @PutMapping("/posts")
    @UserLoginToken
    @ResponseBody
    public Result updatePost(@RequestParam(value = "blogId") Integer blogId,
                             @RequestParam(value = "blogTitle") String blogTitle,
                             @RequestParam(value = "file",required = false) MultipartFile file,
                             @RequestParam(value = "blogCategoryId") Integer blogCategoryId,
                             @RequestParam(value = "blogCategoryName") String blogCategoryName,
                             @RequestParam(value = "blogTags") String blogTags,
                             @RequestParam(value = "blogContent") String blogContent) throws IOException {
        Blog blog = new Blog();
        //上传背景图并设置url
        if(file != null){
            String fileName = RandomUtil.getRandomFileName();
            String url = UploadUtil.uploadFile(file, blogTitle + "/" + fileName);
            blog.setBlogCoverImage(url);
        }
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogCategoryName(blogCategoryName);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);

        boolean res = blogService.updatePost(blog);
        if (res) {
            return ResultGenerator.genSuccessResult("修改博客成功", blog);
        }
        return ResultGenerator.genErrorResult("修改博客失败");
    }

    @DeleteMapping("/posts/{id}")
    @UserLoginToken
    @ResponseBody
    public Result deletePost(@PathVariable int id) {
        boolean res = blogService.deletePost(id);
        if (res) {
            return ResultGenerator.genSuccessResultMsg("删除博客成功");
        }
        return ResultGenerator.genErrorResult("删除博客失败");
    }

    @PatchMapping("/posts/{status}/{id}")
    @UserLoginToken
    @ResponseBody
    public Result publishPost(@PathVariable int status, @PathVariable int id) {
        boolean res = blogService.switchStatus(status, id);
        if (res) {
            return ResultGenerator.genSuccessResultMsg("修改状态成功");
        }
        return ResultGenerator.genErrorResult("修改状态失败");
    }

    @PostMapping("/posts/file")
    @UserLoginToken
    @ResponseBody
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "url", required = false) String url) throws IOException {

        if (url.length() < 0) {
            return ResultGenerator.genErrorResult("请先写标题");
        }
        String fileName = RandomUtil.getRandomFileName();
        String res = UploadUtil.uploadFile(file, url + "/" + fileName);
        if (res.length() > 0) {
            return ResultGenerator.genSuccessResult(res);
        }
        return ResultGenerator.genErrorResult("上传文件失败");
    }
}