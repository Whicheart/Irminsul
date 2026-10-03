package com.genshin.lunar.dto;

import java.util.List;
import lombok.Data;

@Data
public class WeaponQuery {
    private String name;
    private List<Integer> star;
    private List<String> type;
}
