package com.example.demo;

import com.example.demo.application.dto.*;
import com.example.demo.application.exception.*;
import com.example.demo.domain.models.*;
import com.example.demo.domain.services.*;
import com.example.demo.infrastructure.repository.IAccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;


@SpringBootTest
class AccountServiceUnitTest {
	@MockBean
	private IAccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	private Account existingAccount;

	@BeforeEach
	public void setUp() {
		existingAccount = new Account();
		existingAccount.setAccountNumber("1337");
		existingAccount.setFirstName("Agent");
		existingAccount.setLastName("Smith");
		existingAccount.initializeBalance();
		existingAccount.setBalance(BigDecimal.valueOf(1000.00));
	}

	@Test
	public void testCreateAccount() {
		CreateAccountRequest request = new CreateAccountRequest();
		request.firstName = "Agent";
		request.lastName = "Smith";
		when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);
		Account createdAccount = accountService.createAccount(request);

		assertNotNull(createdAccount);
		assertEquals("Agent", createdAccount.getFirstName());
		assertEquals("Smith", createdAccount.getLastName());
	}

	@Test
	public void testDepositSuccess() throws MissingAccountException {
		final String accountNumber = "1234567890";
		BigDecimal amount = BigDecimal.valueOf(500.00);
		DepositRequest depositRequest = new DepositRequest(accountNumber, amount);

		// Set up the mock to return the existing account
		when(accountRepository.findAccountByAccountNumber("1234567890")).thenReturn(existingAccount);
		// Ensure save is called and returns the updated account
		when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Perform the deposit
		accountService.deposit(depositRequest);

		// Assert that the balance was updated correctly
		assertEquals(BigDecimal.valueOf(1500.00), existingAccount.getBalance());

		// Verify that the save method was called on the repository
		verify(accountRepository).save(existingAccount);
	}

	@Test
	public void testDepositAccountNotFound() {
		String accountNumber = "99999";
		BigDecimal amount = BigDecimal.valueOf(500.00);
		DepositRequest depositRequest = new DepositRequest(accountNumber, amount);

		when(accountRepository.findAccountByAccountNumber("9999999999")).thenReturn(null);

		assertThrows(MissingAccountException.class, () -> accountService.deposit(depositRequest));
	}

	@Test
	public void testTransferSuccess() {
		String fromAccountNumber = "1337";
		String toAccountNumber = "123123";
		BigDecimal amount = BigDecimal.valueOf(200.00);
		TransferRequest transferRequest = new TransferRequest(fromAccountNumber, toAccountNumber, amount);

		Account toAccount = new Account();
		toAccount.initializeBalance();
		toAccount.setAccountNumber("123123");
		toAccount.setBalance(BigDecimal.valueOf(500.00));

		when(accountRepository.findAccountByAccountNumber("1337")).thenReturn(existingAccount);
		when(accountRepository.findAccountByAccountNumber("123123")).thenReturn(toAccount);
		when(accountRepository.save(any(Account.class))).thenReturn(existingAccount).thenReturn(toAccount);

		accountService.transfer(transferRequest);

		assertEquals(BigDecimal.valueOf(800.00), existingAccount.getBalance());
		assertEquals(BigDecimal.valueOf(700.00), toAccount.getBalance());
	}

	@Test
	public void testTransferInsufficientFunds() {
		String fromAccountNumber = "1337";
		String toAccountNumber = "0987654321";
		BigDecimal amount = BigDecimal.valueOf(2000.00);
		TransferRequest transferRequest = new TransferRequest(fromAccountNumber, toAccountNumber, amount);

		Account toAccount = new Account();
		toAccount.initializeBalance();
		toAccount.setAccountNumber("09871231");
		toAccount.setBalance(BigDecimal.valueOf(500.00));

		when(accountRepository.findAccountByAccountNumber("1337")).thenReturn(existingAccount);
		when(accountRepository.findAccountByAccountNumber("09871231")).thenReturn(toAccount);

		assertThrows(InsufficientFundsException.class, () -> accountService.transfer(transferRequest));
	}

	@Test
	public void testGetBalanceSuccess() throws MissingAccountException {
		when(accountRepository.findAccountByAccountNumber("1234567890")).thenReturn(existingAccount);

		BigDecimal balance = accountService.getBalance("1234567890");

		assertEquals(BigDecimal.valueOf(1000.00), balance);
	}

	@Test
	public void testGetBalanceAccountNotFound() {
		when(accountRepository.findAccountByAccountNumber("9999999999")).thenReturn(null);

		assertThrows(MissingAccountException.class, () -> accountService.getBalance("9999999999"));
	}

}
