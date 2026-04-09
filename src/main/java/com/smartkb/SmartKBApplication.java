package com.smartkb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SmartKB 应用主类
 *
 * <p>智能知识库搜索平台，提供文章发布、全文检索、标签聚合等功能
 *
 * @author SmartKB Team
 */
@SpringBootApplication
public class SmartKBApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartKBApplication.class, args);
    }
}
