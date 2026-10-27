package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AttactAttenuation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttactAttenuationRepository extends MongoRepository<AttactAttenuation, String> {}
