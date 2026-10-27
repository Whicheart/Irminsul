package com.genshin.lunar.repository;

import com.genshin.lunar.entity.Fetters;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FettersRepository extends MongoRepository<Fetters, String> {
    // 按角色 ID 查语音，先按类型再按编号排序
    List<Fetters> findByAvatarIdOrderByTypeAscFetterIdAsc(Integer avatarId);
}
