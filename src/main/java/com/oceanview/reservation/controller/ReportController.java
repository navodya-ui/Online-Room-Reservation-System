package com.oceanview.reservation.controller;

import com.oceanview.reservation.dto.RevenueReportResponse;
import com.oceanview.reservation.dto.RoomTypeReportResponse;
import com.oceanview.reservation.exception.UnauthorizedException;
import com.oceanview.reservation.service.ReportService;
import com.oceanview.reservation.service.TokenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;
    private final TokenService tokenService;

    public ReportController(ReportService reportService,
                            TokenService tokenService) {
        this.reportService = reportService;
        this.tokenService = tokenService;
    }

    private void requireToken(String token) {
        if (!tokenService.isValid(token)) {
            throw new UnauthorizedException("Invalid session token");
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportResponse> revenue(
            @RequestHeader("X-SESSION-TOKEN") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        requireToken(token);
        return ResponseEntity.ok(reportService.getRevenueReport(from, to));
    }

    @GetMapping("/reservation-count")
    public ResponseEntity<Long> reservationCount(
            @RequestHeader("X-SESSION-TOKEN") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        requireToken(token);
        return ResponseEntity.ok(reportService.getReservationCount(from, to));
    }

    @GetMapping("/room-types")
    public ResponseEntity<List<RoomTypeReportResponse>> roomTypeReport(
            @RequestHeader("X-SESSION-TOKEN") String token
    ) {
        requireToken(token);
        return ResponseEntity.ok(reportService.getRoomTypeReport());
    }
}
