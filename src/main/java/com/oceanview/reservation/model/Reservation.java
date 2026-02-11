package com.oceanview.reservation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "reservations",
    indexes = {
        @Index(name = "idx_reservation_no", columnList = "reservation_no")
    }
)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="reservation_no", nullable = false, unique = true, length = 30)
    private String reservationNo;

    @Column(name="guest_name", nullable = false, length = 100)
    private String guestName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 20)
    private String contact;

    @Column(name="room_type", nullable = false, length = 30)
    private String roomType;

    @Column(name="check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name="check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // One reservation can have one bill
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Bill bill;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructors
    public Reservation() {}

    // Getters & Setters
    public Long getId() { return id; }

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

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
}
