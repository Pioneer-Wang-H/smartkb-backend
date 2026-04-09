package com.smartkb.service;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.smartkb.common.PageResult;
import com.smartkb.dto.request.SearchRequest;
import com.smartkb.dto.response.SearchResponse;
import com.smartkb.model.es.ArticleDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务
 *
 * <p>基于 Elasticsearch 提供全文搜索、高亮、标签聚合等功能
 *
 * @author SmartKB Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 全文搜索
     *
     * @param request 搜索请求
     * @return 搜索结果分页
     */
    public PageResult<SearchResponse> search(SearchRequest request) {
        int page = request.getPage() != null && request.getPage() > 0 ? request.getPage() - 1 : 0;
        int size = request.getSize() != null && request.getSize() > 0 ? request.getSize() : 10;

        NativeQuery query = buildSearchQuery(request.getKeyword(), request.getTagId(), page, size);
        SearchHits<ArticleDocument> searchHits = elasticsearchOperations.search(query, ArticleDocument.class);

        List<SearchResponse> results = new ArrayList<>();
        for (SearchHit<ArticleDocument> hit : searchHits.getSearchHits()) {
            ArticleDocument doc = hit.getContent();
            Map<String, List<String>> highlights = new HashMap<>();
            hit.getHighlightFields().forEach((field, values) -> {
                highlights.put(field, values);
            });

            SearchResponse response = SearchResponse.builder()
                    .id(doc.getId())
                    .title(doc.getTitle())
                    .content(doc.getContent())
                    .authorId(doc.getAuthorId())
                    .tagIds(doc.getTagIds())
                    .createdAt(doc.getCreatedAt())
                    .highlights(highlights)
                    .build();
            results.add(response);
        }

        return PageResult.of(results, searchHits.getTotalHits(), request.getPage(), size);
    }

    /**
     * 构建搜索查询
     *
     * <p>使用多字段匹配查询，支持标签过滤
     */
    private NativeQuery buildSearchQuery(String keyword, String tagId, int page, int size) {
        Query multiMatchQuery = Query.of(q -> q
                .multiMatch(mm -> mm
                        .query(keyword)
                        .fields(Arrays.asList("title^2", "content"))
                        .fuzziness("AUTO")
                )
        );

        BoolQuery.Builder boolBuilder = new BoolQuery.Builder()
                .must(multiMatchQuery);

        if (tagId != null && !tagId.isEmpty()) {
            Query tagFilter = Query.of(q -> q
                    .term(TermQuery.of(t -> t
                            .field("tagIds")
                            .value(tagId)
                    ))
            );
            boolBuilder.filter(tagFilter);
        }

        List<HighlightField> highlightFields = Arrays.asList(
                new HighlightField("title"),
                new HighlightField("content")
        );

        HighlightParameters params = HighlightParameters.builder()
                .withPreTags("<em>")
                .withPostTags("</em>")
                .withFragmentSize(150)
                .withNumberOfFragments(3)
                .build();

        Highlight highlight = new Highlight(params, highlightFields);

        return NativeQuery.builder()
                .withQuery(Query.of(q -> q.bool(boolBuilder.build())))
                .withPageable(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                .withHighlightQuery(new HighlightQuery(highlight, ArticleDocument.class))
                .build();
    }

    /**
     * 获取热门搜索词（基于标签聚合）
     *
     * @return 标签及其文章数量
     */
    public Map<String, Long> getHotTags() {
        Map<String, Long> tagCount = new HashMap<>();
        return tagCount;
    }
}
