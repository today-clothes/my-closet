package com.oclothes.global.config.security.jwt;

import com.oclothes.BaseTest;
import com.oclothes.domain.user.domain.User;
import com.oclothes.domain.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {"jwt.expire-hours=1", "jwt.secret=adsfadsfasdfasdfasdfsdafasfsafsafsafsafasfd"})
@ContextConfiguration(classes = JwtProvider.class)
@ExtendWith(SpringExtension.class)
class JwtProviderTest extends BaseTest {

    @Autowired
    private JwtProvider jwtProvider;

    private UsernamePasswordAuthenticationToken authenticationToken;

    private final String username = "username";

    @BeforeEach
    void setUp() {
        this.authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                "password",
                Stream.of(User.Role.ROLE_USER).map(r -> (GrantedAuthority) r::name).collect(Collectors.toList()));
    }

    @DisplayName("토큰 생성 테스트")
    @Test
    void generateTokenTest() {
        UserDto.LoginResponse loginResponse = this.jwtProvider.generateToken(this.authenticationToken);
        log.info("token: {}", loginResponse.getAccessToken());
        assertNotNull(loginResponse);
    }

    @Test
    void getAuthentication() {
        UserDto.LoginResponse loginResponse = this.jwtProvider.generateToken(this.authenticationToken);
        log.info("token: {}", loginResponse.getAccessToken());
        Authentication authentication = this.jwtProvider.getAuthentication(this.resolveToken(loginResponse));
        assertNotNull(authentication);
        assertEquals(username, authentication.getName());
    }

    @Test
    void validateToken() {
        UserDto.LoginResponse loginResponse = this.jwtProvider.generateToken(this.authenticationToken);
        assertTrue(this.jwtProvider.validateToken(this.resolveToken(loginResponse)));
    }

    private String resolveToken(UserDto.LoginResponse loginResponse) {
        return loginResponse.getAccessToken().replace(JwtProperties.BEARER_TYPE, "");
    }
}