package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.BillRepository;
import com.oceanview.reservation.dao.ReservationRepository;
import com.oceanview.reservation.dto.RevenueReportResponse;
import com.oceanview.reservation.dto.RoomTypeReportResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final BillRepository billRepository;
    private final ReservationRepository reservationRepository;

    public ReportService(BillRepository billRepository,
                         ReservationRepository reservationRepository) {
        this.billRepository = billRepository;
        this.reservationRepository = reservationRepository;
    }

    public RevenueReportResponse getRevenueReport(LocalDate from, LocalDate to) {

        BigDecimal total = billRepository.calculateRevenueBetween(from, to);

        return new RevenueReportResponse(
                from.toString(),
                to.toString(),
                total
        );
    }

    public Long getReservationCount(LocalDate from, LocalDate to) {
        return reservationRepository.countReservationsBetween(from, to);
    }

    public List<RoomTypeReportResponse> getRoomTypeReport() {

        List<Object[]> raw = reservationRepository.countReservationsByRoomType();
        List<RoomTypeReportResponse> result = new ArrayList<>();

        for (Object[] row : raw) {
            result.add(new RoomTypeReportResponse(
                    (String) row[0],
                    (Long) row[1]
            ));
        }

        return result;
    }
}
