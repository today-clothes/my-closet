package com.oclothes.domain.clothes.api;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesResponseMessage;
import com.oclothes.domain.clothes.service.ClothesService;
import com.oclothes.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clothes")
public class ClothesApiController {
    private final ClothesService clothesService;

    @ApiOperation(value = "옷 업로드", notes = "옷 업로드 API")
    @PostMapping
    public ResponseEntity<ResponseDto<ClothesUploadResponse>> upload(@Valid @ModelAttribute ClothesUploadRequest request) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(ClothesResponseMessage.UPLOAD_SUCCESS.getMessage(), this.clothesService.save(request)));
    }

    @ApiOperation(value = "옷 태그 필터링", notes = "옷 태그 필터링 API")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<ClothesDto.SearchResponse>>> searchByTag(@Valid ClothesDto.SearchRequest request) {
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.UPLOAD_SUCCESS.getMessage(), this.clothesService.searchByTag(request)));
    }

    @ApiOperation(value = "옷 키워드 검색", notes = "옷 키워드로 검색 API")
    @GetMapping("/search/all")
    public ResponseEntity<ResponseDto<List<ClothesDto.SearchResponse>>> searchByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.KEYWORD_SEARCH_SUCCESS.getMessage(), clothesService.searchByKeyword(new ClothesDto.SearchKeywordRequest(keyword))));
    }

    @ApiOperation(value = "옷 이미지", notes = "옷 이미지 반환 API")
    @GetMapping(value = "/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        return ResponseEntity.ok(this.clothesService.getImage(url));
    }
}
