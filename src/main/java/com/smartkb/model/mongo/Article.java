package com.smartkb.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章实体（MongoDB 文档）
 *
 * <p>存储文章的完整信息，包括标题、正文、标签等
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "articles")
public class Article {

    /**
     * 文章主键 ID
     */
    @Id
    private String id;

    /**
     * 文章标题，2-100 字符
     */
    private String title;

    /**
     * 文章正文内容
     */
    private String content;

    /**
     * 作者ID
     */
    private String authorId;

    /**
     * 标签ID列表
     */
    private List<String> tagIds;

    /**
     * 文章状态：0-草稿，1-已发布
     */
    @Builder.Default
    private Integer status = 1;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 文章状态枚举
     */
    public enum Status {
        DRAFT(0),
        PUBLISHED(1);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
