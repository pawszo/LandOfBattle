package com.pawszo.keyboardking.dev.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ScoreDTO {
    private Long id;
    private Map<String, String> stats;
    private String nickname;
    private String game;

    public String getNickname() {
        return this.nickname;
    }
}
