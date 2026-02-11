package com.oceanview.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationResponse {

    private String reservationNo;
    private String guestName;
    private String address;
    private String contact;
    private String roomType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDateTime createdAt;

    public ReservationResponse() {}

    public ReservationResponse(
            String reservationNo, String guestName, String address, String contact,
            String roomType, LocalDate checkIn, LocalDate checkOut, LocalDateTime createdAt
    ) {
        this.reservationNo = reservationNo;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
