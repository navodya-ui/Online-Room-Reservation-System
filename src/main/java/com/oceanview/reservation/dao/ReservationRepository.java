package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNo(String reservationNo);

    boolean existsByReservationNo(String reservationNo);
    
    @Query("""
    	    SELECT r.roomType, COUNT(r)
    	    FROM Reservation r
    	    GROUP BY r.roomType
    	""")
    	List<Object[]> countReservationsByRoomType();

    	@Query("""
    	    SELECT COUNT(r)
    	    FROM Reservation r
    	    WHERE r.checkIn >= :fromDate
    	      AND r.checkOut <= :toDate
    	""")
    	Long countReservationsBetween(
    	        @Param("fromDate") java.time.LocalDate fromDate,
    	        @Param("toDate") java.time.LocalDate toDate
    	);

}
