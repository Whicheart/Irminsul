package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AnimalCodex;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalCodexRepository extends MongoRepository<AnimalCodex, Integer> {}
