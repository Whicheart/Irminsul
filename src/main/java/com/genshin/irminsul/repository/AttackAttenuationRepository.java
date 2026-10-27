package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AttackAttenuation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttackAttenuationRepository extends MongoRepository<AttackAttenuation, String> {}
