package com.genshin.lunar.mapper;

import com.genshin.lunar.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMapper extends MongoRepository<User, String> {
    User searchByUsername(String username);
}
