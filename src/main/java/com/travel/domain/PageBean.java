package com.travel.domain;
import java.util.List;

/**
 * @ClassName PageBeans
 * @description:
 * @author: LJX
 * @time: 2020/1/25 21:44
 * @version: 1.0
 * 定义分页查询的JavaBean类,包含分页查询所需要的信息
 */
public class PageBean<T> {
    private int totalCount;             //总记录数
    private int totalPage;              //总页码
    private List<T> pageList;           //每页的数据
    private int currentPage;            //当前页码
    private int rows;                   //每页显示记录数

    public PageBean() {
    }
    public PageBean(int totalCount, int totalPage, List<T> pageList, int currentPage, int rows) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageList = pageList;
        this.currentPage = currentPage;
        this.rows = rows;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}

