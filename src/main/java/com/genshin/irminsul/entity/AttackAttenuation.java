package com.genshin.irminsul.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "attack_attenuation")
public class AttackAttenuation {
    @Id
    private String id;

    // 衰减与破韧序列
    @Field("resetCycle")
    private Double resetCycle;

    @Field("durabilitySequence")
    private List<Double> durabilitySequence;

    @Field("damageSequence")
    private List<Double> damageSequence;

    @Field("enbreakSequence")
    private List<Double> enbreakSequence;
}
