package com.example.kingofadachi.domain.entity;

import java.util.Objects;

public class Favorite {
    private Long userId;
    private Long spotId;

    public Favorite(Long userId, Long spotId) {
        this.userId = userId;
        this.spotId = spotId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(userId, favorite.userId) &&
               Objects.equals(spotId, favorite.spotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, spotId);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "userId=" + userId +
                ", spotId=" + spotId +
                '}';
    }
}
