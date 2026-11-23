package com.genshin.irminsul.repository.monster;

import com.genshin.irminsul.entity.monster.MonsterSpecialName;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 怪物特殊名仓储，对应 monster_special_name 集合 */
@Repository
public interface MonsterSpecialNameRepository extends MongoRepository<MonsterSpecialName, Integer> {
    /**
     * 按特殊名分组查询，按 ID 升序返回
     *
     * @param specialNameLabID 特殊名分组 ID
     * @return 特殊名列表
     */
    List<MonsterSpecialName> findBySpecialNameLabIDOrderByIdAsc(Integer specialNameLabID);
}
