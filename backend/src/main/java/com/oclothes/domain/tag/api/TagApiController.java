package com.oclothes.domain.tag.api;

import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.domain.tag.service.TagService;
import com.oclothes.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.oclothes.domain.tag.dto.TagResponseMessage.ALL_RESPONSE_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagApiController {
    private final TagService tagService;

    @ApiOperation(value = "전체 태그 리스트")
    @GetMapping
    public ResponseEntity<ResponseDto<TagDto.AllResponse>> findAllSeasons() {
        return ResponseEntity.ok(ResponseDto.create(ALL_RESPONSE_SUCCESS.getMessage(), this.tagService.getAll()));
    }
}
