package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, Integer> {

    interface AvatarBriefView {

        Integer getId();
        String getNameTextMapHash();
        String getQualityType();
        String getWeaponType();
        String getIconName();
    }

    @Query(value = "{}", fields = "{ 'nameTextMapHash': 1, 'qualityType': 1, 'weaponType': 1, 'iconName': 1 }")
    List<AvatarBriefView> findAllBrief();
}
