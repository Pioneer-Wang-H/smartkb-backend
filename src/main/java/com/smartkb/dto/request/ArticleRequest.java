package com.smartkb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章创建/更新请求
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

    /**
     * 文章ID，更新时传入
     */
    private String id;

    /**
     * 文章标题，2-100 字符
     */
    @NotBlank(message = "标题不能为空")
    @Size(min = 2, max = 100, message = "标题长度需在 2-100 字符之间")
    private String title;

    /**
     * 文章正文
     */
    @NotBlank(message = "正文不能为空")
    private String content;

    /**
     * 标签ID列表
     */
    private List<String> tagIds;

    /**
     * 文章状态：0-草稿，1-已发布
     */
    private Integer status;
}
