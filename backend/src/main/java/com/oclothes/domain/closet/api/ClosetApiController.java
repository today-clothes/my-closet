package com.oclothes.domain.closet.api;

import com.oclothes.domain.closet.service.ClosetService;
import com.oclothes.global.dto.ResponseDto;
import com.oclothes.global.dto.SliceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.oclothes.domain.closet.dto.ClosetDto.*;
import static com.oclothes.domain.closet.dto.ClosetResponseMessage.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/closets")
public class ClosetApiController {

    private final ClosetService closetService;

    @ApiOperation(value = "옷장 생성", notes = "옷장 생성 API")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateResponse>> create(@Valid @RequestBody CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.create(CREATE_SUCCESS.getMessage(), this.closetService.create(request)));
    }

    @ApiOperation(value = "전체 옷장 리스트", notes = "유저의 옷장 리스트 반환 API")
    @GetMapping
    public ResponseEntity<ResponseDto<SliceDto<DefaultResponse>>> findAllSliceByUser(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(GET_LIST_SUCCESS.getMessage(), this.closetService.findAllSliceByUser(pageable)));
    }

    @ApiOperation(value = "옷장 이름 업데이트", notes = "옷장 이름 변경 API")
    @PutMapping("/{id}/name")
    public ResponseEntity<ResponseDto<DefaultResponse>> updateName(@PathVariable Long id, @Valid @RequestBody NameUpdateRequest response) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_NAME_SUCCESS.getMessage(), this.closetService.updateName(id, response)));
    }

    @ApiOperation(value="옷장 공개 여부", notes = "옷장 공개/비공개 요청 API")
    @PutMapping("/{id}/locked")
    public ResponseEntity<ResponseDto<DefaultResponse>> changeLockStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_LOCKED_STATUS_SUCCESS.getMessage(), this.closetService.changeLockStatus(id)));
    }

    @ApiOperation(value = "옷장 삭제", notes = "옷장 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Long id) {
        this.closetService.delete(id);
        return ResponseEntity.ok(ResponseDto.create(DELETE_SUCCESS.getMessage()));
    }

}