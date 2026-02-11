package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.BillRepository;
import com.oceanview.reservation.dao.RoomRateRepository;
import com.oceanview.reservation.exception.NotFoundException;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.RoomRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillingServiceTest {

    private BillRepository billRepository;
    private RoomRateRepository roomRateRepository;
    private ReservationService reservationService;
    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billRepository = mock(BillRepository.class);
        roomRateRepository = mock(RoomRateRepository.class);
        reservationService = mock(ReservationService.class);

        billingService = new BillingService(billRepository, roomRateRepository, reservationService);
    }

    @Test
    void generateBill_success_calculatesCorrectTotal() {
        Reservation res = new Reservation();
        res.setReservationNo("R001");
        res.setRoomType("Deluxe");
        res.setCheckIn(LocalDate.of(2026, 2, 15));
        res.setCheckOut(LocalDate.of(2026, 2, 18)); // 3 nights

        when(reservationService.getReservationEntityByNo("R001")).thenReturn(res);
        when(roomRateRepository.findById("Deluxe"))
                .thenReturn(Optional.of(new RoomRate("Deluxe", BigDecimal.valueOf(18000))));

        when(billRepository.save(any(Bill.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = billingService.generateOrGetBill("R001");

        assertEquals("R001", response.getReservationNo());
        assertEquals(3, response.getNights());
        assertEquals(new BigDecimal("54000"), response.getTotalAmount());
    }

    @Test
    void generateBill_roomRateMissing_throws404() {
        Reservation res = new Reservation();
        res.setReservationNo("R010");
        res.setRoomType("VIP");
        res.setCheckIn(LocalDate.of(2026, 2, 15));
        res.setCheckOut(LocalDate.of(2026, 2, 16));

        when(reservationService.getReservationEntityByNo("R010")).thenReturn(res);
        when(roomRateRepository.findById("VIP")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> billingService.generateOrGetBill("R010"));
    }
}
