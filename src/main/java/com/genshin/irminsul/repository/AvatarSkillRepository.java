package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.AvatarSkill;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkillRepository extends MongoRepository<AvatarSkill, Integer> {

    interface AvatarSkillBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getDescTextMapHash();

        String getExtraDescTextMapHash();

        String getDescExtraTextMapHash();

        String getSkillIcon();

        Integer getProudSkillGroupId();
    }

    @Query(
            value = "{ '_id': { $in: ?0 } }",
            fields = "{ 'nameTextMapHash': 1, 'descTextMapHash': 1, 'extraDescTextMapHash': 1, "
                    + "'descExtraTextMapHash': 1, 'skillIcon': 1, 'proudSkillGroupId': 1 }")
    List<AvatarSkillBriefView> findBriefByIdIn(Collection<Integer> ids);
}
