package com.example.demo.domain.interfaces;

import java.math.BigDecimal;

public interface IExchangeService {
    BigDecimal getExchangeRate(BigDecimal amount);
}
