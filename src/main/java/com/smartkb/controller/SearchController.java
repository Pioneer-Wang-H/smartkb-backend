package com.smartkb.controller;

import com.smartkb.common.PageResult;
import com.smartkb.common.Result;
import com.smartkb.dto.request.SearchRequest;
import com.smartkb.dto.response.SearchResponse;
import com.smartkb.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索 REST API
 *
 * @author SmartKB Team
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 全文搜索
     *
     * @param keyword 搜索关键词
     * @param tagId  标签ID筛选（可选）
     * @param page   页码，从 1 开始
     * @param size   每页条数
     * @return 搜索结果
     */
    @GetMapping
    public Result<PageResult<SearchResponse>> search(
            @RequestParam String keyword,
            @RequestParam(required = false) String tagId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        SearchRequest request = SearchRequest.builder()
                .keyword(keyword)
                .tagId(tagId)
                .page(page)
                .size(size)
                .build();

        PageResult<SearchResponse> result = searchService.search(request);
        return Result.success(result);
    }

    /**
     * 获取热门标签
     */
    @GetMapping("/hot-tags")
    public Result<?> getHotTags() {
        return Result.success(searchService.getHotTags());
    }
}
