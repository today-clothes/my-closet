package com.oclothes.domain.closet.dao;

import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
    List<Closet> findAllByUser(User user);

    Slice<Closet> findAllSliceByUser(@NonNull User user, Pageable pageable);

    Optional<Closet> findByIdAndUser(Long id, @NonNull User user);
}
