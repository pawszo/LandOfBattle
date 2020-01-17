package com.pawszo.keyboardking.dev.config;

import com.pawszo.keyboardking.dev.dto.CreatePrivilegeDTO;
import com.pawszo.keyboardking.dev.dto.CreateWordSetDTO;
import com.pawszo.keyboardking.dev.mapper.PrivilegeMapper;
import com.pawszo.keyboardking.dev.mapper.WordMapper;
import com.pawszo.keyboardking.dev.model.Privilege;
import com.pawszo.keyboardking.dev.model.Role;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.PrivilegeRepository;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import com.pawszo.keyboardking.dev.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
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

    @Autowired
    private WordService wordService;
    @Autowired
    private WordMapper wordMapper;

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
        createNecessaryWordSets();


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

    public void createNecessaryWordSets() {
        CreateWordSetDTO en1 = new CreateWordSetDTO("If you could not find the words you were looking for please submit feedback or leave a comment below. Let me know what word list you could not find and be sure to get it fixed up for you", "EN");
        CreateWordSetDTO en2 = new CreateWordSetDTO("bytes that express a binary integer that is not just a character is a binary file not plain text by even the loosest common usages. Put another way, translating a plain text file to a character encoding that uses entirely different number to represent characters", "EN");
        CreateWordSetDTO en3 = new CreateWordSetDTO("was not so useful in England, and the accented characters used in Spanish, French, German, and many other languages were entirely unavailable in", "EN");
        CreateWordSetDTO en4 = new CreateWordSetDTO("Many other organisations developed variations on these, and for many years Windows and Macintosh computers used incompatible variations", "EN");
        CreateWordSetDTO en5 = new CreateWordSetDTO("cross comparison sniff happen preserve cattle pray defiant cup zesty appear slippery chin meat nondescript challenge club battle license important government crown next lewd prevent husky license uptight round race innocent thaw aquatic ignorant rock aware previous letters lighten synonymous camera analyse smoke tip fat seashore sign damaged side moan", "EN");
        List<CreateWordSetDTO> setList = new ArrayList<>();
        setList.addAll(Arrays.asList(en1, en2, en3, en4, en5));
        setList.stream().forEach(s -> wordService.addWords(s));
    }


}
