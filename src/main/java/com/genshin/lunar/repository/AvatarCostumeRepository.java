package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarCostume;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCostumeRepository extends MongoRepository<AvatarCostume, Integer> {
    // 按角色 ID 查其全部装扮
    List<AvatarCostume> findByCharacterId(Integer characterId);
}
