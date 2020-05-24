package com.guojunjie.springbootblog.common;

/**
 * @Date： 2020/4/25 16:34
 * @author： guojunjie
 */
public class Constants {
    /**
     * Redis中推荐Zset的key
     */
    public final static String RECOMMEND_ZSET_KEY="recommendZset";
    /**
     * Redis中每日访问量Zset的key
     */
    public final static String VISITS_ZSET_KEY="visitsZset";

    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";
    public static final String ADMIN_EMAIL = "935208113@qq.com";
}
