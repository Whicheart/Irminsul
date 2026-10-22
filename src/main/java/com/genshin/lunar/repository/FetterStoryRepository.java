package com.genshin.lunar.repository;

import com.genshin.lunar.entity.FetterStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetterStoryRepository extends MongoRepository<FetterStory, String> {
    // 按角色 ID 查故事，按编号排序
    List<FetterStory> findByAvatarIdOrderByFetterIdAsc(Integer avatarId);
}
