package com.guojunjie.springbootblog.common;
/**
 * @author guojunjie
 */
public class ResultGenerator {

    /**
     * 返回不带数据只有code=200的成功响应结果
     *
     * @return
     */
    public static Result genSuccessResult() {
        return genSuccessResult(null);
    }

    /**
     * 带数据集的成功响应结果
     *
     * @param data
     * @return
     */
    public static Result genSuccessResult(Object data) {
        return new Result(ResultCode.SUCCESS.getResultCode(),data);
    }


    /**
     * 通用的500服务器异常
     *
     * @return
     */
    public static Result genErrorResult(){
        return genErrorResult(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 使用自定义异常
     *
     * @param code
     * @param message
     * @return
     */
    public static Result genErrorResult(Integer code,String message) {
        return new Result(code,message);
    }

    /**
     * 使用异常枚举类中规定好的异常
     *
     * @param resultCode
     * @return
     */
    public static Result genErrorResult(ResultCode resultCode) {
        return new Result(resultCode.getResultCode(),resultCode.getResultMsg());
    }
}
