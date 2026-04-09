package com.smartkb.model.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章 ES 索引文档
 *
 * <p>用于 Elasticsearch 全文搜索，配置 IK 中文分词器
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "articles")
@Setting(settingPath = "es/article-settings.json")
public class ArticleDocument {

    /**
     * ES 文档 ID，与 MongoDB Article.id 一致
     */
    @Id
    private String id;

    /**
     * 文章标题，使用 IK 分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    /**
     * 文章正文，使用 IK 分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 作者ID，关键字类型用于精确过滤
     */
    @Field(type = FieldType.Keyword)
    private String authorId;

    /**
     * 标签ID列表，关键字类型用于精确过滤
     */
    @Field(type = FieldType.Keyword)
    private List<String> tagIds;

    /**
     * 创建时间，用于排序和范围查询
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;
}
