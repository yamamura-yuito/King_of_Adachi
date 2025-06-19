package com.example.kingofadachi.presentation.controller;

import com.example.kingofadachi.application.service.impl.CheckinService;
import com.example.kingofadachi.domain.entity.Checkin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // Base path for checkin related endpoints
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    /**
     * Creates a new check-in (stamp) for a user at a specific spot.
     *
     * @param userId The ID of the user checking in.
     * @param payload A map containing the "spotId".
     * @return The created Checkin object.
     */
    @PostMapping("/users/{userId}/checkins")
    public ResponseEntity<?> createCheckin(@PathVariable Long userId, @RequestBody Map<String, Long> payload) {
        Long spotId = payload.get("spotId");
        if (spotId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "spotId is required"));
        }
        try {
            Checkin checkin = checkinService.createCheckin(userId, spotId);
            return new ResponseEntity<>(checkin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Generic error handler for other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }

    /**
     * Retrieves all check-ins (stamps) for a specific user.
     *
     * @param userId The ID of the user whose check-ins are to be retrieved.
     * @return A list of Checkin objects.
     */
    @GetMapping("/users/{userId}/checkins")
    public ResponseEntity<?> getCheckinsByUserId(@PathVariable Long userId) {
        try {
            List<Checkin> checkins = checkinService.getCheckinsByUserId(userId);
            if (checkins.isEmpty()) {
                // Consider if NO_CONTENT is more appropriate if user exists but has no checkins
                // vs. user not found. For now, returning OK with empty list is fine.
                // The service currently throws IllegalArgumentException if user not found.
                return new ResponseEntity<>(checkins, HttpStatus.OK);
            }
            return new ResponseEntity<>(checkins, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }
}
