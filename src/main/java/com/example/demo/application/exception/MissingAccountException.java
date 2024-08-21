package com.example.demo.application.exception;

import javax.management.RuntimeErrorException;

public class MissingAccountException extends RuntimeErrorException {
    public MissingAccountException(String accountNumber) {
        super(new Error("Account " + accountNumber + " does not exist"));
    }
}
