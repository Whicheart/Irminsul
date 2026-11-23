package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.Avatar;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 角色仓储，对应 avatar 集合，提供列表页所需的投影查询 */
@Repository
public interface AvatarRepository extends MongoRepository<Avatar, Integer> {

    /** 角色列表需要的字段子集 */
    interface AvatarBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getQualityType();

        String getWeaponType();

        String getIconName();
    }

    /**
     * 查询全部角色的简要投影
     *
     * @return 仅含 ID、名称哈希、品质、武器类型与头像名的列表
     */
    @Query(value = "{}", fields = "{ 'nameTextMapHash': 1, 'qualityType': 1, 'weaponType': 1, 'iconName': 1 }")
    List<AvatarBriefView> findAllBrief();
}
