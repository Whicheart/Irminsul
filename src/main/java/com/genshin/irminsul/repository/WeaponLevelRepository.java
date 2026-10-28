package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.WeaponLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponLevelRepository extends MongoRepository<WeaponLevel, Integer> {}
