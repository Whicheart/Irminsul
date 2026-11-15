package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarPromote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarPromoteRepository extends MongoRepository<AvatarPromote, Integer> {}
