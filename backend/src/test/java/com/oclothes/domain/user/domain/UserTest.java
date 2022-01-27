package com.oclothes.domain.user.domain;

import com.oclothes.BaseDataJpaTest;
import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.user.dao.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest extends BaseDataJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClosetRepository closetRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(new Email("test@mail.com"))
                .password("1234")
                .role(User.Role.ROLE_USER)
                .status(User.Status.WAIT)
                .build();
    }

    @DisplayName("유저 저장 테스트")
    @Test
    void saveTest() {
        User savedUser = this.userRepository.save(user);
        log.info("user id: {}", savedUser.getId());
        assertNotNull(savedUser.getId());
    }

    @DisplayName("유저 옷장 추가 테스트")
    @Test
    void addClosetTest() {
        User savedUser = this.userRepository.save(user);
        savedUser.addCloset(new Closet("my first closet", true, user));
        Closet closet = this.closetRepository.findAllByUser(savedUser).get(0);
        log.info("closet id: {}", closet.getId());
        assertNotNull(closet.getId());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @DisplayName("이메일로 유저 가져오기 테스트")
    @Test
    void findByEmailTest() {
        this.userRepository.save(user);
        User findByEmailUser = this.userRepository.findByEmail_Value(this.user.getEmail().getValue()).get();
        log.info("user id: {}", findByEmailUser.getId());
        assertNotNull(findByEmailUser);
        assertEquals(user.getEmail(), findByEmailUser.getEmail());
    }

}