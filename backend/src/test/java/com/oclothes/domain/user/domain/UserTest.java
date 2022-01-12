package com.oclothes.domain.user.domain;

import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.cloth.dao.ClothRepository;
import com.oclothes.domain.cloth.domain.Cloth;
import com.oclothes.domain.style.dao.StyleRepository;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.dao.UserStyleRepository;
import com.oclothes.infra.email.dao.EmailAuthenticationCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClosetRepository closetRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private UserStyleRepository userStyleRepository;

    @Autowired
    private ClothRepository clothRepository;

    @Autowired
    private EmailAuthenticationCodeRepository emailAuthenticationCodeRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(new Email("test@mail.com"))
                .password("1234")
                .role(User.Role.USER)
                .status(User.Status.WAIT)
                .build();
    }

    @DisplayName("유저 저장 테스트")
    @Test
    void saveTest() {
        User savedUser = this.userRepository.save(user);
        log.info(() -> String.format("user id: %s", savedUser.getId()));
        assertNotNull(savedUser.getId());
    }

    @DisplayName("유저 옷장 추가 테스트")
    @Test
    void addClosetTest() {
        User savedUser = this.userRepository.save(user);
        savedUser.addCloset(new Closet("my first closet", user));
        Closet closet = this.closetRepository.findByUser(savedUser).get(0);
        log.info(() -> String.format("closet id: %s", closet.getId()));
        assertNotNull(closet.getId());
    }

    @DisplayName("유저 옷장에 옷 추가 테스트")
    @Test
    void addClothTest() {
        User savedUser = this.userRepository.save(user);
        savedUser.addCloset(new Closet("my first closet", user));
        Closet closet = this.closetRepository.findByUser(savedUser).get(0);
        closet.addCloth(new Cloth(closet, "", Cloth.Season.SPRING, ""));
        Cloth cloth = this.clothRepository.findByCloset(closet).get(0);
        log.info(() -> String.format("cloth id: %s", cloth.getId()));
        assertNotNull(cloth);
        assertEquals("my first closet", cloth.getCloset().getName());
        assertEquals(Cloth.Season.SPRING, cloth.getSeason());
    }

    @DisplayName("유저 스타일 추가 테스트")
    @Test
    void addUserStyleTest() {
        User savedUser = this.userRepository.save(user);
        String styleName = "귀염뽀짝";
        Style style = this.styleRepository.save(new Style(styleName));
        savedUser.addUserStyle(new UserStyle(savedUser, style));
        UserStyle userStyle = this.userStyleRepository.findByUser(savedUser).get(0);
        log.info(() -> String.format("closet id: %s | name: %s", userStyle.getId(), userStyle.getName()));
        assertNotNull(userStyle.getId());
        assertEquals(styleName, userStyle.getName());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void findByEmailTest() {
        this.userRepository.save(user);
        User findByEmailUser = this.userRepository.findByEmail_Value(this.user.getEmail().getValue()).get();
        log.info(() -> String.format("user id: %s", findByEmailUser.getId()));
        assertNotNull(findByEmailUser);
        assertEquals(user.getEmail(), findByEmailUser.getEmail());
    }

}