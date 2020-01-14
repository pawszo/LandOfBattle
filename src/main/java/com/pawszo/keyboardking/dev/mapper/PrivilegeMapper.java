package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.CreatePrivilegeDTO;
import com.pawszo.keyboardking.dev.dto.PrivilegeDTO;
import com.pawszo.keyboardking.dev.model.Privilege;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class PrivilegeMapper {

    @Autowired
    private RoleRepository roleRepository;

    public Privilege createModel(CreatePrivilegeDTO dto) {
        Privilege privilege = new Privilege();
        privilege.setName(dto.getName());
        return privilege;
    }

    @Transactional
    public Privilege mapToModel(PrivilegeDTO dto) {
        Privilege privilege = new Privilege();
        privilege.setId(dto.getId());
        privilege.setName(dto.getName());
        privilege.setRoles(dto.getRoleIds().stream().map(r -> roleRepository.getOne(r)).collect(Collectors.toList()));
        return privilege;
    }

    public PrivilegeDTO mapToDTO(Privilege privilege) {
        PrivilegeDTO dto = new PrivilegeDTO();
        dto.setId(privilege.getId());
        dto.setName(privilege.getName());
        dto.setRoleIds(privilege.getRoles().stream().map(r -> r.getId()).collect(Collectors.toList()));
        return dto;
    }
}
