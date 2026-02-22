# 🏦 E-Banking Management System

![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)
![OOP](https://img.shields.io/badge/Paradigm-Object--Oriented-success.svg)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-purple.svg)

A robust Java console application designed to simulate core banking functionalities. This project serves as a practical implementation of the **Model-View-Service (MVS)** architecture, focusing on secure data handling, custom exception management, and persistent storage through serialization.

## Key Features
* **Account Management:** Create, view, and delete accounts with randomized 4-digit account numbers and specific types like Savings, Salary, or Fixed Deposits.
* **Financial Transactions:** Implements secure logic for deposits, withdrawals with balance verification, and inter-account transfers.
* **User Validation:** Strict user profile creation requiring Romanian ID (CNP) validation and a minimum age requirement of 16 years.
* **Statistical Analysis:** Real-time calculation of average balances and demographic filtering by user occupation or age range.
* **Data Persistence:** Automatically saves and loads the entire bank state to a binary file (`accountsData`), ensuring data is not lost between sessions.

## Core OOP Concepts Demonstrated
This project showcases a clean Java implementation of essential software engineering principles:

* **Abstraction & Encapsulation:** Used private fields with public getters/setters and abstract logic in services to protect sensitive financial data.
* **Exception Handling:** Implemented a hierarchy of custom exceptions (`InsufficientFundsException`, `AccountNotFoundException`, `InvalidInput`) to manage business logic errors gracefully.
* **Composition (HAS-A Relationships):** The `Bank` class manages a dynamic `List` of `Account` objects, which in turn contain `User` details and a history of `Transaction` objects.
* **Serialization:** Utilizes the `Serializable` interface to convert complex object graphs into byte streams for file storage.
* **Enumerations:** Leveraged `enum` types for `AccountType` and `Occupation` to enforce type safety and clear business rules.
* **Interface-Style Services:** Dedicated `Statistics` and `Bank` service classes to separate business logic from the `Menu` user interface.

## Project Architecture
The code is organized into packages to ensure a high degree of modularity and maintainability:
* **`view`**: `Main`, `Menu` — Handles the console interface and user input flow.
* **`service`**: `Bank`, `Statistics` — Contains the core business logic and report generation.
* **`model`**: `Account`, `User`, `Transaction` — Defines the data structures and entities.
* **`enums`**: `AccountType`, `Occupation` — Type-safe constants for the system.
* **`exceptions`**: `CustomExceptions` — Centralized error handling for the application.

---
Developed as a Java programming exercise.