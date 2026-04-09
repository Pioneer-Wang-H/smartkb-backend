package com.smartkb.repository.mongo;

import com.smartkb.model.mongo.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 标签 MongoDB 持久层
 *
 * @author SmartKB Team
 */
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    /**
     * 根据标签名称查询标签
     *
     * @param name 标签名称
     * @return 标签实体
     */
    Optional<Tag> findByName(String name);

    /**
     * 根据标签ID列表查询标签
     *
     * @param ids 标签ID列表
     * @return 标签列表
     */
    List<Tag> findByIdIn(List<String> ids);

    /**
     * 根据标签名称列表查询标签
     *
     * @param names 标签名称列表
     * @return 标签列表
     */
    List<Tag> findByNameIn(List<String> names);
}
