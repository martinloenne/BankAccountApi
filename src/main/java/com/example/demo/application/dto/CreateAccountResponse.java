package com.example.demo.application.dto;

public class CreateAccountResponse {
    public String AccountNumber;
    public String firstName;
    public String lastName;

    public CreateAccountResponse(String AccountNumber, String firstName, String lastName) {
        this.AccountNumber = AccountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
