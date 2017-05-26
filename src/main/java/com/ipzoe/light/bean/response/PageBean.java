package com.ipzoe.light.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结构
 */
@ApiModel(value = "PageBean", description = "分页Bean")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageBean<T> implements Serializable {
    @ApiModelProperty("不包含查询条件时的记录总数")
    private long all;
    @ApiModelProperty("总记录数")
    private long total;
    @ApiModelProperty("总页数")
    private int pages;
    @ApiModelProperty("页码")
    private int number;
    @ApiModelProperty("每页记录数")
    private int size;
    @ApiModelProperty("结果集")
    private List<T> list;

    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.number = page.getPageNum();
            this.size = page.getPageSize();
            this.list = page.getResult();
        } else {
            this.total = list.size();
            this.pages = 1;
            this.number = 1;
            this.size = list.size();
            this.list = list;
        }
    }

    public PageBean(List<T> list, long all) {
        this(list);
        this.all = all;
    }


    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


}