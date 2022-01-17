package com.oclothes.domain.style.api;

import com.oclothes.BaseWebMvcTest;
import com.oclothes.domain.style.domain.Style;
import com.oclothes.domain.style.dto.StyleDto;
import com.oclothes.domain.style.service.StyleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = StyleApiController.class)
class StyleApiControllerTest extends BaseWebMvcTest {

    @MockBean
    private StyleService styleService;

    @DisplayName("태그 전체를 반환합니다.")
    @WithMockUser
    @Test
    void findAllTest() throws Exception {
        final List<StyleDto.Response> responses = LongStream.rangeClosed(0, 5).mapToObj(i -> new StyleDto.Response(i, Style.TYPE.MOOD, "style" + i)).collect(Collectors.toList());

        when(this.styleService.findAll()).thenReturn(responses);

        mockMvc.perform(get("/styles"))
                .andExpect(jsonPath("$.message").value(containsString("성공")))
                .andDo(print());
        verify(this.styleService, atMostOnce()).findAll();
    }
}