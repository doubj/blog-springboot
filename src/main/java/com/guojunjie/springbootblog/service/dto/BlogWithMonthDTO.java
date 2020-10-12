package com.guojunjie.springbootblog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Date： 2020/4/21 14:35
 * @author： guojunjie
 * TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogWithMonthDTO{
    private Integer month;
    private Integer count;
}
