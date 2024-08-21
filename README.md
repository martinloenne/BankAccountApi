# Bankdata Code Challenge

## Bank Account API

## Overview

This API provides functionality for managing bank accounts, including the creating of accounts, depositing funds, transferring money between accounts, 
and retrieving account balances. Additionally the API includes an endpoint calculating the exchange rates between DKK and USD.

## Base URL

The application runs on `http://localhost:8080`

## Endpoints

### Create a New Account

**Endpoint:** `POST /accounts`

**Description:** Creates a new bank account with zero balance.

**Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Doe"
}
```
Response:

```json
{
"accountNumber": "1234567890",
"firstName": "John",
"lastName": "Doe"
}
```

Response Status: 200 OK

**Example cURL Request:**

```bash
curl -X POST "http://localhost:8080/accounts" \
-H "Content-Type: application/json" \
-d '{"firstName": "Agent", "lastName": "Smith"}'
```

### Deposit Funds
Endpoint: `POST /accounts/{accountNumber}/deposit`

Description: Deposits funds into the account.

Path Parameter:

**accountNumber** (string): The account number to which funds will be deposited.
Query Parameter:

**amount** (string): The amount to be deposited (e.g., "100.00").
**Response Status: 200 OK**

**Example cURL Request:**

```bash
curl -X POST "http://localhost:8080/accounts/1234567890/deposit?amount=100.00"
```
Transfer Funds
Endpoint: POST /accounts/transfer


### Transfer funds

Description: Transfers funds from one account to another.

Query Parameters:

**fromAccount** (string): 
The account number from which funds will be transferred.

**toAccount** (string): 
The account number to which funds will be transferred.

**amount** (string): 
The amount to be transferred (e.g., "50.00").

**Response** Status: 200 OK

**Error Response:**

If there are insufficient funds in the fromAccount, the API will return a 400 Bad Request status with the following error message:

```json
{
  "error": "Insufficient funds in account: <fromAccount>"
}
```

**Example cURL Request:**

```bash
curl -X POST "http://localhost:8080/accounts/transfer?fromAccount=1234567890&toAccount=0987654321&amount=50.00"
```
Get Account Balance

Endpoint: GET /accounts/{accountNumber}/balance


## Get Account Balance 

Description: Retrieves the balance of the specified account.

Path Parameter:

accountNumber (string): The account number for which the balance is requested.
Response:

```json
{
"balance": 50.00
}
```
Response Status: 200 OK

**Example cURL Request:**

```bash
curl -X GET "http://localhost:8080/accounts/1234567890/balance"
Error Handling
400 Bad Request: Invalid request parameters or body.
404 Not Found: Resource not found.
500 Internal Server Error: Unexpected server error.
Running the Application
Build and Run
Build the Application:
```

## GET Exchange Rate
Description: Retrieve the exchange rate between DKK (Danish Krone) and USD (United States Dollar) for a given amount.

**Request:**

**Method:** GET
**Endpoint:** /exchange-rate
Parameters:
**amount** (query parameter): The amount in DKK to be converted to USD.
Response:

Status Code: 200 OK if successful.
Content-Type: application/json
Body: A JSON object containing the amount in DKK and its equivalent in USD.
Response Example:

```json
{
"amount": 1000.00,
"usdEquivalent": 145.00
}
```

**Error Responses:**

400 Bad Request: If the amount is not provided or is less than or equal to zero.
Error Response Example:

```json
{
"error": "Invalid amount. It must be greater than zero."
}
```

Example curl Command
To retrieve the exchange rate for 1000 DKK, you can use the following curl command:

```bash
curl -X GET "http://localhost:8080/exchange-rate?amount=1000"
```


## Running the Application

Ensure you have Gradle installed. Run the following command to build the application:

```bash
./gradlew build
Run the Application:
```

Start the application using the following command:

```bash
./gradlew bootRun
```

The application will start on port 8080 by default.

## Running the test

### Using Command Line

```bash
./gradlew test
```

## TODO
- [ ] Dockerizing.
- [ ] Integration test for controllers.
- [ ] Health check.
- [ ] Fault-tolerance/fallback strategy on external APIs.
- [ ] Resilient-strategy on repository.
- [ ] Retry policy.
- [ ] API Doc.
- [ ] Explore the possibility for using vertical slice/CQRS.
- [ ] Explore the possibility for using events.


## Contact
For questions or issues, please contact lonne.martin@gmail.com


---

## Purpose
The purpose of this assignment is for us to talk about code that you've written and designed yourself based on a set of requirements.

## Expectations
We expect that you fork or copy this __repository (Github, Bitbucket, Gitlab)__ - where you host it does not matter, as long as you can send us a link that allows us to checkout the code and run it locally.

It's important that you include a README file which describes how we start your application, together with a description of how to hit your endpoints (e.g. cURL).

## Assignment
We do not expect you to spend days on this assignement, we suggest you spend a maximum of 4 hours. Feel free to leave rough edges as you see fit, we can talk about your decisions during the interview. Exception and error handling should be considered, but does not have to be fully implemented either.

## Bank account system
Implement the following in Java with Spring:

1. Create a new Account
2. Deposit money to an Account
3. Transfer money between two Accounts
4. Get Balance for an Account

Account should have at least the following attributes:
- Account number
- Balance
- First name
- Last name

For dependency management you can use Gradle, and for persisting data H2 (in-memory DB) could be an option.

Your API only has to support DKK currency. 

*Do not use any real world data, use only mock data!*

Create tests as you see fit, are you comfortable making code changes with the level of testing you've included?

## Bonus task
Create a new GET endpoint in your service, which can calculate the exchange rate between DKK and USD. 
The endpoint should return a JSON structure like so:

``{
    "DKK": 100,00,
    "USD": 14,61
}``

You should _not_ calculate the exchange rate internally in your service, but use a 3rd party provider with an API that can do it for you.
This shows us you know how to model your objects and use a Rest Client in your service.

A potential provider could be `https://www.exchangerate-api.com/docs/pair-conversion-requests` - it's free.

### _Additional_
If you know of any software design principles, take note when you implement them, you'll have your chance to shine during the interview. 


## Assessment
You'll be asked to present your solution to the interviewers where they'll be asking questions about your design decisions.
