package com.genshin.lunar.repository;

import com.genshin.lunar.entity.FetterCharacterCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetterCharacterCardRepository extends MongoRepository<FetterCharacterCard, String> {
    // 按角色 ID 查名片
    List<FetterCharacterCard> findByAvatarId(Integer avatarId);
}
