package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.util.*;
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

    @PostMapping("/login")
    @ResponseBody
    public Result Login(@RequestBody User user) {
        String userName = userService.getUserByUsernameAndPassword(user.getUserName(), user.getPassword());
        if (userName != null) {
            String token = JWTUtil.createToken(userName);
            return ResultGenerator.genSuccessResult(token);
        }
        return ResultGenerator.genErrorResult("登录失败，用户名密码错误");
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
}
