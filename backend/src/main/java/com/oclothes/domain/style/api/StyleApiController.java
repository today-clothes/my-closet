package com.oclothes.domain.style.api;

import com.oclothes.domain.style.dto.StyleDto;
import com.oclothes.domain.style.dto.StyleResponseMessage;
import com.oclothes.domain.style.service.StyleService;
import com.oclothes.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/styles")
public class StyleApiController {
    private final StyleService styleService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<StyleDto.Response>>> findAll() {
        return ResponseEntity.ok(ResponseDto.create(StyleResponseMessage.FIND_ALL_SUCCESS.getMessage(), this.styleService.findAll()));
    }
}
