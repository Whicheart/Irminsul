package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.EquipAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipAffixRepository extends MongoRepository<EquipAffix, Integer> {}
