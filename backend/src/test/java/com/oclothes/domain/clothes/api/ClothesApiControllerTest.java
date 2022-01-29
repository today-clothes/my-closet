package com.oclothes.domain.clothes.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.service.ClothesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
}