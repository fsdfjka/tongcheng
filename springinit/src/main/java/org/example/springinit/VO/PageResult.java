package org.example.springinit.VO;

import lombok.Data;
import java.util.List;

/**
 * 分页响应结果
 */
@Data
public class PageResult<T> {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 当前页数据
     */
    private List<T> list;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    // 构造方法
    public PageResult(Long total, Integer pages, List<T> list, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.pages = pages;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}