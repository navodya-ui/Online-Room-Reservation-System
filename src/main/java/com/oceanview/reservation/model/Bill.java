package com.oceanview.reservation.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique reservation_id in DB -> One-to-One relationship
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, unique = true)
    private Reservation reservation;

    @Column(nullable = false)
    private Integer nights;

    @Column(name="total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name="generated_at", nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @PrePersist
    public void onGenerate() {
        this.generatedAt = LocalDateTime.now();
    }

    // Constructors
    public Bill() {}

    public Bill(Reservation reservation, Integer nights, BigDecimal totalAmount) {
        this.reservation = reservation;
        this.nights = nights;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public Integer getNights() { return nights; }
    public void setNights(Integer nights) { this.nights = nights; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
