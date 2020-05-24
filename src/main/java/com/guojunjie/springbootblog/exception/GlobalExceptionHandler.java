package com.guojunjie.springbootblog.exception;

import com.guojunjie.springbootblog.common.Result;
import com.guojunjie.springbootblog.common.ResultCode;
import com.guojunjie.springbootblog.common.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Date： 2020/4/22 14:28
 * @author： guojunjie
 * TODO 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public  Result businessExceptionHandler(BusinessException e){
        logger.error("发生业务异常！原因是：{}",e.getMessage());
        return ResultGenerator.genErrorResult(e.getCode(),e.getMessage());
    }
    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        logger.error("未知异常！原因是:",e.getMessage());
        return ResultGenerator.genErrorResult();
    }
}
