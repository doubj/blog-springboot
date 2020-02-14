package com.guojunjie.springbootblog.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guojunjie
 */
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int resultCode;
    private String message;
    private T data;

    public Result(int resultCode){
        this.resultCode = resultCode;
    }

    public Result(int resultCode,T data){
        this.resultCode = resultCode;
        this.data = data;
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Result(int resultCode,String message,T data){
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }
}
