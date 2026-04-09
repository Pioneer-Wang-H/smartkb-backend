package com.smartkb.repository.mongo;

import com.smartkb.model.mongo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章 MongoDB 持久层
 *
 * @author SmartKB Team
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    /**
     * 根据状态查询文章分页列表
     *
     * @param status   文章状态
     * @param pageable  分页参数
     * @return 文章分页结果
     */
    Page<Article> findByStatus(Integer status, Pageable pageable);

    /**
     * 根据标签ID查询已发布的文章列表
     *
     * @param tagIds  标签ID列表
     * @param status 文章状态
     * @return 文章列表
     */
    List<Article> findByTagIdsInAndStatus(List<String> tagIds, Integer status);

    /**
     * 根据作者ID查询文章列表
     *
     * @param authorId 作者ID
     * @return 文章列表
     */
    List<Article> findByAuthorId(String authorId);
}
