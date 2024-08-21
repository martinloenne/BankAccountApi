package com.example.demo.domain.services;

import com.example.demo.application.dto.CreateAccountRequest;
import com.example.demo.application.dto.DepositRequest;
import com.example.demo.application.dto.TransferRequest;
import com.example.demo.application.exception.InsufficientFundsException;
import com.example.demo.application.exception.MissingAccountException;
import com.example.demo.domain.interfaces.IAccountService;
import com.example.demo.domain.models.Account;
import com.example.demo.infrastructure.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;


@Service
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final IAccountRepository accountRepository;
    private static final int ACCOUNT_NUMBER_LENGTH = 10; // 10 digits

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(CreateAccountRequest newAccount) {
        Account account = new Account();
        account.setFirstName(newAccount.firstName);
        account.setLastName(newAccount.lastName);

        String accountNumber = generateUniqueAccountNumber();
        account.setAccountNumber(accountNumber);
        account.initializeBalance();

        logger.info("Creating account for {} {}", account.getFirstName(), account.getLastName());

        return accountRepository.save(account);
    }

    @Transactional
    public void deposit(DepositRequest depositRequest) throws MissingAccountException {
        Account account = accountRepository.findAccountByAccountNumber(depositRequest.accountNumber);
        if (account == null)
            throw new MissingAccountException(depositRequest.accountNumber);

        logger.info("Depositing {} DKK to account {}", account.getAccountNumber(), account.getAccountNumber());
        account.addFunds(depositRequest.amount);

        // Designers note: In this example, we're performing a deposit operation on an account. While this approach is straightforward, an event-driven / event-sourcing architecture could provide additional benefits for this scenario.
        // - **Event-Driven Architecture**: Publish an "AccountDeposited" event to a message broker, e.g., `messageBroker.Publish("AccountDepositedTopic", accountDepositedEvent);`. This allows other services (e.g., notifications, fraud detection) to react asynchronously, improving scalability and decoupling.
        // - **Event-Sourcing**: Instead of saving the final state, record "AccountDeposited" events. This provides a complete history, enables easier debugging, and allows state reconstruction by replaying events.
        // Adopting these patterns can significantly improve system scalability, traceability, and maintainability.

        accountRepository.save(account);
    }

    @Transactional
    public void transfer(TransferRequest transferRequest) throws MissingAccountException {
        logger.info("Transferring {} DKK from account {} to account {}", transferRequest.amount,
            transferRequest.fromAccountNumber, transferRequest.toAccountNumber);

        // Check for valid amount
        if (transferRequest.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        // Fetch source account
        Account fromAccount = accountRepository.findAccountByAccountNumber(transferRequest.fromAccountNumber);
        if (fromAccount == null)
            throw new MissingAccountException(transferRequest.fromAccountNumber);

        // Fetch destination account
        Account toAccount = accountRepository.findAccountByAccountNumber(transferRequest.toAccountNumber);

        // Perform the transfer
        try {
            fromAccount.debitFunds(transferRequest.amount);
            toAccount.addFunds(transferRequest.amount);
        }
        catch (IllegalArgumentException e) {
            // Handling insufficient funds or other validation errors
            logger.error(e.getMessage());
            throw new InsufficientFundsException(transferRequest.toAccountNumber);
        }

        // Finalize transaction by saving the accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public BigDecimal getBalance(String accountNumber) throws MissingAccountException {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        if (account == null)
            throw new MissingAccountException(accountNumber);

        return account.getBalance();
    }

    /**
     * Generates a unique account number.
     * @return a unique account number
     */
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = generateRandomAccountNumber();
        } while (accountExists(accountNumber));
        logger.info("Generated unique account number: {}", accountNumber);
        return accountNumber;
    }

    /**
     * Generates a random account number of specified length.
     * This just for mocking purposes. Should be created according to banking system.
     * @return a randomly generated account number
     */
    private String generateRandomAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            Random random = new Random();
            accountNumber.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return accountNumber.toString();
    }

    /**
     * Checks if the account number already exists in the database.
     * @param accountNumber the account number to check
     * @return true if the account number exists, false otherwise
     */
    private boolean accountExists(String accountNumber) {
        Optional<Account> account = accountRepository.findById(Long.valueOf(accountNumber));
        boolean exists = account.isPresent();
        if (exists) {
            logger.warn("Account number {} already exists, generating a new one", accountNumber);
        }
        return exists;
    }
}
