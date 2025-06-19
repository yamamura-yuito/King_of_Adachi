package com.example.kingofadachi.domain.repository;

import com.example.kingofadachi.domain.model.Checkin;
import java.util.List;

public interface CheckinRepository {
    /**
     * Saves a check-in record.
     *
     * @param checkin The Checkin object to save.
     * @return The saved Checkin object (potentially with an updated ID).
     */
    Checkin save(Checkin checkin);

    /**
     * Finds all check-ins for a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of Checkin objects for the user, or an empty list if none are found.
     */
    List<Checkin> findByUserId(Long userId);
}
