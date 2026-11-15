package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarFlycloak;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarFlycloakRepository extends MongoRepository<AvatarFlycloak, Integer> {}
