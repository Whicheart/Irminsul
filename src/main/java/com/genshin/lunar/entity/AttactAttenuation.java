package com.genshin.lunar.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "attack_attenuation")
public class AttactAttenuation {
    @Id
    private String id;

    @Field("resetCycle")
    private Double resetCycle;
    @Field("durabilitySequence")
    private List<Double> durabilitySequence;
    @Field("damageSequence")
    private List<Double> damageSequence;
    @Field("enbreakSequence")
    private List<Double> enbreakSequence;
}
