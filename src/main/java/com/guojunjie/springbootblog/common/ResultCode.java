package com.guojunjie.springbootblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Date： 2020/4/22 13:43
 * @author： guojunjie
 * TODO
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 返回结果状态码及对应信息
     */
    SUCCESS(200, "成功!"),
    SIGNATURE_NOT_MATCH(401,"登录失效了哦"),
    INTERNAL_SERVER_ERROR(500, "服务器开小差啦，请稍后再试");
    private Integer resultCode;
    private String resultMsg;
}
