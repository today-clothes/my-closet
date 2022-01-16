package com.oclothes.domain.closet.api;

import com.oclothes.domain.closet.service.ClosetService;
import com.oclothes.global.dto.ResponseDto;
import com.oclothes.global.dto.SliceDto;
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

    @PostMapping
    public ResponseEntity<ResponseDto<CreateResponse>> create(@Valid @RequestBody CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.create(CREATE_SUCCESS.getMessage(), this.closetService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<SliceDto<DefaultResponse>>> findAllSliceByUser(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(GET_LIST_SUCCESS.getMessage(), this.closetService.findAllSliceByUser(pageable)));
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<ResponseDto<DefaultResponse>> updateName(@PathVariable Long id, @Valid @RequestBody NameUpdateRequest response) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_NAME_SUCCESS.getMessage(), this.closetService.updateName(id, response)));
    }

    @PutMapping("/{id}/locked")
    public ResponseEntity<ResponseDto<DefaultResponse>> changeLockStatus(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseDto.create(CHANGE_LOCKED_STATUS_SUCCESS.getMessage(), this.closetService.changeLockStatus(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Long id) {
        this.closetService.delete(id);
        return ResponseEntity.ok(ResponseDto.create(DELETE_SUCCESS.getMessage()));
    }

}