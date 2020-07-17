package com.guojunjie.springbootblog.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @time： 2019/11/21 20:22
 * @author： guojunjie
 * TODO： 华为云存储对象服务工具类
 */

public class HuaWeiCloudOBSUtil {

    private static final String END_POINT = "https://obs.cn-south-1.myhuaweicloud.com";
    private static final String AK = "AJ7UVJMQWWV4CNRGO3KR";
    private static final String SK = "DExPuQIiyX45MyhJOozhli5M5cDrVCs3aoZ2hN7N";
    private static final String BUCKET_NAME = "obs-myblog";
    private static final String URL_PREFIX = "https://" + BUCKET_NAME + ".obs.cn-south-1.myhuaweicloud.com/";
    public static String uploadFile(String dirName,String name, File file) throws IOException {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(AK, SK, END_POINT);
        PutObjectResult putObjectResult = obsClient.putObject(BUCKET_NAME, dirName + "/" + name, file);
        obsClient.close();
        return putObjectResult.getObjectUrl();
    }

    public static List<String> getListObjects(){
        // 创建ObsClient实例
        final ObsClient obsClient = new ObsClient(AK, SK, END_POINT);

        ListObjectsRequest request = new ListObjectsRequest(BUCKET_NAME);
        request.setMaxKeys(100);
        request.setDelimiter("/");
        ObjectListing result = obsClient.listObjects(request);
        List<ObsObject> objects = result.getObjects();
        List<String> res = new ArrayList<>();
        for(String prefix : result.getCommonPrefixes()){
            res.add(prefix.substring(0, prefix.length() - 1));
        }
        return res;
    }

    public static List<String> listObjectsByPrefix(String prefix) throws ObsException {
        final ObsClient obsClient = new ObsClient(AK, SK, END_POINT);

        ListObjectsRequest request = new ListObjectsRequest(BUCKET_NAME);
        List<String> list = new ArrayList<>();
        // 设置列举带有prefix前缀的100个对象
        request.setMaxKeys(100);
        request.setPrefix(prefix);
        ObjectListing result = obsClient.listObjects(request);
        for(ObsObject obsObject : result.getObjects()){
            String key = obsObject.getObjectKey();
            if(!key.endsWith("/")){
                list.add(URL_PREFIX + obsObject.getObjectKey());
            }
        }
        return list;
    }

    public static void deleteObject(String name){
        ObsClient obsClient = new ObsClient(AK, SK, END_POINT);
        obsClient.deleteObject(BUCKET_NAME, name);
    }


    public static void addDir(String dirName){
        ObsClient obsClient = new ObsClient(AK, SK, END_POINT);
        PutObjectResult putObjectResult = obsClient.putObject(BUCKET_NAME, dirName + "/", new ByteArrayInputStream(new byte[0]));
    }
}
