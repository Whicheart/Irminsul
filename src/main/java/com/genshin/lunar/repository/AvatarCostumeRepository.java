package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarCostume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarCostumeRepository extends MongoRepository<AvatarCostume, Integer> {
    List<AvatarCostume> findByCharacterId(Integer characterId);
}
