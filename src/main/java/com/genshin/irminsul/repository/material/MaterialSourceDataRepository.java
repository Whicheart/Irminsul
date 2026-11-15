package com.genshin.irminsul.repository.material;

import com.genshin.irminsul.entity.material.MaterialSourceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialSourceDataRepository extends MongoRepository<MaterialSourceData, Integer> {}
