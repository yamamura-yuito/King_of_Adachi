package com.example.kingofadachi.infrastructure.web;

import com.example.kingofadachi.application.usecase.CheckinService;
import com.example.kingofadachi.domain.entity.Checkin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import com.example.kingofadachi.presentation.controller.CheckinController;


@WebMvcTest(CheckinController.class)
public class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckinService checkinService;

    @Autowired
    private ObjectMapper objectMapper;

    private Checkin testCheckin;
    private Long testUserId = 1L;
    private Long testSpotId = 1L;

    @BeforeEach
    void setUp() {
        testCheckin = new Checkin(1L, testUserId, testSpotId, LocalDateTime.now());
    }

    @Test
    void createCheckin_success() throws Exception {
        given(checkinService.createCheckin(testUserId, testSpotId)).willReturn(testCheckin);

        Map<String, Long> payload = Map.of("spotId", testSpotId);

        mockMvc.perform(post("/api/users/{userId}/checkins", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(testCheckin.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(testUserId.intValue())))
                .andExpect(jsonPath("$.spotId", is(testSpotId.intValue())));

        verify(checkinService).createCheckin(testUserId, testSpotId);
    }

    @Test
    void createCheckin_missingSpotId_returnsBadRequest() throws Exception {
        Map<String, Long> payload = Collections.emptyMap(); // Missing spotId

        mockMvc.perform(post("/api/users/{userId}/checkins", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("spotId is required")));
    }

    @Test
    void createCheckin_serviceThrowsIllegalArgumentException_returnsNotFound() throws Exception {
        given(checkinService.createCheckin(testUserId, testSpotId))
                .willThrow(new IllegalArgumentException("User not found"));

        Map<String, Long> payload = Map.of("spotId", testSpotId);

        mockMvc.perform(post("/api/users/{userId}/checkins", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));
    }

    @Test
    void createCheckin_serviceThrowsRuntimeException_returnsInternalServerError() throws Exception {
        given(checkinService.createCheckin(testUserId, testSpotId))
                .willThrow(new RuntimeException("Unexpected error"));

        Map<String, Long> payload = Map.of("spotId", testSpotId);

        mockMvc.perform(post("/api/users/{userId}/checkins", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("An unexpected error occurred.")));
    }


    @Test
    void getCheckinsByUserId_success() throws Exception {
        List<Checkin> checkins = Collections.singletonList(testCheckin);
        given(checkinService.getCheckinsByUserId(testUserId)).willReturn(checkins);

        mockMvc.perform(get("/api/users/{userId}/checkins", testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testCheckin.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(testUserId.intValue())));

        verify(checkinService).getCheckinsByUserId(testUserId);
    }

    @Test
    void getCheckinsByUserId_emptyList() throws Exception {
        given(checkinService.getCheckinsByUserId(testUserId)).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/{userId}/checkins", testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(checkinService).getCheckinsByUserId(testUserId);
    }

    @Test
    void getCheckinsByUserId_serviceThrowsIllegalArgumentException_returnsNotFound() throws Exception {
        given(checkinService.getCheckinsByUserId(testUserId))
                .willThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(get("/api/users/{userId}/checkins", testUserId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));

        verify(checkinService).getCheckinsByUserId(testUserId);
    }

    @Test
    void getCheckinsByUserId_serviceThrowsRuntimeException_returnsInternalServerError() throws Exception {
        given(checkinService.getCheckinsByUserId(testUserId))
                .willThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/users/{userId}/checkins", testUserId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("An unexpected error occurred.")));

        verify(checkinService).getCheckinsByUserId(testUserId);
    }
}
