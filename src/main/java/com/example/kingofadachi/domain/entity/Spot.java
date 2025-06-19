package com.example.kingofadachi.domain.entity;

import java.util.Objects;

public class Spot {
    private Long id;
    private String name;
    private String description;
    private Double latitude; // 緯度
    private Double longitude; // 経度

    // Constructors
    public Spot() {
    }

    public Spot(Long id, String name, String description, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spot spot = (Spot) o;
        return Objects.equals(id, spot.id) &&
               Objects.equals(name, spot.name) &&
               Objects.equals(description, spot.description) &&
               Objects.equals(latitude, spot.latitude) &&
               Objects.equals(longitude, spot.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Spot{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", latitude=" + latitude +
               ", longitude=" + longitude +
               '}';
    }
}
