package com.genshin.irminsul.repository.material;

import com.genshin.irminsul.entity.material.Material;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 材料仓储，对应 material 集合，提供展示字段的投影查询 */
@Repository
public interface MaterialRepository extends MongoRepository<Material, Integer> {

    /** 材料展示需要的字段子集 */
    interface MaterialBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getIcon();
    }

    /**
     * 按 ID 集合批量查询材料简要投影
     *
     * @param ids 材料 ID 集合
     * @return 材料投影列表
     */
    @Query(value = "{ '_id': { $in: ?0 } }", fields = "{ 'nameTextMapHash': 1, 'icon': 1 }")
    List<MaterialBriefView> findBriefByIdIn(Collection<Integer> ids);
}
