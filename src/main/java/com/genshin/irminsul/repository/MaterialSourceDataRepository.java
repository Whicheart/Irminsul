package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.MaterialSourceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialSourceDataRepository extends MongoRepository<MaterialSourceData, Integer> {}
