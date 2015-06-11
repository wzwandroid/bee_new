package com.bee.admin.web.filter;

/**
 * 用来传递列表对象的ThreadLocal数据
 * Created by mzllon on 2014/7/24.
 */
public final class SystemContext {
    /**
     * 分页大小
     */
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();

    /**
     * 分页的起始页
     */
    private static ThreadLocal<Integer>  pageNo = new ThreadLocal<Integer>();

    /**
     * 列表的排序字段
     */
    private static ThreadLocal<String>  sortName = new ThreadLocal<String>();

    /**
     * 列表的排序方式
     */
    private static ThreadLocal<String> sortType = new ThreadLocal<String>();

    /**
     * 存储屋里路径
     */
    private static ThreadLocal<String> realPath = new ThreadLocal<String>();

    /**
     * 项目访问地址
     */
    private static ThreadLocal<String> projectUri = new ThreadLocal<String>();


    public static void  removeRealPath() {
        SystemContext.realPath.remove();
    }

    public static void  removePageSize() {
        SystemContext.pageSize.remove();
    }

    public static void  removePageNo() {
        SystemContext.pageNo.remove();
    }

    public static void  removeSortName() {
        SystemContext.sortName.remove();
    }

    public static void  removeSortType() {
        SystemContext.sortType.remove();
    }

    public static void  removeProjectUri() {
        SystemContext.projectUri.remove();
    }

    public static String getRealPath() {
        return realPath.get();
    }

    public static void setRealPath(String _realpath) {
        SystemContext.realPath.set(_realpath);
    }

    public static Integer getPageNo() {
        return pageNo.get();
    }

    public static void setPageNo(Integer _pageOffset) {
        SystemContext.pageNo.set(_pageOffset);
    }

    public static Integer getPageSize() {
        return pageSize.get();
    }

    public static void setPageSize(Integer _pageSize) {
        SystemContext.pageSize.set(_pageSize);
    }

    public static String getSortName() {
        return sortName.get();
    }

    public static void setSortName(String _sortName) {
        SystemContext.sortName.set(_sortName);
    }

    public static String getSortType() {
        return sortType.get();
    }

    public static void setSortType(String _sortType) {
        SystemContext.sortType.set(_sortType);
    }

    public static String getProjectUri() {
        return projectUri.get();
    }

    public static void setProjectUri(String _projectUri) {
        SystemContext.projectUri.set(_projectUri);
    }
}
