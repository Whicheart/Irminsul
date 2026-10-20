package com.genshin.lunar.service.impl;

import com.genshin.lunar.dto.AvatarList;
import com.genshin.lunar.service.AvatarService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final MongoTemplate mongoTemplate;

    public AvatarServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Map<String, Object>> searchAvatar(AvatarList query) {
        Criteria criteria = new Criteria();

        // 隐藏 beta 条目
        criteria.and("beta").ne(true);

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
        }

        Query mongoQuery = new Query(criteria);
        mongoQuery.with(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));

        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "avatar");
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
    public List<Map<String, Object>> searchAvatarSimple(AvatarList query) {
        Criteria criteria = new Criteria();

        // 隐藏 beta 条目
        criteria.and("beta").ne(true);

        if (query.getName() != null && !query.getName().isEmpty()) {
            criteria.and("name").regex(".*" + query.getName() + ".*", "i");
        }
        if (query.getStar() != null && !query.getStar().isEmpty()) {
            criteria.and("star").in(query.getStar());
        }
        if (query.getElem() != null && !query.getElem().isEmpty()) {
            criteria.and("elem").in(query.getElem());
        }
        if (query.getWeapon() != null && !query.getWeapon().isEmpty()) {
            criteria.and("weapon").in(query.getWeapon());
        }

        Query mongoQuery = new Query(criteria);
        mongoQuery.with(
                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "_id"));

        // 只查询需要的字段：_id, name, star, elem, weapon
        mongoQuery.fields().include("name", "star", "elem", "weapon");

        List<org.bson.Document> docs = mongoTemplate.find(mongoQuery, org.bson.Document.class, "avatar");
        List<Map<String, Object>> result = new ArrayList<>();
        for (org.bson.Document doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", doc.get("name"));
            map.put("elem", doc.get("elem"));
            map.put("weapon", doc.get("weapon"));
            result.add(map);
        }
        return result;
    }
}
