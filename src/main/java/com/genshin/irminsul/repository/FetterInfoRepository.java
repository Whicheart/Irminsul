package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.FetterInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FetterInfoRepository extends MongoRepository<FetterInfo, Integer> {

    interface FetterInfoBriefView {

        Integer getId();
        String getAvatarVisionBeforTextMapHash();
    }

    @Query(value = "{}", fields = "{ 'avatarVisionBeforTextMapHash': 1 }")
    List<FetterInfoBriefView> findAllBrief();
}
