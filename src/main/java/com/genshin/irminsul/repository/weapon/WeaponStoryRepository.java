package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 武器故事仓储，对应 weapon_story 集合 */
@Repository
public interface WeaponStoryRepository extends MongoRepository<WeaponStory, Integer> {}
