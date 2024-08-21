package com.example.demo.application.dto;

import java.math.BigDecimal;

public class ExchangeRateResponse {
    public final BigDecimal DKK;
    public final BigDecimal USD;

    public ExchangeRateResponse(BigDecimal dkk, BigDecimal usd) {
        DKK = dkk;
        USD = usd;
    }
}
