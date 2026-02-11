package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;


import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByReservation_Id(Long reservationId);

    boolean existsByReservation_Id(Long reservationId);
    
    @Query("""
    	    SELECT COALESCE(SUM(b.totalAmount), 0)
    	    FROM Bill b
    	    WHERE b.reservation.checkIn >= :fromDate
    	      AND b.reservation.checkOut <= :toDate
    	""")
    	BigDecimal calculateRevenueBetween(
    	        @Param("fromDate") LocalDate fromDate,
    	        @Param("toDate") LocalDate toDate
    	);

}
