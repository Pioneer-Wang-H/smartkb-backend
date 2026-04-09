package com.smartkb.repository.es;

import com.smartkb.model.es.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章 ES 搜索持久层
 *
 * @author SmartKB Team
 */
@Repository
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleDocument, String> {

    /**
     * 根据标签ID列表查询文章
     *
     * @param tagIds 标签ID列表
     * @return 文章列表
     */
    List<ArticleDocument> findByTagIdsIn(List<String> tagIds);

    /**
     * 根据作者ID查询文章列表
     *
     * @param authorId 作者ID
     * @return 文章列表
     */
    List<ArticleDocument> findByAuthorId(String authorId);
}
