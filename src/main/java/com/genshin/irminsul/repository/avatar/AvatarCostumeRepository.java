package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarCostume;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色装扮仓储，对应 avatar_costume 集合 */
@Repository
public interface AvatarCostumeRepository extends MongoRepository<AvatarCostume, Integer> {
    // 按角色 ID 查其全部装扮
    List<AvatarCostume> findByCharacterId(Integer characterId);
}
