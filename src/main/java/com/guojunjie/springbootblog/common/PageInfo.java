package com.guojunjie.springbootblog.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author guojunjie
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    private List data;

    private int currentPage;

    private int totalPage;

    private int pageSize;

    private int totalCount;

}
