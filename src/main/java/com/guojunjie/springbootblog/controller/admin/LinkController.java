package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guojunjie
 */
@RestController
@RequestMapping("/admin")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/links")
    @ResponseBody
    public Result getFriendLinks(){
        List<FriendLink> list = linkService.getFriendLinks();
        return ResultGenerator.genSuccessResult(list);
    }

    @PostMapping("/links")
    @ResponseBody
    public Result addFriendLink(@RequestBody FriendLink friendLink){
        linkService.addFriendLink(friendLink);
        return ResultGenerator.genSuccessResult(friendLink);
    }

    @PutMapping("/links")
    @ResponseBody
    public Result updateFriendLink(@RequestBody FriendLink friendLink){
        linkService.updateFriendLink(friendLink);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/links/{id}")
    @ResponseBody
    public Result deleteFriendLink(@PathVariable int id){
        linkService.deleteFriendLink(id);
        return ResultGenerator.genSuccessResult();
    }
}