package com.guojunjie.springbootblog.common;
public class ResultGenerator {

    private static final int RESULT_CODE_SUCCESS = 200;
    private static final int RESULT_CODE_ERROR = 500;

    public static Result genSuccessResult() {
        return new Result(RESULT_CODE_SUCCESS);
    }

    public static Result genSuccessResultMsg(String message) {
        return new Result(RESULT_CODE_SUCCESS,message);
    }

    public static Result genSuccessResult(Object data) {
        return new Result(RESULT_CODE_SUCCESS,data);
    }

    public static Result genSuccessResult(String message,Object data){
        return new Result(RESULT_CODE_SUCCESS,message,data);
    }


    public static Result genErrorResult(){
        return new Result(RESULT_CODE_ERROR,"请求失败");
    }

    public static Result genErrorResult(String message) {
        return new Result(RESULT_CODE_ERROR,message);
    }

    public static Result genErrorResult(int code, String message) {
        return new Result(code,message);
    }
}
