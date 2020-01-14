package com.pawszo.keyboardking.dev.repository;

import com.pawszo.keyboardking.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

}
