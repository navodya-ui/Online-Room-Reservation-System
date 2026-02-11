package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNo(String reservationNo);

    boolean existsByReservationNo(String reservationNo);
}
