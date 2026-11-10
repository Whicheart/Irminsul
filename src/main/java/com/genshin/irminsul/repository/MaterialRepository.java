package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.Material;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends MongoRepository<Material, Integer> {

    interface MaterialBriefView {

        Integer getId();

        String getNameTextMapHash();

        String getIcon();
    }

    @Query(value = "{ '_id': { $in: ?0 } }", fields = "{ 'nameTextMapHash': 1, 'icon': 1 }")
    List<MaterialBriefView> findBriefByIdIn(Collection<Integer> ids);
}
