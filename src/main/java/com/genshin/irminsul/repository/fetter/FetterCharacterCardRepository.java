package com.genshin.irminsul.repository.fetter;

import com.genshin.irminsul.entity.fetter.FetterCharacterCard;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 角色好感名片仓储，对应 fetter_character_card 集合 */
@Repository
public interface FetterCharacterCardRepository extends MongoRepository<FetterCharacterCard, String> {
    // 按角色 ID 查名片
    List<FetterCharacterCard> findByAvatarId(Integer avatarId);
}
