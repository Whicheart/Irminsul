package com.genshin.irminsul.repository.avatar;

import com.genshin.irminsul.entity.avatar.AvatarCodex;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** 角色图鉴仓储，对应 avatar_codex 集合，提供列表页所需的投影查询 */
@Repository
public interface AvatarCodexRepository extends MongoRepository<AvatarCodex, Integer> {

    /** 图鉴列表需要的字段：角色 ID 与排序号 */
    interface AvatarCodexBriefView {

        Integer getId();

        Integer getSortId();
    }

    /**
     * 查询全部图鉴条目的简要投影
     *
     * @return 仅含 ID 与排序号的列表
     */
    @Query(value = "{}", fields = "{ 'sortId': 1 }")
    List<AvatarCodexBriefView> findAllBrief();
}
