package com.guojunjie.springbootblog.util;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;

import java.io.File;
import java.io.IOException;

/**
 * @time： 2019/11/21 20:22
 * @author： guojunjie
 * TODO： 华为云存储对象服务工具类
 */

public class HuaWeiUploadUtil {

    public static String uploadFile(String path, File file) throws IOException {
        String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
        String ak = "AJ7UVJMQWWV4CNRGO3KR";
        String sk = "DExPuQIiyX45MyhJOozhli5M5cDrVCs3aoZ2hN7N";
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        PutObjectResult putObjectResult = obsClient.putObject("obs-myblog", path, file);

        obsClient.close();
        return putObjectResult.getObjectUrl();
    }
}
