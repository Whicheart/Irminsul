package com.genshin.lunar.repository;

import com.genshin.lunar.entity.AvatarTalent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AvatarTalentRepository extends MongoRepository<AvatarTalent, Integer> {
    List<AvatarTalent> findByIdIn(Collection<Integer> ids);
}
