package com.example.demo.application.dto;

import java.math.BigDecimal;

public class TransferRequest {
    public final String fromAccountNumber;
    public final String toAccountNumber;
    public final BigDecimal amount;

    public TransferRequest(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }
}
