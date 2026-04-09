package com.smartkb.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    /**
     * 当前页码，从 1 开始
     */
    @Builder.Default
    private Integer page = 1;

    /**
     * 每页条数，默认 10
     */
    @Builder.Default
    private Integer size = 10;

    /**
     * 每页最大条数限制
     */
    public static final Integer MAX_SIZE = 100;

    /**
     * 获取 ES from 参数（0-based）
     */
    public Integer getFrom() {
        return (page - 1) * size;
    }

    /**
     * 校验并修正分页参数
     */
    public void validate() {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
    }
}
