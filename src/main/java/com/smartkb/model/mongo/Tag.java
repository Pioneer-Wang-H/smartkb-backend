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

/**
 * 标签实体（MongoDB 文档）
 *
 * <p>存储标签信息，用于文章分类和筛选
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class Tag {

    /**
     * 标签主键 ID
     */
    @Id
    private String id;

    /**
     * 标签名称，唯一
     */
    private String name;

    /**
     * 标签颜色，用于前端展示
     */
    @Builder.Default
    private String color = "#1890ff";

    /**
     * 标签排序权重
     */
    @Builder.Default
    private Integer sort = 0;

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
}
