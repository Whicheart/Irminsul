package com.genshin.irminsul.repository.reliquary;

import com.genshin.irminsul.entity.reliquary.ReliquaryStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 圣遗物故事仓储，对应 relic_story 集合 */
@Repository
public interface ReliquaryStoryRepository extends MongoRepository<ReliquaryStory, Integer> {}
