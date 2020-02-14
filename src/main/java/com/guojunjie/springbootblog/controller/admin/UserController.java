package com.guojunjie.springbootblog.controller.admin;


import com.guojunjie.springbootblog.annotation.UserLoginToken;
import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.service.dto.UserDetail;
import com.guojunjie.springbootblog.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
        User resUser = userService.getUserByUsernameAndPassword(user.getUserName(), user.getPassword());
        if (resUser != null) {
            String token = JWTUtil.createToken(resUser);
            return ResultGenerator.genSuccessResult(token);
        }
        return ResultGenerator.genErrorResult("账号不存在");
    }


    @GetMapping("/user")
    @UserLoginToken
    @ResponseBody
    public Result getUser() {
        UserDetail userDetail = userService.getUserDetail();
        if (userDetail != null) {
            return ResultGenerator.genSuccessResult(userDetail);
        }
        return ResultGenerator.genErrorResult("没有找到该用户");
    }

    @PutMapping("/user")
    @UserLoginToken
    @ResponseBody
    public Result updateUser(@RequestBody User user) {
        boolean res = userService.updateUser(user);
        if (res) {
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genErrorResult("修改信息失败");
    }

    @PutMapping("/user/extra")
    @UserLoginToken
    @ResponseBody
    public Result updateUserExtra(@RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam(value = "userId", required = false) Integer userId,
                                  @RequestParam(value = "nickName", required = false) String nickName,
                                  @RequestParam(value = "introduce", required = false) String introduce) throws IOException {


        //上传File到华为云OBS并返回url
        String fileName = RandomUtil.getRandomFileName();
        String url = UploadUtil.uploadFile(file, "myavatar/avatar");

        UserExtra userExtra = new UserExtra();
        userExtra.setAvatar(url);
        userExtra.setUid(userId);
        userExtra.setIntroduce(introduce);
        userExtra.setNickName(nickName);

        boolean res = userService.updateUserExtra(userExtra);
        if (res) {
            return ResultGenerator.genSuccessResult(userExtra);
        }
        return ResultGenerator.genErrorResult("修改信息失败");
    }
}
