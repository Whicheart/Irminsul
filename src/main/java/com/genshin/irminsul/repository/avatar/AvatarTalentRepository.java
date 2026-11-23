package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarTalent;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色命座仓储，对应 avatar_talent 集合 */
@Repository
public interface AvatarTalentRepository extends MongoRepository<AvatarTalent, Integer> {
    // 按 ID 集合批量查（一次取一个角色的 6 个命座）
    List<AvatarTalent> findByIdIn(Collection<Integer> ids);
}
