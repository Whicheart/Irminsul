package com.genshin.irminsul.repository.material;

import com.genshin.irminsul.entity.material.MaterialSourceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** 材料来源仓储，对应 material_source_data 集合，记录材料的获取途径 */
@Repository
public interface MaterialSourceDataRepository extends MongoRepository<MaterialSourceData, Integer> {}
