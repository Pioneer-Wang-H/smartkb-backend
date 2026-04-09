package com.smartkb.controller;

import com.smartkb.common.PageRequest;
import com.smartkb.common.PageResult;
import com.smartkb.common.Result;
import com.smartkb.dto.request.ArticleRequest;
import com.smartkb.dto.response.ArticleResponse;
import com.smartkb.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文章管理 REST API
 *
 * @author SmartKB Team
 */
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 创建文章
     */
    @PostMapping
    public Result<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
        ArticleResponse response = articleService.create(request);
        return Result.success(response);
    }

    /**
     * 更新文章
     */
    @PutMapping("/{id}")
    public Result<ArticleResponse> update(@PathVariable String id, @Valid @RequestBody ArticleRequest request) {
        ArticleResponse response = articleService.update(id, request);
        return Result.success(response);
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        articleService.delete(id);
        return Result.success(null);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public Result<ArticleResponse> getById(@PathVariable String id) {
        ArticleResponse response = articleService.getById(id);
        return Result.success(response);
    }

    /**
     * 分页查询文章列表
     */
    @GetMapping
    public Result<PageResult<ArticleResponse>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.builder().page(page).size(size).build();
        PageResult<ArticleResponse> result = articleService.list(pageRequest);
        return Result.success(result);
    }

    /**
     * 同步所有文章到 ES（管理接口）
     */
    @PostMapping("/sync")
    public Result<Void> syncToEs() {
        articleService.syncAllToEs();
        return Result.success(null, "同步成功");
    }
}
