package com.example.demo.application.controller;

import com.example.demo.application.dto.ExchangeRateResponse;
import com.example.demo.application.exception.MissingAccountException;

import com.example.demo.domain.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService accountService) {
        this.exchangeRateService = accountService;
    }

    /**
     * GET endpoint to retrieve the exchange rate between DKK and USD for a given amount.
     *
     * @param amount The amount in DKK to be converted to USD.
     * @return A ResponseEntity containing the ExchangeRateResponse with the amount and its USD equivalent.
     */
    @GetMapping()
    public ResponseEntity<ExchangeRateResponse> getBalance(@RequestParam BigDecimal amount) throws MissingAccountException {
        // Validate the amount (e.g., must be greater than zero)
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Fetch the exchange rate
        BigDecimal exchangeCurrency = exchangeRateService.getExchangeRate(amount);

        // Prepare the response
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(amount, exchangeCurrency);

        return new ResponseEntity<>(exchangeRateResponse, HttpStatus.OK);
    }
}
