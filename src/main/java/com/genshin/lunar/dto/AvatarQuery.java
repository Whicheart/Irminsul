package com.genshin.lunar.dto;

import java.util.List;
import lombok.Data;

@Data
public class AvatarQuery {
    private String name;
    private List<Integer> star;
    private List<String> elem;
    private List<String> weapon;
}
