package com.pawszo.keyboardking.dev.repository;

import com.pawszo.keyboardking.dev.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);
}
