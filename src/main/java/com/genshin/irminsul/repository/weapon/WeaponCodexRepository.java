package com.genshin.irminsul.repository.weapon;

import com.genshin.irminsul.entity.weapon.WeaponCodex;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 武器图鉴仓储，对应 weapon_codex 集合，提供列表与详情所需的投影查询 */
@Repository
public interface WeaponCodexRepository extends MongoRepository<WeaponCodex, Integer> {

    /**
     * 按武器 ID 查询图鉴条目
     *
     * @param weaponId 武器 ID
     * @return 图鉴条目；不存在时返回 null
     */
    WeaponCodex findByWeaponId(Integer weaponId);

    /** 武器图鉴列表需要的字段子集 */
    interface WeaponCodexBriefView {

        Integer getId();

        Integer getWeaponId();

        Integer getSortOrder();
    }

    /**
     * 查询全部图鉴条目的简要投影
     *
     * @return 图鉴投影列表
     */
    @Query(value = "{}", fields = "{ 'weaponId': 1, 'sortOrder': 1 }")
    List<WeaponCodexBriefView> findAllBrief();

    /**
     * 按武器 ID 集合批量查询图鉴投影
     *
     * @param weaponIds 武器 ID 集合
     * @return 图鉴投影列表
     */
    @Query(value = "{ 'weaponId': { $in: ?0 } }", fields = "{ 'weaponId': 1, 'sortOrder': 1 }")
    List<WeaponCodexBriefView> findBriefByWeaponIdIn(Collection<Integer> weaponIds);
}
