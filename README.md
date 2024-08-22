# Bankdata Code Challenge

## Bank Account API

## Overview



This API provides functionality for managing bank accounts, including the creating of accounts, depositing funds, transferring money between accounts, 
and retrieving account balances. Additionally the API includes an endpoint calculating the exchange rates between DKK and USD.

## Base URL

The application runs on `http://localhost:8080`.

Additionally, this application uses H2 as an in-memory database for development and testing and can be accessed at `http://localhost:8080/h2-console`

## Running the Application

Ensure you have Gradle installed. Run the following command to build the application:

```bash
gradlew build
```
Run the Application:
Start the application using the following command:

```bash
gradlew bootRun
```

The application will start on port 8080 by default.

## Running the test

### Using Command Line

```bash
gradlew test  
```


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


## TODO
- [ ] Dockerizing.
- [ ] Integration test for controllers.
- [ ] Health check.
- [ ] Fault-tolerance/fallback strategy on external APIs.
- [ ] Resilient-strategy on repository.
- [ ] Retry policy.
- [ ] API Doc.
- [ ] Explore the possibility for using CQRS.
- [ ] Explore the possibility for using Event-Sourcing.


## Contact
For questions or issues, please contact lonne.martin@gmail.com

