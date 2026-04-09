package com.smartkb.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(List<T> list, Long total, Integer page, Integer size) {
        int pages = size > 0 ? (int) Math.ceil((double) total / size) : 0;
        return PageResult.<T>builder()
                .list(list)
                .total(total)
                .page(page)
                .size(size)
                .pages(pages)
                .build();
    }
}
