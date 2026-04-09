package com.smartkb.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应封装
 *
 * <p>所有 API 统一返回此格式
 *
 * @author SmartKB Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 状态码：200-成功，400-参数错误，401-未登录，500-服务器异常
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(200)
                .message("success")
                .data(data)
                .build();
    }

    /**
     * 成功响应（带消息）
     */
    public static <T> Result<T> success(T data, String message) {
        return Result.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> error(Integer code, String message) {
        return Result.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

    /**
     * 参数错误
     */
    public static <T> Result<T> badRequest(String message) {
        return error(400, message);
    }

    /**
     * 服务器异常
     */
    public static <T> Result<T> serverError(String message) {
        return error(500, message);
    }
}
