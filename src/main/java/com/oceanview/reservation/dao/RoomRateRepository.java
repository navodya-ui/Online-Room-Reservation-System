package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.RoomRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRateRepository extends JpaRepository<RoomRate, String> {
    // roomType is the primary key (String)
}
