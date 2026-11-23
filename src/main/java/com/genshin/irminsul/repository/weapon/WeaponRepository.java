package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.Weapon;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 武器仓储，对应 weapon 集合，提供列表页所需的投影查询 */
@Repository
public interface WeaponRepository extends MongoRepository<Weapon, Integer> {

    /** 武器列表需要的字段子集 */
    interface WeaponBriefView {

        Integer getId();

        String getNameTextMapHash();

        Integer getRankLevel();

        String getWeaponType();

        String getIcon();
    }

    /**
     * 查询全部武器的简要投影
     *
     * @return 武器投影列表
     */
    @Query(value = "{}", fields = "{ 'nameTextMapHash': 1, 'rankLevel': 1, 'weaponType': 1, 'icon': 1 }")
    List<WeaponBriefView> findAllBrief();
}
