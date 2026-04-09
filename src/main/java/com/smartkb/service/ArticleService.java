package com.smartkb.service;

import com.smartkb.common.PageRequest;
import com.smartkb.common.PageResult;
import com.smartkb.dto.request.ArticleRequest;
import com.smartkb.dto.response.ArticleResponse;
import com.smartkb.model.es.ArticleDocument;
import com.smartkb.model.mongo.Article;
import com.smartkb.repository.es.ArticleSearchRepository;
import com.smartkb.repository.mongo.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务
 *
 * <p>提供文章的 CRUD 操作，同时负责 MongoDB 与 ES 的数据同步
 *
 * @author SmartKB Team
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleSearchRepository articleSearchRepository;

    /**
     * 创建文章
     *
     * @param request 文章创建请求
     * @return 创建的文章响应
     */
    public ArticleResponse create(ArticleRequest request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId("default-author")
                .tagIds(request.getTagIds())
                .status(request.getStatus() != null ? request.getStatus() : Article.Status.PUBLISHED.getValue())
                .build();

        Article saved = articleRepository.save(article);
        syncToEs(saved);
        return ArticleResponse.from(saved);
    }

    /**
     * 更新文章
     *
     * @param id      文章ID
     * @param request 更新请求
     * @return 更新后的文章响应
     */
    public ArticleResponse update(String id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("文章不存在: " + id));

        if (request.getTitle() != null) {
            article.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            article.setContent(request.getContent());
        }
        if (request.getTagIds() != null) {
            article.setTagIds(request.getTagIds());
        }
        if (request.getStatus() != null) {
            article.setStatus(request.getStatus());
        }

        Article saved = articleRepository.save(article);
        syncToEs(saved);
        return ArticleResponse.from(saved);
    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     */
    public void delete(String id) {
        articleRepository.deleteById(id);
        articleSearchRepository.deleteById(id);
    }

    /**
     * 根据ID获取文章
     *
     * @param id 文章ID
     * @return 文章响应
     */
    public ArticleResponse getById(String id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("文章不存在: " + id));
        return ArticleResponse.from(article);
    }

    /**
     * 分页查询文章列表
     *
     * @param pageRequest 分页请求
     * @return 分页结果
     */
    public PageResult<ArticleResponse> list(PageRequest pageRequest) {
        pageRequest.validate();
        org.springframework.data.domain.PageRequest springPageRequest = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Article> page = articleRepository.findByStatus(Article.Status.PUBLISHED.getValue(), springPageRequest);
        List<ArticleResponse> list = page.getContent().stream()
                .map(ArticleResponse::from)
                .toList();

        return PageResult.of(list, page.getTotalElements(), pageRequest.getPage(), pageRequest.getSize());
    }

    /**
     * 同步文章到 ES
     *
     * @param article MongoDB 文章实体
     */
    public void syncToEs(Article article) {
        ArticleDocument document = ArticleDocument.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthorId())
                .tagIds(article.getTagIds())
                .createdAt(article.getCreatedAt())
                .build();
        articleSearchRepository.save(document);
    }

    /**
     * 同步所有已发布文章到 ES
     */
    public void syncAllToEs() {
        List<Article> articles = articleRepository.findByTagIdsInAndStatus(
                null, Article.Status.PUBLISHED.getValue());
        List<ArticleDocument> documents = articles.stream()
                .map(article -> ArticleDocument.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .authorId(article.getAuthorId())
                        .tagIds(article.getTagIds())
                        .createdAt(article.getCreatedAt())
                        .build())
                .toList();
        articleSearchRepository.saveAll(documents);
    }
}
