package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCurveRepository extends MongoRepository<AvatarCurve, Integer> {}
