package com.genshin.irminsul.repository.fetter;

import com.genshin.irminsul.entity.fetter.FetterInfo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 角色资料仓储，对应 fetter_info 集合，提供角色详情所需的投影查询 */
@Repository
public interface FetterInfoRepository extends MongoRepository<FetterInfo, Integer> {

    /** 角色详情需要的字段子集 */
    interface FetterInfoBriefView {

        Integer getId();

        String getAvatarVisionBeforTextMapHash();
    }

    /**
     * 查询全部角色资料的简要投影
     *
     * @return 角色资料投影列表
     */
    @Query(value = "{}", fields = "{ 'avatarVisionBeforTextMapHash': 1 }")
    List<FetterInfoBriefView> findAllBrief();
}
