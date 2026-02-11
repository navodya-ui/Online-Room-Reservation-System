package com.oceanview.reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillResponse {

    private String reservationNo;
    private String roomType;
    private Integer nights;
    private BigDecimal ratePerNight;
    private BigDecimal totalAmount;
    private LocalDateTime generatedAt;

    public BillResponse() {}

    public BillResponse(String reservationNo, String roomType, Integer nights,
                        BigDecimal ratePerNight, BigDecimal totalAmount, LocalDateTime generatedAt) {
        this.reservationNo = reservationNo;
        this.roomType = roomType;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = totalAmount;
        this.generatedAt = generatedAt;
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Integer getNights() { return nights; }
    public void setNights(Integer nights) { this.nights = nights; }

    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
