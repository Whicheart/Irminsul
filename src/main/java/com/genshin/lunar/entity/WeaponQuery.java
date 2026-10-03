package com.genshin.lunar.entity;

import lombok.Data;

import java.util.List;

@Data
public class WeaponQuery {
    private String name;
    private List<Integer> star;
    private List<String> type;
}
