package com.genshin.lunar.repository;

import com.genshin.lunar.entity.FetterInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterInfoRepository extends MongoRepository<FetterInfo, Integer> {}
