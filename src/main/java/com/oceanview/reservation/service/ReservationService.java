package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.ReservationRepository;
import com.oceanview.reservation.dao.RoomRateRepository;
import com.oceanview.reservation.dto.ReservationCreateRequest;
import com.oceanview.reservation.dto.ReservationResponse;
import com.oceanview.reservation.exception.BadRequestException;
import com.oceanview.reservation.exception.DuplicateException;
import com.oceanview.reservation.exception.NotFoundException;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.RoomRate;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRateRepository roomRateRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRateRepository roomRateRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRateRepository = roomRateRepository;
    }

    public ReservationResponse createReservation(ReservationCreateRequest req) {

        if (reservationRepository.existsByReservationNo(req.getReservationNo())) {
            throw new DuplicateException("Reservation number already exists: " + req.getReservationNo());
        }

        // Date validation
        if (!req.getCheckOut().isAfter(req.getCheckIn())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        long nights = ChronoUnit.DAYS.between(req.getCheckIn(), req.getCheckOut());
        if (nights <= 0) {
            throw new BadRequestException("Stay must be at least 1 night");
        }

        // Validate room type exists in room_rates
        RoomRate rate = roomRateRepository.findById(req.getRoomType())
                .orElseThrow(() -> new NotFoundException("Room type not found: " + req.getRoomType()));

        Reservation r = new Reservation();
        r.setReservationNo(req.getReservationNo());
        r.setGuestName(req.getGuestName());
        r.setAddress(req.getAddress());
        r.setContact(req.getContact());
        r.setRoomType(rate.getRoomType()); // store normalized value
        r.setCheckIn(req.getCheckIn());
        r.setCheckOut(req.getCheckOut());

        Reservation saved = reservationRepository.save(r);

        return new ReservationResponse(
                saved.getReservationNo(),
                saved.getGuestName(),
                saved.getAddress(),
                saved.getContact(),
                saved.getRoomType(),
                saved.getCheckIn(),
                saved.getCheckOut(),
                saved.getCreatedAt()
        );
    }

    public ReservationResponse getReservationByNo(String reservationNo) {
        Reservation r = reservationRepository.findByReservationNo(reservationNo)
                .orElseThrow(() -> new NotFoundException("Reservation not found: " + reservationNo));

        return new ReservationResponse(
                r.getReservationNo(),
                r.getGuestName(),
                r.getAddress(),
                r.getContact(),
                r.getRoomType(),
                r.getCheckIn(),
                r.getCheckOut(),
                r.getCreatedAt()
        );
    }

    // Internal helper for BillingService
    public Reservation getReservationEntityByNo(String reservationNo) {
        return reservationRepository.findByReservationNo(reservationNo)
                .orElseThrow(() -> new NotFoundException("Reservation not found: " + reservationNo));
    }
}
