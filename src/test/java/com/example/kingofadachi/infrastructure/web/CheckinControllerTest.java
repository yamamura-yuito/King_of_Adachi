package com.example.kingofadachi.infrastructure.web;

import com.example.kingofadachi.application.service.CheckinService;
import com.example.kingofadachi.domain.model.Checkin;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;


@WebMvcTest(CheckinController.class)
class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckinService checkinService;

    @Autowired
    private ObjectMapper objectMapper;

    private Checkin testCheckin;
    // private User testUser; // Not directly used in controller tests but for context

    @BeforeEach
    void setUp() {
        // testUser = new User(1L, "testuser"); // From domain model, if needed
        testCheckin = new Checkin(1L, 1L, 1L, LocalDateTime.now());
    }

    @Test
    void createCheckin_success() throws Exception {
        Map<String, Long> payload = Map.of("spotId", 1L);
        when(checkinService.createCheckin(1L, 1L)).thenReturn(testCheckin);

        mockMvc.perform(post("/api/users/1/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.spotId", is(1)));
    }

    @Test
    void createCheckin_missingSpotId_returnsBadRequest() throws Exception {
        Map<String, Long> payload = Collections.emptyMap(); // No spotId

        mockMvc.perform(post("/api/users/1/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("spotId is required")));
    }

    @Test
    void createCheckin_serviceThrowsIllegalArgument_returnsNotFound() throws Exception {
        Map<String, Long> payload = Map.of("spotId", 1L);
        when(checkinService.createCheckin(1L, 1L)).thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(post("/api/users/1/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));
    }

    @Test
    void createCheckin_serviceThrowsUnexpectedException_returnsInternalServerError() throws Exception {
        Map<String, Long> payload = Map.of("spotId", 1L);
        when(checkinService.createCheckin(1L, 1L)).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/api/users/1/checkins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("An unexpected error occurred.")));
    }


    @Test
    void getCheckinsByUserId_success() throws Exception {
        List<Checkin> checkins = Collections.singletonList(testCheckin);
        when(checkinService.getCheckinsByUserId(1L)).thenReturn(checkins);

        mockMvc.perform(get("/api/users/1/checkins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void getCheckinsByUserId_emptyList() throws Exception {
        when(checkinService.getCheckinsByUserId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/1/checkins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getCheckinsByUserId_serviceThrowsIllegalArgument_returnsNotFound() throws Exception {
        when(checkinService.getCheckinsByUserId(1L)).thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(get("/api/users/1/checkins"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));
    }

    @Test
    void getCheckinsByUserId_serviceThrowsUnexpectedException_returnsInternalServerError() throws Exception {
        when(checkinService.getCheckinsByUserId(anyLong())).thenThrow(new RuntimeException("Unexpected DB error"));

        mockMvc.perform(get("/api/users/1/checkins"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error", is("An unexpected error occurred.")));
    }
}
