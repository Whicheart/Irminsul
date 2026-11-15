package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCurveRepository extends MongoRepository<AvatarCurve, Integer> {}
