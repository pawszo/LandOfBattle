package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateScoreDTO implements ICreateScoreDTO {

    private String wordsPerMinute;
    private String charsPerMinute;
    private String nickname;
    private String game;

    @Override
    public Map<String, String> getStats() {
        return null;
    }
}
