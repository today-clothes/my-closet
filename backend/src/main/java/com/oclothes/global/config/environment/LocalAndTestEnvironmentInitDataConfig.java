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
                new SeasonTag("???"),
                new SeasonTag("??????"),
                new SeasonTag("??????"),
                new SeasonTag("??????")
        ));
        this.eventTagRepository.saveAll(List.of(
                new EventTag("??????"),
                new EventTag("??????"),
                new EventTag("?????????"),
                new EventTag("??????"),
                new EventTag("?????????")
        ));
        this.moodTagRepository.saveAll(List.of(
                new MoodTag("?????????"),
                new MoodTag("???????????????"),
                new MoodTag("?????????????????????"),
                new MoodTag("????????????"),
                new MoodTag("?????????")
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
