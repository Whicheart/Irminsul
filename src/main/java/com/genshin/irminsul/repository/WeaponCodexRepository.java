package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.WeaponCodex;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponCodexRepository extends MongoRepository<WeaponCodex, Integer> {

    WeaponCodex findByWeaponId(Integer weaponId);

    interface WeaponCodexBriefView {

        Integer getId();

        Integer getWeaponId();

        Integer getSortOrder();
    }

    @Query(value = "{}", fields = "{ 'weaponId': 1, 'sortOrder': 1 }")
    List<WeaponCodexBriefView> findAllBrief();

    @Query(value = "{ 'weaponId': { $in: ?0 } }", fields = "{ 'weaponId': 1, 'sortOrder': 1 }")
    List<WeaponCodexBriefView> findBriefByWeaponIdIn(Collection<Integer> weaponIds);
}
