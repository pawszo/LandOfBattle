package com.pawszo.keyboardking.dev.repository;

import com.pawszo.keyboardking.dev.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Role getByName(String name);
}
