package com.oclothes.global.config.environment;


import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.style.dao.StyleRepository;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Profile("local")
@Configuration
public class LocalAndTestEnvironmentInitDataConfig implements ApplicationRunner, DisposableBean {

    private final UserRepository userRepository;
    private final ClosetRepository closetRepository;
    private final PasswordEncoder passwordEncoder;
    private final StyleRepository styleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.userRepository.save(this.createUser("admin@test.com", User.Role.ROLE_ADMIN));
        final User user = this.userRepository.save(this.createUser("user@test.com", User.Role.ROLE_USER));
        final Closet c1 = this.closetRepository.save(new Closet("c1", false, user));
        this.styleRepository.saveAll(List.of(
                new Style(Style.TYPE.SEASON, "봄"),
                new Style(Style.TYPE.SEASON, "여름"),
                new Style(Style.TYPE.SEASON, "가을"),
                new Style(Style.TYPE.SEASON, "겨울"),
                new Style(Style.TYPE.TPO, "바다"),
                new Style(Style.TYPE.TPO, "여행"),
                new Style(Style.TYPE.TPO, "캠퍼스"),
                new Style(Style.TYPE.TPO, "카페"),
                new Style(Style.TYPE.TPO, "데이트"),
                new Style(Style.TYPE.TPO, "결혼식"),
                new Style(Style.TYPE.TPO, "출근"),
                new Style(Style.TYPE.TPO, "데일리"),
                new Style(Style.TYPE.MOOD, "미니멀"),
                new Style(Style.TYPE.MOOD, "이지캐주얼"),
                new Style(Style.TYPE.MOOD, "비즈니스캐주얼"),
                new Style(Style.TYPE.MOOD, "아메카지"),
                new Style(Style.TYPE.MOOD, "스트릿"),
                new Style(Style.TYPE.MOOD, "시티보이"),
                new Style(Style.TYPE.MOOD, "원마일웨어"),
                new Style(Style.TYPE.MOOD, "스포티"),
                new Style(Style.TYPE.MOOD, "유니크"),
                new Style(Style.TYPE.MOOD, "레트로"),
                new Style(Style.TYPE.MOOD, "러블리"),
                new Style(Style.TYPE.MOOD, "모던캐주얼"),
                new Style(Style.TYPE.MOOD, "락시크")
        ));
    }

    private User createUser(String email, User.Role role) {
        return User.builder()
                .status(User.Status.NORMAL)
                .role(role)
                .email(new Email(email))
                .password(this.passwordEncoder.encode("123456"))
                .build();
    }

    @Override
    public void destroy() throws Exception {
        FileUtils.deleteDirectory(new File("./images"));
    }
}
