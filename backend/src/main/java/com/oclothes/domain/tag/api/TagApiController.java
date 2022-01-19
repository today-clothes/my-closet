package com.oclothes.domain.tag.api;

import com.oclothes.domain.tag.dto.TagDto;
import com.oclothes.domain.tag.service.TagService;
import com.oclothes.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
public class TagApiController {
    private final TagService tagService;

    @GetMapping("/season")
    public ResponseEntity<ResponseDto<TagDto.Response>> findAllSeasons() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/event")
    public ResponseEntity<ResponseDto<TagDto.Response>> findAllEvents() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/mood")
    public ResponseEntity<ResponseDto<TagDto.Response>> findAllMoods() {
        return ResponseEntity.ok(null);
    }
}
