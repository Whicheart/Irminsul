package com.genshin.irminsul.repository.fetter;

import com.genshin.irminsul.entity.fetter.FetterStory;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterStoryRepository extends MongoRepository<FetterStory, String> {
    // 按角色 ID 查故事，按编号排序
    List<FetterStory> findByAvatarIdOrderByFetterIdAsc(Integer avatarId);
}
