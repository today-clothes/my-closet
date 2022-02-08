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
import static com.oclothes.domain.clothes.dto.ClothesResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clothes")
public class ClothesApiController {
    private final ClothesService clothesService;

    @ApiOperation(value = "옷 업로드", notes = "옷 업로드 API")
    @PostMapping
    public ResponseEntity<ResponseDto<ClothesUploadResponse>> upload(@Valid @ModelAttribute ClothesUploadRequest request) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(UPLOAD_SUCCESS.getMessage(), this.clothesService.save(request)));
    }

    @ApiOperation(value = "옷 태그 필터링", notes = "옷 전체/개별 태그 필터링 API")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> searchAllByTag(
            @Valid @RequestBody ClothesDto.SearchRequest request,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(TAG_FILTER_SUCCESS.getMessage(), this.clothesService.searchByTag(request, pageable)));
    }

    @ApiOperation(value = "옷 키워드 검색", notes = "옷 키워드로 검색 API")
    @GetMapping("/search/all")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> searchByKeyword(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(KEYWORD_SEARCH_SUCCESS.getMessage(),
                clothesService.searchByKeyword(keyword, pageable)));
    }

    @ApiOperation(value = "옷 공개 여부", notes = "옷 공개/비공개 요청 API")
    @PatchMapping("/{id}/locked")
    public ResponseEntity<ResponseDto<ClothesDto.DefaultResponse>> changeLockStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_LOCKED_STATUS_SUCCESS.getMessage(), clothesService.changeLockStatus(id)));
    }

    @ApiOperation(value = "옷 이미지", notes = "옷 이미지 반환 API")
    @GetMapping(value = "/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        return ResponseEntity.ok(this.clothesService.getImage(url));
    }

    @ApiOperation(value = "옷 삭제", notes = "옷 개별 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<ResponseDto<Void>>> deleteById(@PathVariable Long id) {
        this.clothesService.deleteById(id);
        return ResponseEntity.ok(ResponseDto.create(ClothesResponseMessage.DELETE_SUCCESS.getMessage()));
    }

}
