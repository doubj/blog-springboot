package com.guojunjie.springbootblog.controller.blog;

import com.guojunjie.springbootblog.common.BlogListQuery;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.entity.Message;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.LinkService;
import com.guojunjie.springbootblog.service.MessageService;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.service.dto.BlogCardDTO;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author guojunjie
 */
@RestController
public class MyBlogController {

    @Resource
    BlogService blogService;

    @Resource
    MessageService messageService;

    @Resource
    LinkService linkService;

    @Resource
    UserService userService;

    @PostMapping("/blog/query")
    @ResponseBody
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

    @PostMapping("/message")
    @ResponseBody
    public Result addMessage(@RequestBody Message message){
        messageService.addMessage(message);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/message")
    @ResponseBody
    public Result getMessageList(@RequestParam("page") int page){
        Map<String,Object> map = messageService.getMessageByPage(page);
        return ResultGenerator.genSuccessResult(map);
    }
}