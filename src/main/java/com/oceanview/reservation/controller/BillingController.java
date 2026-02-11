package com.oceanview.reservation.controller;

import com.oceanview.reservation.dto.BillResponse;
import com.oceanview.reservation.exception.UnauthorizedException;
import com.oceanview.reservation.service.BillingService;
import com.oceanview.reservation.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    private final BillingService billingService;
    private final TokenService tokenService;

    public BillingController(BillingService billingService, TokenService tokenService) {
        this.billingService = billingService;
        this.tokenService = tokenService;
    }

    private void requireToken(String token) {
        if (!tokenService.isValid(token)) {
            throw new UnauthorizedException("Session token is missing or invalid. Please login again.");
        }
    }

    @GetMapping("/{reservationNo}")
    public ResponseEntity<BillResponse> getBill(
            @RequestHeader(value = "X-SESSION-TOKEN", required = false) String token,
            @PathVariable String reservationNo
    ) {
        requireToken(token);
        return ResponseEntity.ok(billingService.generateOrGetBill(reservationNo));
    }
}
