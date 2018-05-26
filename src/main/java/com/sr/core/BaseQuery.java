package com.sr.core;

import java.io.Serializable;

/**
 * Created by songk on 2017/11/11.
 */
public class BaseQuery implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 4;

    public static final String ASC_SORT = "asc";

    public static final String DESC_SORT = "desc";

    private int pageSize = DEFAULT_PAGE_SIZE;  //每页多少条记录，默认20

    private int totalRecord = 0;  //总记录数

    private int currentPage = 1;  //当前页数

    private int totalPage = 0;   //总页数

    private int max = 0;   //mysql读取记录数

    private int offset = 0;  //mysql开始位置

    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static String getAscSort() {
        return ASC_SORT;
    }

    public static String getDescSort() {
        return DESC_SORT;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalPage = (totalRecord + getPageSize() - 1) / getPageSize();
        this.totalRecord = totalRecord;
    }

    public int getCurrentPage() {
        if (currentPage < 1) {
            currentPage = 1;
        } else if (getTotalPage() > 0 && currentPage > getTotalPage()) {
            currentPage = getTotalPage();
        }

        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getMax() {
        if (max == 0) {
            return this.getPageSize();
        }
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getOffset() {
        if (offset == 0) {
            return ((this.getCurrentPage() - 1 <= 0) ? 0 : this.getCurrentPage() - 1) * this.getPageSize();
        }
        return offset;
}

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
