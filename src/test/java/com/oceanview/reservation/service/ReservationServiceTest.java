package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.ReservationRepository;
import com.oceanview.reservation.dao.RoomRateRepository;
import com.oceanview.reservation.dto.ReservationCreateRequest;
import com.oceanview.reservation.exception.BadRequestException;
import com.oceanview.reservation.exception.DuplicateException;
import com.oceanview.reservation.exception.NotFoundException;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.RoomRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private RoomRateRepository roomRateRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        roomRateRepository = mock(RoomRateRepository.class);
        reservationService = new ReservationService(reservationRepository, roomRateRepository);
    }

    @Test
    void createReservation_success() {
        ReservationCreateRequest req = new ReservationCreateRequest();
        req.setReservationNo("R001");
        req.setGuestName("Nimal");
        req.setAddress("Galle");
        req.setContact("0771234567");
        req.setRoomType("Deluxe");
        req.setCheckIn(LocalDate.of(2026, 2, 15));
        req.setCheckOut(LocalDate.of(2026, 2, 18));

        when(reservationRepository.existsByReservationNo("R001")).thenReturn(false);
        when(roomRateRepository.findById("Deluxe"))
                .thenReturn(Optional.of(new RoomRate("Deluxe", BigDecimal.valueOf(18000))));

        // simulate save
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            // mimic DB id
            // (id not necessary for response)
            return r;
        });

        var response = reservationService.createReservation(req);

        assertEquals("R001", response.getReservationNo());
        assertEquals("Nimal", response.getGuestName());
        assertEquals("Deluxe", response.getRoomType());

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(captor.capture());
        assertEquals("R001", captor.getValue().getReservationNo());
    }

    @Test
    void createReservation_duplicateReservationNo_throws409() {
        ReservationCreateRequest req = new ReservationCreateRequest();
        req.setReservationNo("R001");
        req.setGuestName("Nimal");
        req.setAddress("Galle");
        req.setContact("0771234567");
        req.setRoomType("Deluxe");
        req.setCheckIn(LocalDate.of(2026, 2, 15));
        req.setCheckOut(LocalDate.of(2026, 2, 18));

        when(reservationRepository.existsByReservationNo("R001")).thenReturn(true);

        assertThrows(DuplicateException.class, () -> reservationService.createReservation(req));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void createReservation_invalidDates_throws400() {
        ReservationCreateRequest req = new ReservationCreateRequest();
        req.setReservationNo("R002");
        req.setGuestName("Kamal");
        req.setAddress("Galle");
        req.setContact("0771234567");
        req.setRoomType("Single");
        req.setCheckIn(LocalDate.of(2026, 2, 18));
        req.setCheckOut(LocalDate.of(2026, 2, 18)); // same day

        when(reservationRepository.existsByReservationNo("R002")).thenReturn(false);

        assertThrows(BadRequestException.class, () -> reservationService.createReservation(req));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void createReservation_roomTypeNotFound_throws404() {
        ReservationCreateRequest req = new ReservationCreateRequest();
        req.setReservationNo("R003");
        req.setGuestName("Sunil");
        req.setAddress("Galle");
        req.setContact("0771234567");
        req.setRoomType("VIP"); // not existing
        req.setCheckIn(LocalDate.of(2026, 2, 15));
        req.setCheckOut(LocalDate.of(2026, 2, 16));

        when(reservationRepository.existsByReservationNo("R003")).thenReturn(false);
        when(roomRateRepository.findById("VIP")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservationService.createReservation(req));
    }
}
