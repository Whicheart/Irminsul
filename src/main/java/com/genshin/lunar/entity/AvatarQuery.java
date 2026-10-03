package com.genshin.lunar.entity;

import lombok.Data;

import java.util.List;

@Data
public class AvatarQuery {
    private String name;
    private List<Integer> star;
    private List<String> elem;
    private List<String> weapon;
}
