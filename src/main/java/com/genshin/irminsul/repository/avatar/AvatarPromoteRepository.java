package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色突破配置仓储，对应 avatar_promote 集合 */
@Repository
public interface AvatarPromoteRepository extends MongoRepository<AvatarPromote, Integer> {}
