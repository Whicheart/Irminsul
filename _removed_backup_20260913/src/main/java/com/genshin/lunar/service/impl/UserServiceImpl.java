package com.genshin.lunar.service.impl;

import com.genshin.lunar.entity.User;
import com.genshin.lunar.mapper.UserMapper;
import com.genshin.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(String username, String password){
        User user = userMapper.searchByUsername(username);
        if(user == null){
            return null;
        }
        if(!password.equals(user.getPassword())){
            return null;
        }
        return user;
    }
}
