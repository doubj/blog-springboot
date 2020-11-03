package com.guojunjie.springbootblog.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * @Date： 2020/7/17 20:36
 */
public class LocalUpload {
    private static final String baseUrl = "C:/app/data/";
    public static String putObject(String title, String key, MultipartFile sourceFile) {

        Calendar calendar = Calendar.getInstance();

        // 文件存储路径 C:\app\data\2020717\objectName
        String prefix = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH) + 1) + "" + calendar.get(Calendar.DATE);
        String path = baseUrl + "/" + prefix;
        createDir(path);
        String fileType = sourceFile.getOriginalFilename().substring(sourceFile.getOriginalFilename().lastIndexOf("."));
        String filePath = path + "/" + key + fileType ;

        try {
            FileOutputStream file = new FileOutputStream(filePath);
            file.write(sourceFile.getBytes());
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file://" + filePath;
    }

    public static void createDir(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }

        String[] paths = path.split("/");
        String newPath = "";
        for(int i=0;i<paths.length;i++){
            if(i==paths.length-1){
                if(paths[i].contains(".")){
                    newPath = newPath.substring(0,newPath.length()-1);
                }else{
                    newPath += paths[i];
                }
            }else{
                newPath += paths[i] + "/";
            }
        }

        File fileinfo = new File(newPath);
        if (!fileinfo.isDirectory()) {
            fileinfo.mkdirs();
        }
    }
}
