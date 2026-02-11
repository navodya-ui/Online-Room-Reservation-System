package com.oceanview.reservation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "room_rates")
public class RoomRate {

    @Id
    @Column(name="room_type", length = 30)
    private String roomType;

    @Column(name="rate_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePerNight;

    // Constructors
    public RoomRate() {}

    public RoomRate(String roomType, BigDecimal ratePerNight) {
        this.roomType = roomType;
        this.ratePerNight = ratePerNight;
    }

    // Getters & Setters
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }
}
