package com.example.demo.domain.interfaces;

import com.example.demo.application.dto.CreateAccountRequest;
import com.example.demo.application.dto.DepositRequest;
import com.example.demo.application.dto.TransferRequest;
import com.example.demo.application.exception.MissingAccountException;
import com.example.demo.domain.models.Account;

import java.math.BigDecimal;

public interface IAccountService {
    Account createAccount(CreateAccountRequest newAccount);

    void deposit(DepositRequest depositRequest) throws MissingAccountException;

    void transfer(TransferRequest transferRequest) throws MissingAccountException;

    BigDecimal getBalance(String accountNumber) throws MissingAccountException;

}
