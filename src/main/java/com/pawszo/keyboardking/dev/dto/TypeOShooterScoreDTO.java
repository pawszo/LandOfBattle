package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOShooterScoreDTO implements ICreateScoreDTO {

    private String time;
    private String points;
    private String killedBy;
    private String language;
    private String nickname;
    private String game;


    @Override
    public Map<String, String> getStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("time", time);
        stats.put("points", points);
        stats.put("language", language);
        stats.put("killedBy", killedBy);
        return stats;
    }
}
