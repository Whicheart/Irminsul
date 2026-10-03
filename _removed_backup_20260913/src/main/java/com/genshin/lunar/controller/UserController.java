package com.genshin.lunar.controller;

import com.genshin.lunar.entity.User;
import com.genshin.lunar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) { //把前端传的 JSON 转成 LoginRequest 对象，最终 Map 变成 JSON 给前端
        Map<String, Object> result = new HashMap<>();

        // 密码解码
        String password;
        try {
            byte[] decoded = Base64.getDecoder().decode(request.getPassword());
            password = new String(decoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            result.put("code", 400);
//            result.put("msg", "密码错误");
            return result;
        }

        // 登录验证
        User user = userService.login(request.getUsername(), password);
        if(user == null) {
            result.put("code", 401); // 401 未授权
//            result.put("msg", "用户名或密码错误");
            return result;
        }

        result.put("code", 200);
//        result.put("msg", "登陆成功");
        result.put("data", Map.of(
//                "id", user.getId(),
                "username", user.getUsername(),
                "group", user.getGroup()
        ));

        return result;
    }
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
