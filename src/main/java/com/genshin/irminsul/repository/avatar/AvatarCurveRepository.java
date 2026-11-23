package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarCurve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色属性成长曲线仓储，对应 avatar_curve 集合，主键为等级 */
@Repository
public interface AvatarCurveRepository extends MongoRepository<AvatarCurve, Integer> {}
