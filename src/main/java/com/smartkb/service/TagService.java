package com.smartkb.service;

import com.smartkb.dto.request.TagRequest;
import com.smartkb.dto.response.TagResponse;
import com.smartkb.model.mongo.Tag;
import com.smartkb.repository.mongo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务
 *
 * @author SmartKB Team
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 创建标签
     *
     * @param request 标签创建请求
     * @return 创建的标签响应
     */
    public TagResponse create(TagRequest request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .color(request.getColor() != null ? request.getColor() : "#1890ff")
                .sort(request.getSort() != null ? request.getSort() : 0)
                .build();

        Tag saved = tagRepository.save(tag);
        return TagResponse.from(saved);
    }

    /**
     * 更新标签
     *
     * @param id      标签ID
     * @param request 更新请求
     * @return 更新后的标签响应
     */
    public TagResponse update(String id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("标签不存在: " + id));

        if (request.getName() != null) {
            tag.setName(request.getName());
        }
        if (request.getColor() != null) {
            tag.setColor(request.getColor());
        }
        if (request.getSort() != null) {
            tag.setSort(request.getSort());
        }

        Tag saved = tagRepository.save(tag);
        return TagResponse.from(saved);
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    public void delete(String id) {
        tagRepository.deleteById(id);
    }

    /**
     * 根据ID获取标签
     *
     * @param id 标签ID
     * @return 标签响应
     */
    public TagResponse getById(String id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("标签不存在: " + id));
        return TagResponse.from(tag);
    }

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    public List<TagResponse> listAll() {
        return tagRepository.findAll().stream()
                .map(TagResponse::from)
                .toList();
    }
}
