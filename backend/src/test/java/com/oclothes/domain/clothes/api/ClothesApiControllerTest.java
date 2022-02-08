package com.oclothes.domain.clothes.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.service.ClothesService;
import com.oclothes.global.dto.SliceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.io.FileInputStream;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ClothesApiController.class})
class ClothesApiControllerTest extends BaseWebMvcTest {

    @MockBean
    private ClothesService clothesService;

    @DisplayName("옷 업로드를 성공한다.")
    @WithMockUser
    @Test
    void clothesUploadTest() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("file", new FileInputStream("./README.md"));
        final ClothesDto.ClothesUploadResponse response = new ClothesDto.ClothesUploadResponse(1L, 1L, "imageUrl");

        when(this.clothesService.save(any())).thenReturn(response);

        mockMvc.perform(multipart("/clothes")
                        .file(file)
                        .param("closetId", "1")
                        .param("seasonIds", "1,2")
                        .param("eventIds", "1")
                        .param("moodIds", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(containsString("저장했습니다")))
                .andExpect(jsonPath("$.data.closetId").value(is(1)))
                .andExpect(jsonPath("$.data.clothesId").value(is(1)))
                .andDo(print());
        verify(this.clothesService, atMostOnce()).save(any());
    }

    @DisplayName("이미지 보기")
    @WithMockUser
    @Test
    void getImageTest() throws Exception {
        final String utf8 = "charset=UTF-8";

        when(this.clothesService.getImage(any())).thenReturn(new byte[0]);

        mockMvc.perform(get("/clothes/imageUrl"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(String.format("%s;%s", MediaType.IMAGE_JPEG_VALUE, utf8)));
        verify(this.clothesService, atMostOnce()).getImage(any());
    }

    @DisplayName("옷 필터링 결과를 SliceDto 매핑해서 반환")
    @WithMockUser
    @Test
    void getFilteringList() throws Exception {
        final ClothesDto.SearchRequest req = new ClothesDto.SearchRequest(1L, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        final SliceDto<ClothesDto.SearchResponse> dto = SliceDto.create(new SliceImpl<>(Collections.emptyList()));

        when(clothesService.searchByTag(any(), any())).thenReturn(dto);

        mockMvc.perform(get("/clothes/search/?size=20")
                    .content(objectMapper.writeValueAsString(req))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("필터링된")))
                .andDo(print());
    }


    @DisplayName("전체 옷장 필터링 결과를 SliceDto 매핑해서 반환")
    @WithMockUser
    @Test
    void getAllFilteringList() throws Exception {
        final ClothesDto.SearchRequest req = new ClothesDto.SearchRequest(1L, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        final SliceDto<ClothesDto.SearchResponse> dto = SliceDto.create(new SliceImpl<>(Collections.emptyList()));

        when(clothesService.searchAllClosetByTag(any(), any())).thenReturn(dto);

        mockMvc.perform(get("/clothes/search?size=20")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("필터링된")))
                .andDo(print());
    }


    @DisplayName("옷 검색 결과를 SliceDto 매핑해서 반환")
    @WithMockUser
    @Test
    void getSearchKeywordList() throws Exception {
        final SliceDto<ClothesDto.SearchResponse> dto = SliceDto.create(new SliceImpl<>(Collections.emptyList()));
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "hi");
        requestParams.add("size", "20");

        when(clothesService.searchByKeyword(any(), any())).thenReturn(dto);

        mockMvc.perform(get("/clothes/search/all")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("일치하는")))
                .andDo(print());
    }

    @DisplayName("옷을 삭제한다.")
    @WithMockUser
    @Test
    void deleteClothes() throws Exception {
        doNothing().when(this.clothesService).deleteById(any());

        this.mockMvc.perform(delete("/clothes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("삭제")))
                .andDo(print());
    }

    @DisplayName("옷 공개 상태 변경")
    @WithMockUser
    @Test
    void changeLockStatusTest() throws Exception {
        final long id = 1L;
        final String name = "test";
        final ClothesDto.DefaultResponse dto = new ClothesDto.DefaultResponse(id, true);

        when(clothesService.changeLockStatus(any())).thenReturn(dto);

        mockMvc.perform(patch("/clothes/{id}/locked", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());

        verify(clothesService, atMostOnce()).changeLockStatus(id);
    }

}