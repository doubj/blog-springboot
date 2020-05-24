package com.guojunjie.springbootblog.exception;

import com.guojunjie.springbootblog.common.ResultCode;
import lombok.Data;

/**
 * @Date： 2020/4/22 14:42
 * @author： guojunjie
 * TODO 业务异常
 */
@Data
public class BusinessException extends RuntimeException {
    private Integer code;

    /**
     * 通过枚举类传参
     *
     * @param resultCode 异常枚举
     */
    public BusinessException(ResultCode resultCode){
        super(resultCode.getResultMsg());
        this.code = resultCode.getResultCode();
    }

    /**
     * 使用自定义消息
     *
     * @param code 错误码
     * @param msg 信息
     */
    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
