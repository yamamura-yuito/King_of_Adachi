package com.example.kingofadachi.application.service.impl;

import com.example.kingofadachi.domain.entity.Checkin;
import com.example.kingofadachi.domain.entity.Spot;
import com.example.kingofadachi.domain.entity.User;
import com.example.kingofadachi.domain.repository.CheckinRepository;
import com.example.kingofadachi.domain.repository.SpotRepository;
import com.example.kingofadachi.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final UserRepository userRepository; // For user validation
    private final SpotRepository spotRepository; // For spot validation

    public CheckinService(CheckinRepository checkinRepository, UserRepository userRepository, SpotRepository spotRepository) {
        this.checkinRepository = checkinRepository;
        this.userRepository = userRepository;
        this.spotRepository = spotRepository;
    }

    @Transactional
    public Checkin createCheckin(Long userId, Long spotId) {
        // Validate user (optional but good practice)
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Validate spot (optional but good practice)
        Optional<Spot> spot = spotRepository.findById(spotId);
        if (spot.isEmpty()) {
            throw new IllegalArgumentException("Spot not found with ID: " + spotId);
        }

        // Create Checkin entity
        // The Checkin constructor requires an ID. Since it's auto-generated,
        // we'll pass null or use a constructor that doesn't require it if available.
        // Let's assume the Checkin model has a constructor for new checkins
        // or the ID will be set by the mapper.
        // The current Checkin model's constructor `public Checkin(Long id, Long userId, Long spotId, LocalDateTime checkinTime)`
        // requires an ID. We should adjust this or add a new constructor.
        // For now, let's assume the ID is handled by the database/mapper.
        Checkin newCheckin = new Checkin(null, userId, spotId, LocalDateTime.now());

        return checkinRepository.save(newCheckin);
    }

    public List<Checkin> getCheckinsByUserId(Long userId) {
        // Validate user (optional)
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // Or return empty list, or throw custom exception
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return checkinRepository.findByUserId(userId);
    }
}
