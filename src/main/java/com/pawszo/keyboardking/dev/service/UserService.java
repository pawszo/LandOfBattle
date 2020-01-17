package com.pawszo.keyboardking.dev.service;

import com.pawszo.keyboardking.dev.dto.CreateUserDTO;
import com.pawszo.keyboardking.dev.dto.PasswordUpdateUserDTO;
import com.pawszo.keyboardking.dev.dto.UserDTO;
import com.pawszo.keyboardking.dev.mapper.UserMapper;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.ScoreRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreRepository scoreRepository;

   /* @Transactional
    public UserDTO registerUser(Map<String, String> body) throws DataInvalid {
        return userMapper.mapToDTO(userRepository.save(userMapper.createModel(body)));
    }
*/


    public UserDTO registerUser(CreateUserDTO userDTO) {
        if (userDTO.getNickname().isEmpty()) {
            userDTO.setNickname("anonym" + System.currentTimeMillis());
        }
        User user = userRepository.save(userMapper.mapToModel(userDTO));
        return userMapper.mapToDTO(user);
    }

    public List<UserDTO> getUsersList() {
        return userRepository
                .findAll()
                .stream()
                .map(u -> userMapper.mapToDTO(u))
                .collect(Collectors.toList());
    }

    public Set<String> getNicknameSet() {
        return userRepository.findAll().stream().map(u -> u.getNickname()).collect(Collectors.toSet());
    }

    public UserDTO updatePassword(PasswordUpdateUserDTO dto) {
        User user = userRepository.getOne(dto.getId());
        user.setState("active");
        user.setPassword(dto.getPasswordConfirmation());
        userRepository.save(user);
        return userMapper.mapToDTO(user);
    }


    public Boolean isUserDetailUnique(String phrase, String type) {
        if (type.equalsIgnoreCase("nick")) {

            boolean nickAvailable = !userRepository.existsByNickname(phrase);
            boolean scoreNickAvailable = !scoreRepository.existsByNickname(phrase);
            return (nickAvailable && scoreNickAvailable);
        } else if (type.equalsIgnoreCase("email")) {

            return !userRepository.existsByEmail(phrase);
        } else {
            return false;
        }
    }
}
