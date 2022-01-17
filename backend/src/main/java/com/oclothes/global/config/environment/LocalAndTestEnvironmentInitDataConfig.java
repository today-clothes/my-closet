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
        this.styleRepository.save(new Style("미니멀"));
        this.styleRepository.save(new Style("이지캐주얼"));
        this.styleRepository.save(new Style("비즈니스캐주얼"));
        this.styleRepository.save(new Style("아메카지"));
        this.styleRepository.save(new Style("스트릿"));
        this.styleRepository.save(new Style("시티보이"));
        this.styleRepository.save(new Style("원마일웨어"));
        this.styleRepository.save(new Style("스포티"));
        this.styleRepository.save(new Style("유니크"));
        this.styleRepository.save(new Style("레트로"));
        this.styleRepository.save(new Style("러블리"));
        this.styleRepository.save(new Style("모던캐주얼"));
        this.styleRepository.save(new Style("락시크"));
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
