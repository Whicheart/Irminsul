package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.FetterCharacterCard;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterCharacterCardRepository extends MongoRepository<FetterCharacterCard, String> {
    // 按角色 ID 查名片
    List<FetterCharacterCard> findByAvatarId(Integer avatarId);
}
