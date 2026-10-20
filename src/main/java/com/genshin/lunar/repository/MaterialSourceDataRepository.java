package com.genshin.lunar.repository;

import com.genshin.lunar.entity.MaterialSourceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialSourceDataRepository extends MongoRepository<MaterialSourceData, Integer> {}