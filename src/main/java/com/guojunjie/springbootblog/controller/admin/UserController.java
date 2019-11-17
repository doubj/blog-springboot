package com.guojunjie.springbootblog.controller.admin;


import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.controller.vo.UserVo;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result Login(@RequestBody User user){
        User resUser = userService.findUserByUsernameAndPassword(user.getUserName(),user.getPassword());
        if(resUser != null){
            String token = JWTUtil.createToken(resUser);
            return ResultGenerator.genSuccessResult(token);
        }
        return ResultGenerator.genErrorResult("账号不存在");
    }


    @GetMapping("/user/{id}")
    @UserLoginToken
    @ResponseBody
    public Result getUser(@PathVariable int id){
        UserVo userVo = userService.findUserById(id);
        if(userVo != null){
            return ResultGenerator.genSuccessResult(userVo);
        }
        return ResultGenerator.genErrorResult("没有找到该用户");
    }

    @PutMapping("/user")
    @UserLoginToken
    @ResponseBody
    public Result updateUser(@RequestBody User user){
        boolean res = userService.updateUser(user);
        if(res){
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genErrorResult("修改信息失败");
    }

    @PutMapping("/user/extra")
    @UserLoginToken
    @ResponseBody
    public Result updateUser(@RequestBody UserExtra userextra){
        boolean res = userService.updateUserExtra(userextra);
        if(res){
            return ResultGenerator.genSuccessResult(userextra);
        }
        return ResultGenerator.genErrorResult("修改信息失败");
    }
}
