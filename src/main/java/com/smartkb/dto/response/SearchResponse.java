package com.smartkb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 搜索响应
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private String id;
    private String title;
    private String content;
    private String authorId;
    private List<String> tagIds;
    private LocalDateTime createdAt;

    /**
     * 搜索匹配高亮片段
     */
    private Map<String, List<String>> highlights;
}
