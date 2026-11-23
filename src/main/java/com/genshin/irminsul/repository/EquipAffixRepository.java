package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.EquipAffix;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 套装效果仓储，对应 equip_affix 集合，一个套装包含多个档位的效果文本 */
@Repository
public interface EquipAffixRepository extends MongoRepository<EquipAffix, Integer> {}
