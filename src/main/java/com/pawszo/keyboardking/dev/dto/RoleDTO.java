package com.pawszo.keyboardking.dev.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDTO {

    private Long id;
    private String name;
    private List<Long> usersIds = new ArrayList<>();
    private List<Long> privilegesIds = new ArrayList<>();

}
