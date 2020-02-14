package com.guojunjie.springbootblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 用于生成随机文件名 当前时间加5位随机数
 */
public class RandomUtil {
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        // 获取5位随机数
        int randNum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;

        // 当前时间
        return randNum + str;
    }
}
