package com.example.kingofadachi.application.usecase;

import com.example.kingofadachi.application.usecase.impl.CheckinService;
import com.example.kingofadachi.domain.entity.Checkin;
import com.example.kingofadachi.domain.entity.Spot;
import com.example.kingofadachi.domain.entity.User;
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
public class CheckinServiceTest {

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
        testUser = new User(1L, "testUser");
        testSpot = new Spot(1L, "testSpot", "description", 0.0, 0.0, null, 0, null);
        testCheckin = new Checkin(1L, testUser.getId(), testSpot.getId(), LocalDateTime.now());
    }

    @Test
    void createCheckin_success() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(spotRepository.findById(testSpot.getId())).thenReturn(Optional.of(testSpot));
        when(checkinRepository.save(any(Checkin.class))).thenAnswer(invocation -> {
            Checkin checkinToSave = invocation.getArgument(0);
            // Simulate saving by setting an ID if it's null (as per CheckinService logic)
            return new Checkin(1L, checkinToSave.getUserId(), checkinToSave.getSpotId(), checkinToSave.getCheckinTime());
        });

        Checkin createdCheckin = checkinService.createCheckin(testUser.getId(), testSpot.getId());

        assertNotNull(createdCheckin);
        assertEquals(testUser.getId(), createdCheckin.getUserId());
        assertEquals(testSpot.getId(), createdCheckin.getSpotId());
        assertNotNull(createdCheckin.getCheckinTime());
        assertNotNull(createdCheckin.getId()); // Ensure ID is set

        verify(userRepository).findById(testUser.getId());
        verify(spotRepository).findById(testSpot.getId());
        verify(checkinRepository).save(any(Checkin.class));
    }

    @Test
    void createCheckin_userNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.createCheckin(testUser.getId(), testSpot.getId());
        });

        assertEquals("User not found with ID: " + testUser.getId(), exception.getMessage());
        verify(userRepository).findById(testUser.getId());
        verify(spotRepository, never()).findById(anyLong());
        verify(checkinRepository, never()).save(any(Checkin.class));
    }

    @Test
    void createCheckin_spotNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(spotRepository.findById(testSpot.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.createCheckin(testUser.getId(), testSpot.getId());
        });

        assertEquals("Spot not found with ID: " + testSpot.getId(), exception.getMessage());
        verify(userRepository).findById(testUser.getId());
        verify(spotRepository).findById(testSpot.getId());
        verify(checkinRepository, never()).save(any(Checkin.class));
    }

    @Test
    void getCheckinsByUserId_success() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(checkinRepository.findByUserId(testUser.getId())).thenReturn(Collections.singletonList(testCheckin));

        List<Checkin> foundCheckins = checkinService.getCheckinsByUserId(testUser.getId());

        assertNotNull(foundCheckins);
        assertEquals(1, foundCheckins.size());
        assertEquals(testCheckin, foundCheckins.get(0));

        verify(userRepository).findById(testUser.getId());
        verify(checkinRepository).findByUserId(testUser.getId());
    }

    @Test
    void getCheckinsByUserId_userNotFound_throwsIllegalArgumentException() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkinService.getCheckinsByUserId(testUser.getId());
        });

        assertEquals("User not found with ID: " + testUser.getId(), exception.getMessage());
        verify(userRepository).findById(testUser.getId());
        verify(checkinRepository, never()).findByUserId(anyLong());
    }

    @Test
    void getCheckinsByUserId_noCheckinsFound_returnsEmptyList() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(checkinRepository.findByUserId(testUser.getId())).thenReturn(Collections.emptyList());

        List<Checkin> foundCheckins = checkinService.getCheckinsByUserId(testUser.getId());

        assertNotNull(foundCheckins);
        assertTrue(foundCheckins.isEmpty());

        verify(userRepository).findById(testUser.getId());
        verify(checkinRepository).findByUserId(testUser.getId());
    }
}
