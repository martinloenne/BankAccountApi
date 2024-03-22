# Bankdata Code Challenge

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

For dependency management you can use either Gradle or Maven, and for persisting data H2 (in-memory DB) could be an option.

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