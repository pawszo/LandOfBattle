package com.pawszo.keyboardking.dev.dto;

import java.util.Map;

public interface ICreateScoreDTO {

    Map<String, String> getStats();

    String getNickname();

    void setNickname(String nickname);

    String getGame();

    void setGame(String game);
}
