package com.oclothes.domain.user.dao;

import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.domain.UserStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStyleRepository extends JpaRepository<UserStyle, Long> {
    List<UserStyle> findByUser(User savedUser);
}
