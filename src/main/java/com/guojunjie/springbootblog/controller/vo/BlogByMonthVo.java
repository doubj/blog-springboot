package com.guojunjie.springbootblog.controller.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @time： 2019/12/2 21:38
 * @author： guojunjie
 * TODO
 */
@Data
@ToString
public class BlogByMonthVo implements Serializable {
    private String month;
    private int count;
}
