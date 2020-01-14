package com.pawszo.keyboardking.dev.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrivilegeDTO {

    private Long id;
    private String name;
    private List<Long> roleIds = new ArrayList<>();
}
