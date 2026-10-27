package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCurveRepository extends MongoRepository<AvatarCurve, Integer> {}
