package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.aop.log.Log;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.exception.BusinessException;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.service.dto.UserPassDTO;
import com.guojunjie.springbootblog.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author guojunjie
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    @Resource
    UserService userService;

    @Log("请求登录")
    @PostMapping("/login")
    @ResponseBody
    public Result Login(@RequestBody User user) {
        String userName = userService.getUserByUsernameAndPassword(user.getUserName(), user.getPassword());
        String token = "";
        if (userName != null) {
            token = JWTUtil.createToken(userName);
        }
        return ResultGenerator.genSuccessResult(token);
    }


    @GetMapping("/user")
    @ResponseBody
    public Result getUser(HttpServletRequest request) {
        // 这里没验证user的正确，因为拦截器已经验证了token的有效性
        // todo:但也许这里需要一个over check?
        String token = request.getHeader("token");
        User user = userService.getUser(token);
        return ResultGenerator.genSuccessResult(user);
    }

    @PutMapping("/user")
    @ResponseBody
    public Result updateUser(@RequestBody User userInfo,HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = userService.getUser(token);
        userInfo.setUserId(user.getUserId());
        userService.updateUser(userInfo);
        return ResultGenerator.genSuccessResult(user);
    }

    @Log("修改密码")
    @PutMapping("/user/pass")
    @ResponseBody
    public Result updatePass(@RequestBody UserPassDTO user,HttpServletRequest request){
        String token = request.getHeader("token");
        userService.updatePass(token,user);
        return ResultGenerator.genSuccessResult();
    }
}
