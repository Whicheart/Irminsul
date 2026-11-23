package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarFlycloak;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色风之翼仓储，对应 avatar_flycloak 集合 */
@Repository
public interface AvatarFlycloakRepository extends MongoRepository<AvatarFlycloak, Integer> {}
