package com.example.demo.domain.services;

import com.example.demo.domain.interfaces.IExchangeService;
import com.example.demo.infrastructure.clients.ExchangeRateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class ExchangeRateService implements IExchangeService {
    private final String BASECURRENCY = "DKK";
    private final String TARGETCURRENCY = "USD";
    private final ExchangeRateClient exchangeRateClient;

    @Autowired
    public ExchangeRateService(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    public BigDecimal getExchangeRate(BigDecimal amount) {
        return exchangeRateClient.getExchangeRate(BASECURRENCY, TARGETCURRENCY, amount);
    }
}
