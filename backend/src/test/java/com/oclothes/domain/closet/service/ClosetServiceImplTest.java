package com.oclothes.domain.closet.service;

import com.oclothes.BaseTest;
import com.oclothes.domain.closet.dao.ClosetRepository;
import com.oclothes.domain.closet.domain.Closet;
import com.oclothes.domain.closet.dto.ClosetDto;
import com.oclothes.domain.closet.dto.ClosetMapper;
import com.oclothes.domain.closet.exception.ClosetNotEmptyException;
import com.oclothes.domain.cloth.service.ClothService;
import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.util.SecurityUtils;
import com.oclothes.global.dto.SliceDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.util.Collections;
import java.util.Optional;

import static com.oclothes.domain.closet.dto.ClosetDto.CreateRequest;
import static com.oclothes.domain.closet.dto.ClosetDto.CreateResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClosetServiceImplTest extends BaseTest {

    @Spy
    private ClosetMapper closetMapper = Mappers.getMapper(ClosetMapper.class);

    @Mock
    private ClosetRepository closetRepository;

    @Mock
    private ClothService clothService;

    @InjectMocks
    ClosetServiceImpl closetService;

    private static MockedStatic<SecurityUtils> securityUtilsMock;

    @BeforeAll
    static void beforeAll() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
    }

    @AfterAll
    static void afterAll() {
        securityUtilsMock.close();
    }

    @DisplayName("유저가 옷장 생성을 성공한다.")
    @Test
    void createTest() {
        final String name = "my-first-closet-1";
        final CreateRequest request = new CreateRequest(name, false);
        final Closet closet = new Closet(name, false, null);
        final User user = User.builder().build();

        securityUtilsMock.when(SecurityUtils::getLoggedInUser).thenReturn(user);
        when(this.closetRepository.save(any())).thenReturn(closet);

        CreateResponse response = this.closetService.create(request);
        assertEquals(name, response.getName());
        assertFalse(response.isLocked());
    }

    @DisplayName("옷장을 리스트를 SliceDto로 반환 성공한다.")
    @Test
    void findAllSliceByUser() {
        final PageRequest pageRequest = PageRequest.of(1, 1);
        final SliceImpl<Closet> slice = new SliceImpl<>(Collections.emptyList());
        final User user = User.builder().build();

        when(this.closetRepository.findAllSliceByUser(any(), any())).thenReturn(slice);

        final SliceDto<ClosetDto.DefaultResponse> response = this.closetService.findAllSliceByUser(pageRequest);
        assertFalse(response.hasNext());
        assertTrue(response.isEmpty());
    }

    @DisplayName("옷장 이름 변경을 성공한다.")
    @Test
    void updateNameTest() {
        final long id = 1L;
        final Closet closet = Closet.builder().name("beforeName").locked(true).build();
        final ClosetDto.NameUpdateRequest request = new ClosetDto.NameUpdateRequest(id, "test");

        when(this.closetRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(closet));

        final ClosetDto.DefaultResponse response = this.closetService.updateName(id, request);
        assertEquals("test", response.getName());
        assertTrue(response.isLocked());
    }

    @DisplayName("옷장 공개 상태가 true일 겨우 false로 변경한다.")
    @Test
    void changeLockStatusFalseTest() {
        final long id = 1L;
        final Closet closet = Closet.builder().locked(true).build();

        when(this.closetRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(closet));

        final ClosetDto.DefaultResponse response = this.closetService.changeLockStatus(1L);
        assertFalse(response.isLocked());
    }

    @DisplayName("옷장 공개 상태가 false일 겨우 true로 변경한다.")
    @Test
    void changeLockStatusTrueTest() {
        final long id = 1L;
        final Closet closet = Closet.builder().locked(false).build();

        when(this.closetRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(closet));

        final ClosetDto.DefaultResponse response = this.closetService.changeLockStatus(1L);
        assertTrue(response.isLocked());
    }

    @DisplayName("옷장 안의 옷이 0보다 클 경우 ")
    @Test
    void whenClothesCountGreaterThenZeroThenDeleteFail() {
        final long id = 1L;
        final Closet closet = Closet.builder().locked(false).build();

        when(this.closetRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(closet));
        when(this.clothService.getSizeByCloset(any())).thenReturn(1L);

        assertThrows(ClosetNotEmptyException.class, () -> this.closetService.delete(id));
    }

    @DisplayName("옷장 삭제를 성공한다.")
    @Test
    void deleteSuccess() {
        final long id = 1L;
        final Closet closet = Closet.builder().locked(false).build();

        when(this.closetRepository.findByIdAndUser(any(), any())).thenReturn(Optional.of(closet));
        when(this.clothService.getSizeByCloset(any())).thenReturn(0L);
        doNothing().when(this.closetRepository).delete(any());

        this.closetService.delete(id);
        verify(this.closetRepository, atMostOnce()).delete(any());
    }
}