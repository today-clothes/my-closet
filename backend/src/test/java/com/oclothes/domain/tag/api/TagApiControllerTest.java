package com.oclothes.domain.tag.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.domain.tag.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagApiController.class)
class TagApiControllerTest extends BaseWebMvcTest {
    @MockBean
    private TagService tagService;

    @DisplayName("전체 태그 리스트 반환")
    @WithMockUser
    @Test
    void getAllTest() throws Exception {
        final TagDto.AllResponse response = new TagDto.AllResponse(null, null, null);

        when(this.tagService.getAll()).thenReturn(response);

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(containsString("성공")))
                .andDo(print());
    }
}