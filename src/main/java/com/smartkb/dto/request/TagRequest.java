package com.smartkb.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签创建/更新请求
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {

    /**
     * 标签ID，更新时传入
     */
    private String id;

    /**
     * 标签名称，唯一
     */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 20, message = "标签名称最多 20 字符")
    private String name;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 排序权重
     */
    private Integer sort;
}
