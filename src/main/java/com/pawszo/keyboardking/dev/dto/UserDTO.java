package com.pawszo.keyboardking.dev.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class UserDTO {

    private Long id;
    private String nickname;
    private String email;
    //"active", "passwordChange", "deleted", "locked"
    private String state;
    private List<Long> rolesIds = new ArrayList<>();

    public void addRoleId(Long roleId) {
        rolesIds.add(roleId);
    }

    public UserDTO withState(String state) {
        this.setState(state);
        return this;
    }

}
