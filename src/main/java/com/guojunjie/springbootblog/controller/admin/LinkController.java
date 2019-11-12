package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/links")
    @UserLoginToken
    @ResponseBody
    public Result getLinks(){
        List<FriendLink> resList = linkService.selectLinks();
        if(!resList.isEmpty()){
            return ResultGenerator.genSuccessResult(resList);
        }
        return ResultGenerator.genErrorResult("获取友链失败");
    }

    @PostMapping("/links")
    @UserLoginToken
    @ResponseBody
    public Result addLink(@RequestBody FriendLink friendLink){
        boolean res = linkService.addLink(friendLink);
        if(res){
            return ResultGenerator.genSuccessResult("添加友链成功",friendLink);
        }
        return ResultGenerator.genErrorResult("添加失败");
    }

    @PutMapping("/links")
    @UserLoginToken
    @ResponseBody
    public Result updateLink(@RequestBody FriendLink friendLink){
        boolean res = linkService.updateLink(friendLink);
        if(res){
            return ResultGenerator.genSuccessResult("修改友链成功",friendLink);
        }
        return ResultGenerator.genErrorResult("修改失败");
    }

    @DeleteMapping("/links/{id}")
    @UserLoginToken
    @ResponseBody
    public Result updateLink(@PathVariable int id){
        boolean res = linkService.deleteLink(id);
        if(res){
            return ResultGenerator.genSuccessResultMsg("删除友链成功");
        }
        return ResultGenerator.genErrorResult("删除失败");
    }
}