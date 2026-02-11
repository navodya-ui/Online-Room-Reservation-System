package com.oceanview.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ReservationCreateRequest {

    @NotBlank(message = "Reservation number is required")
    @Size(max = 30, message = "Reservation number must be max 30 characters")
    private String reservationNo;

    @NotBlank(message = "Guest name is required")
    @Size(max = 100, message = "Guest name must be max 100 characters")
    private String guestName;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be max 255 characters")
    private String address;

    @NotBlank(message = "Contact is required")
    @Size(max = 20, message = "Contact must be max 20 characters")
    @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "Contact number format is invalid")
    private String contact;

    @NotBlank(message = "Room type is required")
    @Size(max = 30, message = "Room type must be max 30 characters")
    private String roomType;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOut;

    public ReservationCreateRequest() {}

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
}
