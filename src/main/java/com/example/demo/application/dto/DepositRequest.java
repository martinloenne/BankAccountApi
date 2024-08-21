package com.example.demo.application.dto;

import java.math.BigDecimal;

public class DepositRequest {
    public final String accountNumber;
    public final BigDecimal amount;

    public DepositRequest(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
