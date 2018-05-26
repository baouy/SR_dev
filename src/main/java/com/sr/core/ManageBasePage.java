package com.sr.core;

/**
 * BJUI 分页基础类
 * Created by KingZS on 2018/5/26
 */
public class ManageBasePage {

    public static boolean usePage=true;

    public static final int DEFAULT_PAGE_SIZE = 4;

    public static final String ASC_SORT = "asc";

    public static final String DESC_SORT = "desc";

    private int pageSize = DEFAULT_PAGE_SIZE;  //每页多少条记录，默认20

    private int totalRecord = 0;  //mysql总记录数

    private int pageCurrent = 1;  //当前页数

    private int totalRow;//bjui分页请求总记录数

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

    public static boolean isUsePage() {
        return usePage;
    }

    public static void setUsePage(boolean usePage) {
        ManageBasePage.usePage = usePage;
    }

    public int getPageCurrent() {
        if (pageCurrent < 1) {
            pageCurrent = 1;
        } else if (getTotalPage() > 0 && pageCurrent > getTotalPage()) {
            pageCurrent = getTotalPage();
        }

        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
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
            return ((this.getPageCurrent() - 1 <= 0) ? 0 : this.getPageCurrent() - 1) * this.getPageSize();
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
