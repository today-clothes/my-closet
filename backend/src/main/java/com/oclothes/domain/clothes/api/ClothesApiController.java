package com.oclothes.domain.clothes.api;

import com.oclothes.domain.clothes.dto.ClothesDto;
import com.oclothes.domain.clothes.dto.ClothesResponseMessage;
import com.oclothes.domain.clothes.service.ClothesService;
import com.oclothes.global.dto.ResponseDto;
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

    @PostMapping
    public ResponseEntity<ResponseDto<ClothesUploadResponse>> upload(@Valid @ModelAttribute ClothesUploadRequest request) {
        return ResponseEntity.status(CREATED).body(ResponseDto.create(ClothesResponseMessage.UPLOAD_SUCCESS.getMessage(), this.clothesService.save(request)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<ClothesDto.SearchResponse>>> search(@Valid ClothesDto.SearchRequest request) {
        return ResponseEntity.ok(ResponseDto.create("adf", this.clothesService.search(request)));
    }

    @GetMapping(value = "/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String url) {
        return ResponseEntity.ok(this.clothesService.getImage(url));
    }
}
