package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.BillRepository;
import com.oceanview.reservation.dao.RoomRateRepository;
import com.oceanview.reservation.dto.BillResponse;
import com.oceanview.reservation.exception.BadRequestException;
import com.oceanview.reservation.exception.NotFoundException;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.RoomRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final RoomRateRepository roomRateRepository;
    private final ReservationService reservationService;

    public BillingService(BillRepository billRepository,
                          RoomRateRepository roomRateRepository,
                          ReservationService reservationService) {
        this.billRepository = billRepository;
        this.roomRateRepository = roomRateRepository;
        this.reservationService = reservationService;
    }

    public BillResponse generateOrGetBill(String reservationNo) {

        Reservation reservation = reservationService.getReservationEntityByNo(reservationNo);

        // If bill already exists, return it
        if (reservation.getBill() != null) {
            Bill existing = reservation.getBill();
            RoomRate rate = roomRateRepository.findById(reservation.getRoomType())
                    .orElseThrow(() -> new NotFoundException("Room type not found: " + reservation.getRoomType()));

            return new BillResponse(
                    reservation.getReservationNo(),
                    reservation.getRoomType(),
                    existing.getNights(),
                    rate.getRatePerNight(),
                    existing.getTotalAmount(),
                    existing.getGeneratedAt()
            );
        }

        long nightsLong = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nightsLong <= 0) throw new BadRequestException("Invalid reservation dates; cannot generate bill");

        int nights = (int) nightsLong;

        RoomRate rate = roomRateRepository.findById(reservation.getRoomType())
                .orElseThrow(() -> new NotFoundException("Room type not found: " + reservation.getRoomType()));

        BigDecimal total = rate.getRatePerNight().multiply(BigDecimal.valueOf(nights));

        Bill bill = new Bill();
        bill.setReservation(reservation);
        bill.setNights(nights);
        bill.setTotalAmount(total);

        // link both sides (important for JPA)
        reservation.setBill(bill);

        Bill saved = billRepository.save(bill);

        return new BillResponse(
                reservation.getReservationNo(),
                reservation.getRoomType(),
                saved.getNights(),
                rate.getRatePerNight(),
                saved.getTotalAmount(),
                saved.getGeneratedAt()
        );
    }
}
