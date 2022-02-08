package com.oclothes.domain.closet.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.closet.dto.ClosetDto;
import com.oclothes.domain.closet.service.ClosetService;
import com.oclothes.global.dto.SliceDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClosetApiController.class)
class ClosetApiControllerTest extends BaseWebMvcTest {

    @MockBean
    private ClosetService closetService;

    @DisplayName("옷장 생성을 성공한다.")
    @WithMockUser
    @Test
    void createClosetTest() throws Exception {
        final ClosetDto.CreateRequest request = new ClosetDto.CreateRequest("나의 옷장1", true);
        mockMvc.perform(post("/closets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
    }

    @DisplayName("옷장 목록을 SliceDto 형태로 가져온다.")
    @WithMockUser
    @Test
    void findAllSliceByUserTest() throws Exception {
        final SliceDto<ClosetDto.DefaultResponse> dto = SliceDto.
                create(new SliceImpl<>(Collections.emptyList()));
        when(this.closetService.findAllSliceByUser(any())).thenReturn(dto);
        mockMvc.perform(get("/closets?size=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
    }

    @DisplayName("옷장 이름 변경을 성공한다.")
    @WithMockUser
    @Test
    void updateNameTest() throws Exception {
        final long id = 1L;
        final String name = "test";
        final ClosetDto.NameUpdateRequest request = new ClosetDto.NameUpdateRequest(id, name);
        final ClosetDto.DefaultResponse dto = new ClosetDto.DefaultResponse(id, name, true);

        when(this.closetService.updateName(any(), any())).thenReturn(dto);

        mockMvc.perform(patch("/closets/{id}/name", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
        verify(this.closetService, atMostOnce()).updateName(id, request);
    }

    @DisplayName("옷장 공개 상태 변경을 성공한다.")
    @WithMockUser
    @Test
    void changeLockStatusTest() throws Exception {
        final long id = 1L;
        final String name = "test";
        final ClosetDto.DefaultResponse dto = new ClosetDto.DefaultResponse(id, name, true);

        when(this.closetService.changeLockStatus(any())).thenReturn(dto);

        mockMvc.perform(patch("/closets/{id}/locked", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
        verify(this.closetService, atMostOnce()).changeLockStatus(id);
    }

    @DisplayName("옷장 삭제를 성공한다.")
    @WithMockUser
    @Test
    void deleteTest() throws Exception {
        doNothing().when(this.closetService).delete(any());
        mockMvc.perform(delete("/closets/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Matchers.containsString("완료")))
                .andDo(print());
    }
}