package com.guojunjie.springbootblog.controller.admin;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultGenerator;
import com.guojunjie.springbootblog.util.HuaWeiCloudOBSUtil;
import com.guojunjie.springbootblog.util.LocalUpload;
import com.guojunjie.springbootblog.util.RandomUtil;
import com.guojunjie.springbootblog.util.UploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Date： 2020/5/14 11:53
 * @author： guojunjie
 */
@RestController
@RequestMapping("/admin/cloud")
public class CloudController {
    @GetMapping("/dir")
    @ResponseBody
    public Result getAllDir(){
        return ResultGenerator.genSuccessResult(HuaWeiCloudOBSUtil.getListObjects());
    }

    @GetMapping("/img")
    @ResponseBody
    public Result getImageByDir(@RequestParam("prefix") String prefix){
        return ResultGenerator.genSuccessResult(HuaWeiCloudOBSUtil.listObjectsByPrefix(prefix));
    }

    @PostMapping("/dir")
    @ResponseBody
    public Result addDir(@RequestBody Map<String,String> map){
        HuaWeiCloudOBSUtil.addDir(map.get("dirName"));
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/img")
    @ResponseBody
    public Result uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "title", required = false) String title) throws IOException {
        String fileName = RandomUtil.getRandomFileName();
//        String res = UploadUtil.uploadFile(file, title, fileName);
        String res = LocalUpload.putObject(title, fileName, file);
        return ResultGenerator.genSuccessResult(res);
    }

    @DeleteMapping("/img")
    @ResponseBody
    public Result deleteFile(@RequestParam(value = "dirName") String dirName, @RequestParam(value = "name") String name) {

        String key = dirName + "/" + name;
        HuaWeiCloudOBSUtil.deleteObject(key);
        return ResultGenerator.genSuccessResult();
    }
}
