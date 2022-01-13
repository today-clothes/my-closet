package com.oclothes.global.config;


import com.oclothes.domain.style.dao.StyleRepository;
import com.oclothes.domain.style.domain.Style;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Profile({"local", "test"})
@Configuration
public class LocalAndTestEnvironmentInitDataConfig implements ApplicationRunner {

    private final StyleRepository styleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
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

}
