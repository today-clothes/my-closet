package com.oclothes.domain.closet.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
    List<Closet> findByUser(User user);
}
