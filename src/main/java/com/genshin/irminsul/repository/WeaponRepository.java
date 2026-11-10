package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Weapon;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, Integer> {

    interface WeaponBriefView {

        Integer getId();

        String getNameTextMapHash();

        Integer getRankLevel();

        String getWeaponType();

        String getIcon();
    }

    @Query(value = "{}", fields = "{ 'nameTextMapHash': 1, 'rankLevel': 1, 'weaponType': 1, 'icon': 1 }")
    List<WeaponBriefView> findAllBrief();
}
