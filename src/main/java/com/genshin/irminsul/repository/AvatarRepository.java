package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Avatar;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

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
