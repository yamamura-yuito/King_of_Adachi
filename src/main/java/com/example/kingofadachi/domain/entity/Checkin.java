package com.example.kingofadachi.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Checkin {
    private Long id;
    private Long userId;
    private Long spotId;
    private LocalDateTime checkinTime;

    public Checkin(Long id, Long userId, Long spotId, LocalDateTime checkinTime) {
        this.id = id;
        this.userId = userId;
        this.spotId = spotId;
        this.checkinTime = checkinTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checkin checkin = (Checkin) o;
        return Objects.equals(id, checkin.id) &&
                Objects.equals(userId, checkin.userId) &&
                Objects.equals(spotId, checkin.spotId) &&
                Objects.equals(checkinTime, checkin.checkinTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, spotId, checkinTime);
    }

    @Override
    public String toString() {
        return "Checkin{" +
                "id=" + id +
                ", userId=" + userId +
                ", spotId=" + spotId +
                ", checkinTime=" + checkinTime +
                '}';
    }
}
