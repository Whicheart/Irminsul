package com.genshin.irminsul.repository;

import com.genshin.irminsul.entity.FetterInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetterInfoRepository extends MongoRepository<FetterInfo, Integer> {}
