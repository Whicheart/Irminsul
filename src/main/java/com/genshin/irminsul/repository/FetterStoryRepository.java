package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.FetterStory;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterStoryRepository extends MongoRepository<FetterStory, String> {
    // 按角色 ID 查故事，按编号排序
    List<FetterStory> findByAvatarIdOrderByFetterIdAsc(Integer avatarId);
}
