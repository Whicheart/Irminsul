package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Weapon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, Integer> {}
