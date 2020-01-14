package com.pawszo.keyboardking.dev.mapper;

import com.pawszo.keyboardking.dev.dto.CreateUserDTO;
import com.pawszo.keyboardking.dev.dto.UserDTO;
import com.pawszo.keyboardking.dev.model.Role;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /* public User createModel(Map<String, String> body) throws DataInvalid {
         User user = new User();
         user.addRole(roleRepository.findByName("basic").orElseThrow(DataInvalid::new));
         if(!body.containsKey("nickname") || !body.containsKey("password") || !body.containsKey("email")) {
             throw new DataInvalid("At least 1 field has errors.");
         }

         user.setNickname(body.get("nickname"));
         user.setPassword(body.get("password"));
         user.setEmail(body.get("email"));
         return user;
     }
 */
    @Transactional
    public User mapToModel(CreateUserDTO userDTO) {
        User user = new User();
        user.setNickname(userDTO.getNickname());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setState("active");
        user.addRole(roleRepository.findByName("ROLE_USER").orElse(new Role("ROLE_USER")));
        return user;

    }

    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNickname(user.getNickname());
        userDTO.setState(user.getState());
        userDTO.setRolesIds(user.getRoles().stream().map(r -> r.getId()).collect(Collectors.toList()));
        return userDTO;
    }

}
