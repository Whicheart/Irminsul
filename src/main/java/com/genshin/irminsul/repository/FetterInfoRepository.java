package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.FetterInfo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterInfoRepository extends MongoRepository<FetterInfo, Integer> {

    interface FetterInfoBriefView {

        Integer getId();

        String getAvatarVisionBeforTextMapHash();
    }

    @Query(value = "{}", fields = "{ 'avatarVisionBeforTextMapHash': 1 }")
    List<FetterInfoBriefView> findAllBrief();
}
