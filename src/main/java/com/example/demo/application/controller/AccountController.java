package com.example.demo.application.controller;

import com.example.demo.application.dto.CreateAccountRequest;
import com.example.demo.application.dto.CreateAccountResponse;
import com.example.demo.application.dto.DepositRequest;
import com.example.demo.application.dto.TransferRequest;
import com.example.demo.application.exception.InvalidOperationException;
import com.example.demo.application.exception.MissingAccountException;
import com.example.demo.domain.models.Account;
import com.example.demo.domain.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {
  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest account) {

    Account createdAccount = accountService.createAccount(account);

    CreateAccountResponse accountResponse  = new CreateAccountResponse(createdAccount.getAccountNumber(),
            createdAccount.getFirstName(),
            createdAccount.getLastName());

    return new ResponseEntity<>(accountResponse, HttpStatus.OK);
  }

  @PostMapping("/transfer")
  public ResponseEntity<Void> transfer(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam String amount) throws InvalidOperationException {
    TransferRequest transferRequest = new TransferRequest(fromAccount, toAccount, new BigDecimal(amount));
    accountService.transfer(transferRequest);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{accountNumber}/deposit")
  public ResponseEntity<Void> deposit(@PathVariable String accountNumber, @RequestParam String amount) throws MissingAccountException {
    DepositRequest depositRequest = new DepositRequest(accountNumber, new BigDecimal(amount));

    accountService.deposit(depositRequest);

    return ResponseEntity.ok().build();
  }

  @GetMapping("{accountNumber}/balance")
  public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) throws MissingAccountException {
    BigDecimal balance = accountService.getBalance(accountNumber);

    return ResponseEntity.ok(balance.doubleValue());
  }
}
