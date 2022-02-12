package com.oclothes.domain.clothes.domain;

import com.oclothes.BaseDataJpaTest;
import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.clothes.dao.ClothesEventTagRepository;
import com.oclothes.domain.clothes.dao.ClothesMoodTagRepository;
import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.dao.ClothesSeasonTagRepository;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.tag.dao.EventTagRepository;
import com.oclothes.domain.tag.dao.MoodTagRepository;
import com.oclothes.domain.tag.dao.SeasonTagRepository;
import com.oclothes.domain.tag.domain.EventTag;
import com.oclothes.domain.tag.domain.MoodTag;
import com.oclothes.domain.tag.domain.SeasonTag;
import com.oclothes.domain.user.domain.Email;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.util.SecurityUtils;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

import static com.oclothes.domain.clothes.dto.ClothesDto.SearchRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClothesRepositoryTest extends BaseDataJpaTest {
    @Autowired
    private ClosetRepository closetRepository;
    @Autowired
    private ClothesRepository clothesRepository;
    @Autowired
    private EventTagRepository eventTagRepository;
    @Autowired
    private MoodTagRepository moodTagRepository;
    @Autowired
    private SeasonTagRepository seasonTagRepository;
    @Autowired
    private ClothesSeasonTagRepository clothesSeasonTagRepository;
    @Autowired
    private ClothesMoodTagRepository clothesMoodTagRepository;
    @Autowired
    private ClothesEventTagRepository clothesEventTagRepository;
    @PersistenceContext
    EntityManager em;

    User user;

    private static MockedStatic<SecurityUtils> securityUtils;

    @BeforeAll
    static void beforeAll() {
        securityUtils = Mockito.mockStatic(SecurityUtils.class);
    }

    @AfterAll
    static void afterAll() {
        securityUtils.close();
    }

    @BeforeEach
    public void init() {
        //1.회원
        Email email = new Email("sks@naver.com");
        user = User.builder().email(email).height(10).nickname("semi").password("10").weight(1).build();
        em.persist(user);
        securityUtils.when(SecurityUtils::getLoggedInUser).thenReturn(user);
    }

    @DisplayName("개별 옷장 태그 필터링")
    @Test
    public void searchByTag() throws IOException {
        //1.옷장 생성
        Closet closet = closetRepository.save(new Closet("c1", user));

        //2.태그 생성
        MoodTag moodTag1 = moodTagRepository.save(new MoodTag("무드1"));
        EventTag eventTag1 = eventTagRepository.save(new EventTag("이벤트1"));
        SeasonTag seasonTag1 = seasonTagRepository.save(new SeasonTag("계절1"));
        SeasonTag seasonTag2 = seasonTagRepository.save(new SeasonTag("계절2"));

        //3. 옷 생성
        Clothes clothes1 = clothesRepository.save(createClothes(closet, "a", true));
        Clothes clothes2 = clothesRepository.save(createClothes(closet, "b", false));
        Clothes clothes3 = clothesRepository.save(createClothes(closet, "c", true));
        Clothes clothes4 = clothesRepository.save(createClothes(closet, "d", true));

        //4. 태그-옷 연결
        //clothes1 - 계절1 + 이벤트1
        clothesSeasonTagRepository.save(new ClothesSeasonTag(clothes1, seasonTag1));
        clothesEventTagRepository.save(new ClothesEventTag(clothes1, eventTag1));

        //clothes2 - 계절1 + 계절2 + 무드1
        clothesSeasonTagRepository.save(new ClothesSeasonTag(clothes2, seasonTag1));
        clothesSeasonTagRepository.save(new ClothesSeasonTag(clothes2, seasonTag2));
        clothesMoodTagRepository.save(new ClothesMoodTag(clothes2, moodTag1));

        //clothes - 계절2 + 이벤트1
        clothesSeasonTagRepository.save(new ClothesSeasonTag(clothes3, seasonTag2));
        clothesEventTagRepository.save(new ClothesEventTag(clothes3, eventTag1));

        //clothes - 무드1 + 이벤트1
        clothesMoodTagRepository.save(new ClothesMoodTag(clothes4, moodTag1));
        clothesEventTagRepository.save(new ClothesEventTag(clothes4, eventTag1));

        //4.검색 객체 생성
        // 4.1) 조건. 옷장 전체
        SearchRequest req1 = new SearchRequest(closet.getId(), null, null, null, null);
        // 4.2) 조건. 옷장, 계절1
        SearchRequest req2 = new SearchRequest(closet.getId(), null, List.of(seasonTag1.getId()), null, null);
        // 4.3) 조건. 옷장, 계절1 또는 계절2
        SearchRequest req3 = new SearchRequest(closet.getId(), null, List.of(seasonTag1.getId(), seasonTag2.getId()), null, null);
        // 4.4) 조건. 옷장, 계절2
        SearchRequest req4 = new SearchRequest(closet.getId(), null, List.of(seasonTag2.getId()), null, null);
        // 4.5) 조건. 옷장, 계절1 그리고 무드1
        SearchRequest req5 = new SearchRequest(closet.getId(), null, List.of(seasonTag1.getId()), null, List.of(moodTag1.getId()));
        // 4.6) 조건. 옷장, 계절1 그리고 무드1 그리고 이벤트1
        SearchRequest req6 = new SearchRequest(closet.getId(), null, List.of(seasonTag1.getId()), List.of(eventTag1.getId()), List.of(moodTag1.getId()));
        // 4.7) 조건. 옷장, (계절1 or 계절2) and 이벤트1
        SearchRequest req7 = new SearchRequest(closet.getId(), null, List.of(seasonTag1.getId(), seasonTag2.getId()), List.of(eventTag1.getId()), null);

        PageRequest pageRequest = PageRequest.of(0, 5);

        Slice<Clothes> result1 = clothesRepository.searchByTag(req1, user, pageRequest);
        Slice<Clothes> result2 = clothesRepository.searchByTag(req2, user, pageRequest);
        Slice<Clothes> result3 = clothesRepository.searchByTag(req3, user, pageRequest);
        Slice<Clothes> result4 = clothesRepository.searchByTag(req4, user, pageRequest);
        Slice<Clothes> result5 = clothesRepository.searchByTag(req5, user, pageRequest);
        Slice<Clothes> result6 = clothesRepository.searchByTag(req6, user, pageRequest);
        Slice<Clothes> result7 = clothesRepository.searchByTag(req7, user, pageRequest);

        assertEquals(4, result1.getNumberOfElements());
        assertEquals(2, result2.getNumberOfElements());
        assertEquals(3, result3.getNumberOfElements());
        assertEquals(2, result4.getNumberOfElements());
        assertEquals(1, result5.getNumberOfElements());
        assertEquals(0, result6.getNumberOfElements());
        assertEquals(2, result7.getNumberOfElements());
    }

    @Test
    @DisplayName("전체 키워드로 공개 상태 옷 검색")
    public void findByContentContainingAndLockedIsFalseTest() {
        ClothesDto.SearchRequest request = new ClothesDto.SearchRequest(null, "우왕", null, null, null);
        Closet closet = new Closet("c1", user);
        Closet result = this.closetRepository.save(closet);
        Clothes clothes1 = this.createClothes(result, "aa", true);
        Clothes clothes2 = this.createClothes(result, "bb", false);
        clothes1.setContent("ㅋ키키키예시");
        clothes2.setContent("1우왕1");
        this.clothesRepository.save(clothes1);
        this.clothesRepository.save(clothes2);
        PageRequest pageRequest = PageRequest.of(0, 2);

        Slice<Clothes> clothes = this.clothesRepository.searchByTag(request,null, pageRequest);
        assertEquals(1, clothes.getNumberOfElements());
    }

    private Clothes createClothes(Closet closet, String imgUrl, boolean locked) {
        return Clothes.builder().user(user).imgUrl(imgUrl).locked(locked).closet(closet).build();
    }
}
