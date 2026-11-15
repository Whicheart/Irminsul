package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponStoryRepository extends MongoRepository<WeaponStory, Integer> {}
