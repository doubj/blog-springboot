package com.guojunjie.springbootblog.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Date： 2020/4/26 10:53
 * @author： guojunjie
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
