package com.example.demo.infrastructure.clients.response;

import java.math.BigDecimal;

public class ExchangeRateResponse {
    // Indicates successful result
    public String result;

    // Base currency e.g "USD"
    public String base_code;

    // Target currency
    public String target_code;

    public BigDecimal conversion_rate;

    public BigDecimal conversion_result;
}
