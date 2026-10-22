package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AttactAttenuation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttactAttenuationRepository extends MongoRepository<AttactAttenuation, String> {}
