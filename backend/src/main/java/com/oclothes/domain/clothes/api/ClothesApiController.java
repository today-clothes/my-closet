package com.oclothes.domain.clothes.api;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.service.ClothesRecommendService;
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
import java.util.List;

import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadRequest;
import static com.oclothes.domain.clothes.dto.ClothesDto.ClothesUploadResponse;
import static com.oclothes.domain.clothes.dto.ClothesResponseMessage.*;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clothes")
public class ClothesApiController {
    private final ClothesService clothesService;
    private final ClothesRecommendService clothesRecommendService;

    @ApiOperation(value = "옷 업로드", notes = "옷 업로드 API")
    @PostMapping
    public ResponseEntity<ResponseDto<ClothesUploadResponse>> upload(@Valid @ModelAttribute ClothesUploadRequest request) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(UPLOAD_SUCCESS.getMessage(), this.clothesService.save(request)));
    }

    @ApiOperation(value = "옷 검색", notes = "옷 전체/개별 태그 필터링, 옷 키워드 검색, 옷 전체 리스트 반환 API")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<SliceDto<ClothesDto.SearchResponse>>> search(
            @Valid ClothesDto.SearchRequest request,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(SEARCH_CLOTHES_SUCCESS.getMessage(), this.clothesService.search(request, pageable)));
    }

    @ApiOperation(value = "옷 공개 여부", notes = "옷 공개/비공개 요청 API")
    @PatchMapping("/{id}/locked")
    public ResponseEntity<ResponseDto<ClothesDto.DefaultResponse>> changeLockStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_LOCKED_STATUS_SUCCESS.getMessage(), clothesService.changeLockStatus(id)));
    }

    @ApiOperation(value = "옷 상세정보", notes = "옷 상세정보 반환 API")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ClothesDto.ClothesDetailResponse>> getClothesDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.create(CLOTHES_DETAILS_SUCCESS.getMessage(), clothesService.getClothesDetails(id)));
    }

    @ApiOperation(value = "옷 이미지", notes = "옷 이미지 반환 API")
    @GetMapping(value = "/images/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        return ResponseEntity.ok(this.clothesService.getImage(url));
    }

    @ApiOperation(value = "옷 삭제", notes = "옷 개별 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteById(@PathVariable Long id) {
        this.clothesService.deleteById(id);
        return ResponseEntity.ok(ResponseDto.create(DELETE_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "옷 삭제", notes = "옷 개별 삭제 API")
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteAllById(@RequestParam List<Long> ids) {
        this.clothesService.deleteAllByIdIn(ids);
        return ResponseEntity.ok(ResponseDto.create(DELETE_All_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "옷 추천", notes = "옷 추천 알고리즘")
    @GetMapping("/recommend")
    public ResponseEntity<ResponseDto<List<ClothesDto.SearchResponse>>> recommendClothes() {
        return ResponseEntity.ok(ResponseDto.create(RECOMMEND_CLOTHES_SUCCESS.getMessage(), this.clothesRecommendService.recommendClothes()));
    }

}
