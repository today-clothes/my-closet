package com.oclothes.domain.user.dao;

import com.oclothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail_Value(@NonNull String value);

    boolean existsByEmail_Value(@NonNull String value);

    Optional<User> findById(Long id);
}
