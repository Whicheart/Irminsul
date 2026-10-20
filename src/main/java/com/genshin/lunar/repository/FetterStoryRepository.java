package com.genshin.lunar.repository;

import com.genshin.lunar.entity.FetterStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetterStoryRepository extends MongoRepository<FetterStory, String> {
    List<FetterStory> findByAvatarIdOrderByFetterIdAsc(Integer avatarId);
}
