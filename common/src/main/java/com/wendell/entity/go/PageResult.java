package com.wendell.entity.go;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangWenhao
 * @date 2026/1/16 17:06
 */
public class PageResult<T> implements Serializable {
    private long pageNum;
    private long pageSize;
    private long total;
    private List<T> list;

    public PageResult() {}

    public PageResult(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list == null ? Collections.emptyList() : list;
    }

    // 静态构造方法，方便使用
    public static <T> PageResult<T> of(long pageNum, long pageSize, long total, List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }

    // Getters and Setters
    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
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
}
