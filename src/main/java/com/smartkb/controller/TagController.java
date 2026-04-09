package com.smartkb.controller;

import com.smartkb.common.Result;
import com.smartkb.dto.request.TagRequest;
import com.smartkb.dto.response.TagResponse;
import com.smartkb.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理 REST API
 *
 * @author SmartKB Team
 */
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 创建标签
     */
    @PostMapping
    public Result<TagResponse> create(@Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.create(request);
        return Result.success(response);
    }

    /**
     * 更新标签
     */
    @PutMapping("/{id}")
    public Result<TagResponse> update(@PathVariable String id, @Valid @RequestBody TagRequest request) {
        TagResponse response = tagService.update(id, request);
        return Result.success(response);
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        tagService.delete(id);
        return Result.success(null);
    }

    /**
     * 获取标签详情
     */
    @GetMapping("/{id}")
    public Result<TagResponse> getById(@PathVariable String id) {
        TagResponse response = tagService.getById(id);
        return Result.success(response);
    }

    /**
     * 获取所有标签
     */
    @GetMapping
    public Result<List<TagResponse>> listAll() {
        List<TagResponse> responses = tagService.listAll();
        return Result.success(responses);
    }
}
