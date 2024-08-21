package com.example.demo.domain.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    private String lastName;

    private String accountNumber;

    private BigDecimal balance;

    private BigDecimal debitLimit;

    // Getters and Setters
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getDebitLimit() {
        return debitLimit;
    }

    public void initializeBalance() {
        this.balance = BigDecimal.ZERO;
        this.debitLimit = BigDecimal.ZERO;
    }

    public void addFunds(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than zero.");
        }

        // Add the amount to the current balance
        this.balance = this.balance.add(amount);
    }

    public void debitFunds(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to debit must be greater than zero.");
        }

        // Calculate the total available funds including the debit limit
        BigDecimal totalAvailableFunds = this.balance.add(this.debitLimit);

        if (amount.compareTo(totalAvailableFunds) > 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        // Deduct the amount from the current balance
        this.balance = this.balance.subtract(amount);
    }
}
