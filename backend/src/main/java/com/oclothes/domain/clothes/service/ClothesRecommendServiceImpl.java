package com.oclothes.domain.clothes.service;

import com.oclothes.domain.clothes.dao.ClothesRepository;
import com.oclothes.domain.clothes.domain.Clothes;
import com.oclothes.domain.clothes.domain.ClothesMoodTag;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesMapper;
import com.oclothes.domain.user.domain.UserPersonalInformation;
import com.oclothes.global.config.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ClothesRecommendServiceImpl implements ClothesRecommendService {
    private final ClothesRepository clothesRepository;
    private final ClothesMapper clothesMapper;
    private final long numOfMoodTag = 12L;

    @Getter
    @AllArgsConstructor
    private static class ClothesWithSim {
        private long cIndex;
        private float similarity;
    }

    public List<ClothesDto.SearchResponse> recommendClothes() {
        UserPersonalInformation userInfo = SecurityUtils.getLoggedInUser().getPersonalInformation();

        List<Clothes> clothes = this.clothesRepository.findAllForRecommend(userInfo.getGender(), userInfo.getAge(), userInfo.getHeight(), userInfo.getWeight());
        List<ClothesWithSim> clothesWithSims = new ArrayList<>();

        Long styleVectorized;
        Long userVectorized = this.getUserVectorization();
        for (long i = 0; i < clothes.size(); i++) {
            styleVectorized = this.getClothesVectorization(clothes.get((int) i).getMoodTags());
            clothesWithSims.add(new ClothesWithSim(i, this.calcSimilarity(styleVectorized, userVectorized)));
        }

        clothesWithSims.sort((c1, c2) -> {
            if (c1.getSimilarity() > c2.getSimilarity()) {
                return -1;
            } else if (c1.getSimilarity() < c2.getSimilarity()) {
                return 1;
            }
            return 0;
        });

        return clothesWithSims.stream()
                .map(c -> clothes.get((int) c.getCIndex()))
                .map(this.clothesMapper::toSearchResponse)
                .collect(Collectors.toList());
    }

    private Long getClothesVectorization(Set<ClothesMoodTag> clothes) {
        return clothes.stream()
                .mapToLong(tag -> (long) Math.pow(2, this.numOfMoodTag - tag.getTag().getId()))
                .sum();
    }

    private Long getUserVectorization() {
        return SecurityUtils.getLoggedInUser().getMoodTags().stream()
                .mapToLong(tag -> (long) Math.pow(2, this.numOfMoodTag - tag.getMoodTag().getId()))
                .sum();
    }

    private float calcSimilarity(Long style, Long user) {
        final long remove_one = (long) Math.pow(2, this.numOfMoodTag);
        int cnt_one_xnor = countOne((~(style ^ user)) + remove_one);

        return ((float) cnt_one_xnor / (float) this.numOfMoodTag);
    }

    private int countOne(Long input) {
        int i;
        for (i = 0; input != 0; i++) {
            input &= (input - 1);
        }
        return i;
    }

}
