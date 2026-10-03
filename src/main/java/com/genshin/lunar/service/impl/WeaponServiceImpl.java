package com.genshin.lunar.service.impl;

import com.genshin.lunar.dto.WeaponQuery;
import com.genshin.lunar.service.WeaponService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class WeaponServiceImpl implements WeaponService {
    private static final Map<String, String> TYPE_PREFIX_MAP = Map.of(
            "sword", "11",
            "claymore", "12",
            "polearm", "13",
            "bow", "14",
            "catalyst", "15");

    private final MongoTemplate mongoTemplate;

    public WeaponServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Map<String, Object>> searchWeapon(WeaponQuery query) {
        Criteria criteria = new Criteria();

        // 隐藏 beta 条目
        criteria.and("beta").ne(true);

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
        }

        Query mongoQuery = new Query(criteria);
        mongoQuery.with(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));

        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "weapon");
        List<Map<String, Object>> result = new ArrayList<>();
        for (org.bson.Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.putAll(doc);
            map.put("id", doc.get("_id"));
            map.remove("_id");
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> searchWeaponSimple(WeaponQuery query) {
        Criteria criteria = new Criteria();

        // 隐藏 beta 条目
        criteria.and("beta").ne(true);

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
        }
        if (query.getStar() != null && !query.getStar().isEmpty()) {
            criteria.and("star").in(query.getStar());
        }
        if (query.getType() != null && !query.getType().isEmpty()) {
            List<Criteria> orList = new ArrayList<>();

            for (String typeName : query.getType()) {
                String prefix = TYPE_PREFIX_MAP.get(typeName);
                if (prefix != null) {
                    int min = Integer.parseInt(prefix + "000"); // 11000
                    int max = min + 1000; // 12000
                    orList.add(Criteria.where("_id").gte(min).lt(max));
                }
            }

            if (!orList.isEmpty()) {
                criteria.orOperator(orList.toArray(new Criteria[0]));
            }
        }

        Query mongoQuery = new Query(criteria);
        mongoQuery.with(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));

        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "weapon");
        List<Map<String, Object>> result = new ArrayList<>();
        for (org.bson.Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", doc.get("name"));
            result.add(map);
        }
        return result;
    }
}
