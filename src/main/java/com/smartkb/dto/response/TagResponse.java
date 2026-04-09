package com.smartkb.dto.response;

import com.smartkb.model.mongo.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 标签响应
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {

    private String id;
    private String name;
    private String color;
    private Integer sort;
    private LocalDateTime createdAt;

    /**
     * 从 Tag 实体转换
     */
    public static TagResponse from(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .sort(tag.getSort())
                .createdAt(tag.getCreatedAt())
                .build();
    }
}
