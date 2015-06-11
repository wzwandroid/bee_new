package com.bee.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wzw
 * @date 2015-06-08
 */
public class Pager<E> {
    // ====================================================
    // static fields
    /** 正序排序 */
    public static final String SORT_ASC = "ASC";

    /** 倒序排序 */
    public static final String SORT_DESC = "DESC";

    public static final int DEFAULT_PAGESIZE = 10;

    public static final int DEFAULT_PAGENO = 1;

    // ====================================================
    // 以下属性定义用于前台分页
    /** 当前第几页，默认值为1，既第一页. */
    protected int pageNo = 1;

    /** 每页最大记录数，默认值为0，表示pageSize不可用. */
    protected int pageSize = 0;

    /** 总记录数，默认值为-1，表示totalCount不可用. */
    protected int totalCount = -1;// 总记录数

    /** 排序字段名. */
    protected String sortName = null;

    /** 使用正序还是倒序,默认是正序. */
    protected String sortOrder = SORT_ASC;

    /** 分页查询时是否自动统计总记录数,默认会自动统计. */
    protected boolean autoCount = true;

    /** 查询结果. */
    protected List<E> result = null;

    // ====================================================
    // constructor...

    public Pager() {
        totalCount = 0;
        result = new ArrayList<E>();
    }

    public Pager(int pageNo, int pageSize) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Pager(int pageNo, int pageSize, String sortName, String sortOrder) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortName = sortName;
        this.sortOrder = sortOrder;
    }


    // ====================================================
    //tool method

    /**
     * 是否为正序排序.
     * @return boolean
     */
    public boolean isAsc() {
        return SORT_ASC.equalsIgnoreCase(sortOrder);
    }

    /**
     * 取得倒转的排序方向.
     * @return 如果sortOrder=='ASC'就返回'DESC'，如果sortOrder=='DESC'就返回'ASC'
     */
    public String getInverseSortOrder() {
        if (SORT_ASC.equalsIgnoreCase(sortOrder)) {
            return SORT_DESC;
        } else {
            return SORT_ASC;
        }
    }

    public int getFirstResultSize(){
        return (pageNo - 1) * pageSize;
    }


    /**
     * 排序是否可用.
     * @return sortName是否非空
     */
    public boolean isSortNameEnabled() {
        return (sortName != null) && (sortName.trim().length() != 0);
    }


    // ====================================================
    //getter and setter methods...

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public static String getSortAsc() {
        return SORT_ASC;
    }

    public static String getSortDesc() {
        return SORT_DESC;
    }

    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public int getPageCount() {
        if (this.totalCount == 0) {
            return 0;
        }
        return (this.totalCount - 1) / this.pageSize + 1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pager [autoCount=").append(autoCount).append(
                ", pageNo=").append(pageNo).append(", pageSize=").append(
                pageSize).append(", result=").append(result).append(
                ", sortName=").append(sortName).append(", sortOrder=").append(
                sortOrder).append(", totalCount=").append(totalCount).append(
                "]");
        return builder.toString();
    }

}
