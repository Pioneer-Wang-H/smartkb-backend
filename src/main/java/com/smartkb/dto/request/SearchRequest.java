package com.smartkb.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索请求
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    /**
     * 搜索关键词
     */
    @NotBlank(message = "搜索关键词不能为空")
    private String keyword;

    /**
     * 标签ID筛选
     */
    private String tagId;

    /**
     * 当前页码，从 1 开始
     */
    @Builder.Default
    private Integer page = 1;

    /**
     * 每页条数
     */
    @Builder.Default
    private Integer size = 10;
}
