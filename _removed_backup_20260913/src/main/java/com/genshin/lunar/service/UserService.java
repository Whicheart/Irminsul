package com.genshin.lunar.service;

import com.genshin.lunar.entity.User;

public interface UserService {
    User login(String username, String password);
}
