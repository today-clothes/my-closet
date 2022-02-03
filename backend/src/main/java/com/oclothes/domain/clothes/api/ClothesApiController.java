package com.oclothes.domain.clothes.api;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesResponseMessage;
import com.oclothes.domain.clothes.service.ClothesService;
import com.oclothes.global.dto.ResponseDto;
import com.oclothes.global.dto.SliceDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @ApiOperation(value = "옷 전체 태그 필터링", notes = "옷 전체 태그 필터링 API")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> searchAllByTag(@Valid @RequestBody ClothesDto.SearchRequest request, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.TAG_FILTER_SUCCESS.getMessage(), this.clothesService.searchAllClosetByTag(request, pageable)));
    }

    @ApiOperation(value = "옷 개별 태그 필터링", notes = "옷 개별 태그 필터링 API")
    @GetMapping("/search/{closetId}")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> searchByTag(@Valid @RequestBody ClothesDto.SearchRequest request,
                                                                                        @PageableDefault Pageable pageable, @PathVariable("closetId") String id) {
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.TAG_FILTER_SUCCESS.getMessage(), this.clothesService.searchByTag(request, pageable)));
    }

    @ApiOperation(value = "옷 키워드 검색", notes = "옷 키워드로 검색 API")
    @GetMapping("/search/all")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> searchByKeyword(@RequestParam String keyword, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.KEYWORD_SEARCH_SUCCESS.getMessage(),
                clothesService.searchByKeyword(new ClothesDto.SearchKeywordRequest(keyword), pageable)));
    }

    @ApiOperation(value = "옷 이미지", notes = "옷 이미지 반환 API")
    @GetMapping(value = "/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        return ResponseEntity.ok(this.clothesService.getImage(url));
    }
}
