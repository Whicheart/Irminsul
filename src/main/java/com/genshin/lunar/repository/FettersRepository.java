package com.genshin.lunar.repository;

import com.genshin.lunar.entity.Fetters;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FettersRepository extends MongoRepository<Fetters, String> {
    // 按角色 ID 查语音，先按类型再按编号排序
    List<Fetters> findByAvatarIdOrderByTypeAscFetterIdAsc(Integer avatarId);
}
