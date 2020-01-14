package com.pawszo.keyboardking.dev.config;

import com.pawszo.keyboardking.dev.dto.CreatePrivilegeDTO;
import com.pawszo.keyboardking.dev.mapper.PrivilegeMapper;
import com.pawszo.keyboardking.dev.model.Privilege;
import com.pawszo.keyboardking.dev.model.Role;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.PrivilegeRepository;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

//@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege adminPrivilege
                = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");

        List<Privilege> adminRole = Arrays.asList(readPrivilege, writePrivilege, adminPrivilege);
        List<Privilege> userRole = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminRole);
        createRoleIfNotFound("ROLE_USER", userRole);

        Role adminR = roleRepository.getByName("ROLE_ADMIN");
        Role userR = roleRepository.getByName("ROLE_USER");
        User user = new User();
        user.setNickname("admin");
        user.setPassword(passwordEncoder.encode("wiedzmin3"));
        user.setEmail("paulszoltysek@gmail.com");
        user.setRoles(Arrays.asList(adminR, userR));
        user.setState("active");
        userRepository.save(user);


        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name).orElse(null);
        if (privilege == null) {
            privilege = privilegeMapper.createModel(new CreatePrivilegeDTO(name));
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(String name, List<Privilege> privileges) {

        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
