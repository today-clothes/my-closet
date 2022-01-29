package com.oclothes.domain.clothes.repository;

import com.oclothes.BaseDataJpaTest;
import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.*;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.domain.ClothesEventTag;
import com.oclothes.domain.clothes.domain.ClothesMoodTag;
import com.oclothes.domain.clothes.domain.ClothesSeasonTag;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.domain.EventTag;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.domain.SeasonTag;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClothesRepositoryTest extends BaseDataJpaTest {
    @Autowired private ClosetRepository closetRepository;
    @Autowired private ClothesRepository clothesRepository;
    @Autowired private EventTagRepository eventTagRepository;
    @Autowired private MoodTagRepository moodTagRepository;
    @Autowired private SeasonTagRepository seasonTagRepository;
    @Autowired private ClothesSeasonTagRepository clothesSeasonTagRepository;
    @Autowired private ClothesMoodTagRepository clothesMoodTagRepository;
    @Autowired private ClothesEventTagRepository clothesEventTagRepository;
    @PersistenceContext EntityManager em;

    User user;

    @BeforeEach
    public void init() {
        //1.회원
        Email email = new Email("sks@naver.com");
        user = User.builder().email(email).height(10).nickname("semi").password("10").weight(1).build();
        em.persist(user);
    }


    @Test
    @DisplayName("전체 키워드로 옷 검색")
    public void searchByKeyword(){
        Closet closet = new Closet("c1", true, user);
        Closet result = closetRepository.save(closet);

        Clothes clothes1 = Clothes.builder()
                .user(user)
                .imgUrl("aa")
                .closet(result).build();

        Clothes clothes2 = Clothes.builder()
                .user(user)
                .imgUrl("bb")
                .closet(result).build();

        clothes1.setContent("ㅋ키키키예시");
        clothes2.setContent("기분좋은날옷");

        Clothes c1 = clothesRepository.save(clothes1);
        Clothes c2 = clothesRepository.save(clothes2);

        List<Clothes> clothes = clothesRepository.findByContentContaining("예시");
        Assertions.assertThat(clothes.size()).isEqualTo(1);
    }


}
