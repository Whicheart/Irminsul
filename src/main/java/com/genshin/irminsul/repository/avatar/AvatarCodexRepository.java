package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarCodex;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarCodexRepository extends MongoRepository<AvatarCodex, Integer> {

    interface AvatarCodexBriefView {

        Integer getId();

        Integer getSortId();
    }

    @Query(value = "{}", fields = "{ 'sortId': 1 }")
    List<AvatarCodexBriefView> findAllBrief();
}
