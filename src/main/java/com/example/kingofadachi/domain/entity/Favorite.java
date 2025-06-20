package com.example.kingofadachi.domain.entity;

import java.util.Objects;

public class Favorite {

    private Long id;
    private Long userId;
    private Long spotId; // Spot オブジェクトではなく spotId を保持

    public Favorite() {
    }

    public Favorite(Long id, Long userId, Long spotId) {
        this.id = id;
        this.userId = userId;
        this.spotId = spotId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id) &&
               Objects.equals(userId, favorite.userId) &&
               Objects.equals(spotId, favorite.spotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, spotId);
    }

    @Override
    public String toString() {
        return "Favorite{" +
               "id=" + id +
               ", userId=" + userId +
               ", spotId=" + spotId +
               '}';
    }
}
