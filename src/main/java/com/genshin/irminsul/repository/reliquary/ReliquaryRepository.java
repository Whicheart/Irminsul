package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.Reliquary;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 圣遗物仓储，对应 reliquary 集合，提供套装部件展示所需的投影查询 */
@Repository
public interface ReliquaryRepository extends MongoRepository<Reliquary, Integer> {

    /** 套装部件展示需要的字段子集 */
    interface ReliquaryBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getDescTextMapHash();

        String getIcon();
    }

    /**
     * 按 ID 集合批量查询部件简要投影
     *
     * @param ids 部件 ID 集合
     * @return 部件投影列表
     */
    @Query(value = "{ '_id': { $in: ?0 } }", fields = "{ 'nameTextMapHash': 1, 'descTextMapHash': 1, 'icon': 1 }")
    List<ReliquaryBriefView> findBriefByIdIn(Collection<Integer> ids);
}
