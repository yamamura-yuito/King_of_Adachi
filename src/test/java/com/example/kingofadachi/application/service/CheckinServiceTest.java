package com.example.kingofadachi.application.service;

import com.example.kingofadachi.domain.model.Checkin;
import com.example.kingofadachi.domain.model.Spot;
import com.example.kingofadachi.domain.model.User;
import com.example.kingofadachi.domain.repository.CheckinRepository;
import com.example.kingofadachi.domain.repository.SpotRepository;
import com.example.kingofadachi.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckinServiceTest {

    @Mock
    private CheckinRepository checkinRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private CheckinService checkinService;

    private User testUser;
    private Spot testSpot;
    private Checkin testCheckin;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testuser");
        testSpot = new Spot(1L, "Test Spot", "Description", 35.0, 139.0);
        testCheckin = new Checkin(1L, 1L, 1L, LocalDateTime.now());
    }

    @Test
    void createCheckin_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(spotRepository.findById(1L)).thenReturn(Optional.of(testSpot));
        when(checkinRepository.save(any(Checkin.class))).thenAnswer(invocation -> {
            Checkin c = invocation.getArgument(0);
            // Simulate ID generation by repository/mapper
            return new Checkin(1L, c.getUserId(), c.getSpotId(), c.getCheckinTime());
        });

        Checkin createdCheckin = checkinService.createCheckin(1L, 1L);

        assertNotNull(createdCheckin);
        assertEquals(1L, createdCheckin.getId());
        assertEquals(1L, createdCheckin.getUserId());
        assertEquals(1L, createdCheckin.getSpotId());
        verify(checkinRepository, times(1)).save(any(Checkin.class));
    }

    @Test
    void createCheckin_userNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.createCheckin(1L, 1L);
        });
        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(checkinRepository, never()).save(any(Checkin.class));
    }

    @Test
    void createCheckin_spotNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(spotRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.createCheckin(1L, 1L);
        });
        assertEquals("Spot not found with ID: 1", exception.getMessage());
        verify(checkinRepository, never()).save(any(Checkin.class));
    }

    @Test
    void getCheckinsByUserId_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(checkinRepository.findByUserId(1L)).thenReturn(Collections.singletonList(testCheckin));

        List<Checkin> checkins = checkinService.getCheckinsByUserId(1L);

        assertNotNull(checkins);
        assertEquals(1, checkins.size());
        assertEquals(testCheckin, checkins.get(0));
        verify(checkinRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getCheckinsByUserId_userNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.getCheckinsByUserId(1L);
        });
        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(checkinRepository, never()).findByUserId(anyLong());
    }

    @Test
    void getCheckinsByUserId_noCheckinsFound_returnsEmptyList() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(checkinRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        List<Checkin> checkins = checkinService.getCheckinsByUserId(1L);

        assertNotNull(checkins);
        assertTrue(checkins.isEmpty());
        verify(checkinRepository, times(1)).findByUserId(1L);
    }
}
