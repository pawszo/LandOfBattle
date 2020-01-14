package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.RoleDTO;
import com.pawszo.keyboardking.dev.model.Role;
import com.pawszo.keyboardking.dev.repository.PrivilegeRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;


    @Transactional
    public Role mapToModel(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setId(roleDTO.getId());
        role.setUsers(roleDTO.getUsersIds().stream().map(u -> userRepository.getOne(u)).collect(Collectors.toList()));
        role.setPrivileges(roleDTO.getPrivilegesIds().stream().map(p -> privilegeRepository.getOne(p)).collect(Collectors.toList()));
        return role;
    }

    public RoleDTO mapToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setUsersIds(role.getUsers().stream().map(u -> u.getId()).collect(Collectors.toList()));
        roleDTO.setPrivilegesIds(role.getPrivileges().stream().map(p -> p.getId()).collect(Collectors.toList()));
        return roleDTO;

    }
}
