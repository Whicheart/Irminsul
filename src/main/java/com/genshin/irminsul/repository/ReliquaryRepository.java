package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Reliquary;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliquaryRepository extends MongoRepository<Reliquary, Integer> {

    interface ReliquaryBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getDescTextMapHash();

        String getIcon();
    }

    @Query(value = "{ '_id': { $in: ?0 } }", fields = "{ 'nameTextMapHash': 1, 'descTextMapHash': 1, 'icon': 1 }")
    List<ReliquaryBriefView> findBriefByIdIn(Collection<Integer> ids);
}
