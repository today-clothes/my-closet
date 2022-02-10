package com.oclothes.global.config.environment;


import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.domain.EventTag;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.domain.SeasonTag;
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
    private final SeasonTagRepository seasonTagRepository;
    private final EventTagRepository eventTagRepository;
    private final MoodTagRepository moodTagRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.userRepository.save(this.createUser("admin@test.com", User.Role.ROLE_ADMIN));
        final User user = this.userRepository.save(this.createUser("user@test.com", User.Role.ROLE_USER));
        final User user1 = this.userRepository.save(this.createUser("james@test.com", User.Role.ROLE_USER));
        final Closet c1 = this.closetRepository.save(new Closet("c1",  user));
        final Closet c2 = this.closetRepository.save(new Closet("c2",  user1));

        this.seasonTagRepository.saveAll(List.of(
                new SeasonTag("봄"),
                new SeasonTag("여름"),
                new SeasonTag("가을"),
                new SeasonTag("겨울")
        ));
        this.eventTagRepository.saveAll(List.of(
                new EventTag("바다"),
                new EventTag("여행"),
                new EventTag("캠퍼스"),
                new EventTag("카페"),
                new EventTag("데이트")
        ));
        this.moodTagRepository.saveAll(List.of(
                new MoodTag("미니멀"),
                new MoodTag("이지캐주얼"),
                new MoodTag("비즈니스캐주얼"),
                new MoodTag("아메카지"),
                new MoodTag("스트릿")
        ));
    }

    private User createUser(String email, User.Role role) {
        return User.builder()
                .status(User.Status.NORMAL)
                .role(role)
                .email(new Email(email))
                .password(this.passwordEncoder.encode("123456"))
                .nickname("james")
                .height(160)
                .weight(50)
                .build();
    }

    @Override
    public void destroy() throws Exception {
        FileUtils.deleteDirectory(new File("./images"));
    }
}
