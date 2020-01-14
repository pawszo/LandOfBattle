package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateWordDTO {

    private String name;
    private String language;
}
