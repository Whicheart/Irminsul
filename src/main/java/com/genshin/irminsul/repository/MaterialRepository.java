package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends MongoRepository<Material, Integer> {}
