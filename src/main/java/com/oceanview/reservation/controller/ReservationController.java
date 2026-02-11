package com.oceanview.reservation.controller;

import com.oceanview.reservation.dto.ReservationCreateRequest;
import com.oceanview.reservation.dto.ReservationResponse;
import com.oceanview.reservation.exception.UnauthorizedException;
import com.oceanview.reservation.service.ReservationService;
import com.oceanview.reservation.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;
    private final TokenService tokenService;

    public ReservationController(ReservationService reservationService, TokenService tokenService) {
        this.reservationService = reservationService;
        this.tokenService = tokenService;
    }

    private void requireToken(String token) {
        if (!tokenService.isValid(token)) {
            throw new UnauthorizedException("Session token is missing or invalid. Please login again.");
        }
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @RequestHeader(value = "X-SESSION-TOKEN", required = false) String token,
            @Valid @RequestBody ReservationCreateRequest request
    ) {
        requireToken(token);
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{reservationNo}")
    public ResponseEntity<ReservationResponse> getByReservationNo(
            @RequestHeader(value = "X-SESSION-TOKEN", required = false) String token,
            @PathVariable String reservationNo
    ) {
        requireToken(token);
        return ResponseEntity.ok(reservationService.getReservationByNo(reservationNo));
    }
}
