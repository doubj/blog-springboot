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
    private int code;
    private String msg;
    private T data;

    public Result(int code){
        this.code = code;
    }

    public Result(int code, T data){
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
