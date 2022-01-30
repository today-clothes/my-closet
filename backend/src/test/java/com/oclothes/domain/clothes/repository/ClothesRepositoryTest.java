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

    @DisplayName("개별 옷장 태그 필터링")
    @Test
    public void searchByTag() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("./README.md"));

        //1.옷장 생성
        Closet closet = new Closet("c1", true, user);
        Closet result = closetRepository.save(closet);

        //2.태그 생성
        MoodTag mTag = new MoodTag("무드");
        EventTag eTag = new EventTag("등산");
        SeasonTag sTag = new SeasonTag("가을");
        SeasonTag sTag2= new SeasonTag("봄");

        MoodTag mood = moodTagRepository.save(mTag);
        SeasonTag season = seasonTagRepository.save(sTag);
        SeasonTag season2 = seasonTagRepository.save(sTag2);
        EventTag event = eventTagRepository.save(eTag);

        //3. 옷 생성
        Clothes clothes1 = Clothes.builder()
                .user(user)
                .imgUrl("aa")
                .closet(result).build();

        Clothes clothes2 = Clothes.builder()
                .user(user)
                .imgUrl("bb")
                .closet(result).build();

        Clothes clothes3 = Clothes.builder()
                .user(user)
                .imgUrl("cc")
                .closet(result).build();

        Clothes clothes4 = Clothes.builder()
                .user(user)
                .imgUrl("dd")
                .closet(result).build();

        Clothes c1 = clothesRepository.save(clothes1);
        Clothes c2 = clothesRepository.save(clothes2);
        Clothes c3 = clothesRepository.save(clothes3);
        Clothes c4 = clothesRepository.save(clothes4);

        //4. 태그-옷 연결
        //clothes1 - 가을 + 등산
        clothesSeasonTagRepository.save(new ClothesSeasonTag(c1, season));
        clothesEventTagRepository.save(new ClothesEventTag(c1, event));

        //clothes2 - 가을 + 봄 + 무드
        clothesSeasonTagRepository.save(new ClothesSeasonTag(c2, season));
        clothesSeasonTagRepository.save(new ClothesSeasonTag(c2, season2));
        clothesMoodTagRepository.save(new ClothesMoodTag(c2, mood));

        //clothes - 봄 + 등산
        clothesSeasonTagRepository.save(new ClothesSeasonTag(c3, season2));
        clothesEventTagRepository.save(new ClothesEventTag(c3, event));

        //clothes - 무드 + 등산
        clothesMoodTagRepository.save(new ClothesMoodTag(c4, mood));
        clothesEventTagRepository.save(new ClothesEventTag(c4, event));

        //4.검색 객체 생성
        List<Long> searchSeasonList = new ArrayList<>();
        searchSeasonList.add(season.getId());

        List<Long> searchMoodList = new ArrayList<>();
        searchMoodList.add(mood.getId());

        List<Long> searchEventList = new ArrayList<>();
        searchEventList.add(event.getId());

        ClothesDto.SearchRequest req1 = new ClothesDto.SearchRequest(result.getId(), searchSeasonList, null, null);
        ClothesDto.SearchRequest req2 = new ClothesDto.SearchRequest(result.getId(), null, null, searchMoodList);
        ClothesDto.SearchRequest req3 = new ClothesDto.SearchRequest(result.getId(), null, searchEventList, searchMoodList);
        List<Clothes> result1 = clothesRepository.searchByTag(req1);
        List<Clothes> result2 = clothesRepository.searchByTag(req2);
        List<Clothes> result3 = clothesRepository.searchByTag(req3);

        Assertions.assertThat(result1.size()).isEqualTo(2);
        Assertions.assertThat(result2.size()).isEqualTo(1);
        Assertions.assertThat(result3.size()).isEqualTo(4);
    }


    @Test
    @DisplayName("전체 옷장 태그 필터링")
    public void searchAllByTag(){
        Closet closet = new Closet("c1", true, user);
        Closet result = closetRepository.save(closet);

        MoodTag mTag = new MoodTag("무드");
        EventTag eTag = new EventTag("등산");
        SeasonTag sTag = new SeasonTag("가을");
        SeasonTag sTag2= new SeasonTag("봄");

        MoodTag mood = moodTagRepository.save(mTag);
        SeasonTag season = seasonTagRepository.save(sTag);
        SeasonTag season2 = seasonTagRepository.save(sTag2);
        EventTag event = eventTagRepository.save(eTag);

        Clothes clothes1 = Clothes.builder()
                .user(user)
                .imgUrl("aa")
                .closet(result).build();

        Clothes clothes2 = Clothes.builder()
                .user(user)
                .imgUrl("bb")
                .closet(result).build();

        Clothes c1 = clothesRepository.save(clothes1);
        Clothes c2 = clothesRepository.save(clothes2);

        clothesSeasonTagRepository.save(new ClothesSeasonTag(c1, season));
        clothesEventTagRepository.save(new ClothesEventTag(c1, event));

        clothesSeasonTagRepository.save(new ClothesSeasonTag(c2, season));
        clothesSeasonTagRepository.save(new ClothesSeasonTag(c2, season2));
        clothesMoodTagRepository.save(new ClothesMoodTag(c2, mood));


        List<Long> searchSeasonList = new ArrayList<>();
        searchSeasonList.add(season.getId());

        List<Long> searchMoodList = new ArrayList<>();
        searchMoodList.add(mood.getId());

        ClothesDto.SearchRequest searchRequest = new ClothesDto.SearchRequest(result.getId(), searchSeasonList, null, searchMoodList);
        List<Clothes> clothes = clothesRepository.searchAllClosetByTag(searchRequest);

        Assertions.assertThat(clothes.size()).isEqualTo(2);
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
