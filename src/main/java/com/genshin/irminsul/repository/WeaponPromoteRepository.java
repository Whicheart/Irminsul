package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.WeaponPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponPromoteRepository extends MongoRepository<WeaponPromote, Integer> {}
