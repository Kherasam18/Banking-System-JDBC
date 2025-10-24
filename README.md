# 🏦 Java Banking System (JDBC)

A console-based **Banking System** built in **Java using JDBC** and **MySQL** that simulates real-world banking operations such as **account creation, deposits, withdrawals, balance checks, and fund transfers** — all with secure data handling and transactional integrity.

---

## Features

✅ **User Registration & Login**  
- Secure user management with unique email validation.  
- Registration and authentication system using JDBC.

✅ **Account Management**  
- Open new bank accounts linked to registered users.  
- Automatically generates sequential account numbers.

✅ **Banking Operations**  
- Deposit (Credit) and Withdraw (Debit) money.  
- Fund Transfer between accounts.  
- Balance Inquiry.  
- 4-digit Security PIN validation for every transaction.

✅ **Database Layer (JDBC)**  
- MySQL database integration using **JDBC API**.  
- Transactions with **commit/rollback** for safety.  
- Exception handling and input validation.  
- Automatic table creation if not present.

---

## Project Architecture

Banking_System/

├── DatabaseUtil.java # Handles database connectivity

├── App.java # Main application and navigation flow

├── User.java # Manages registration and login

├── Accounts.java # Handles account creation and retrieval

├── Manager.java # Manages debit, credit, transfer, and balance

└── banking_system.sql # (Optional) SQL dump for reference


---

## Tech Stack

| Component | Technology |
|------------|------------|
| Language | Java 17+ |
| Database | MySQL 8+ |
| Connectivity | JDBC (Java Database Connectivity) |
| IDE | IntelliJ IDEA / Eclipse / VS Code |
| Build Tool | (Optional) Maven |

---

## Key Learning Concepts

- JDBC API (`DriverManager`, `Connection`, `PreparedStatement`, `ResultSet`)
- SQL CRUD operations
- Transactions (`commit`, `rollback`)
- Exception Handling
- OOP Design (Encapsulation, Separation of Concerns)
- Table Auto-Creation
- Basic Security (PIN Validation, Unique Constraints)

---

## Setup Instructions

### 1️⃣ Clone Repository
```bash
git clone https://github.com/<your-username>/JavaBankingSystem-JDBC.git
cd JavaBankingSystem-JDBC
```

### 2️⃣ Create MySQL Database
```bash
CREATE DATABASE banking_system;
USE banking_system;
```

### 3️⃣ Update Database Credentials
In DatabaseUtil.java, modify:
```bash
private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password";
```

### 4️⃣ Run the Application

Run the App.java file.
Tables will be created automatically on startup.

### Example Flow
```bash
WELCOME TO BANKING SYSTEM
1. Register
2. Login
3. Exit
```

### Register
```bash
Full Name: Sam Khera
Email: sam@gmail.com
Password: *****
```

### Open Account
```bash
Full Name: Sam Khera
Initial Deposit: 5000
Security PIN (4 digits): 1234
Account Created! Your Account Number: 10000100
```

### Debit / Credit / Transfer
```bash
Enter Security PIN: ****
Enter Amount: 1000
Rs.1000 credited successfully!
```

## Future Improvements (Suggestions)

Here’s how you can take this project from good → exceptional:

- Password Hashing (Security Layer)
   - Use MessageDigest or BCrypt for password encryption.
- Modularize with DAO Pattern
    - Separate database logic into a UserDAO, AccountDAO, TransactionDAO.
- Add Transaction History Table
    - Record every debit/credit/transfer with timestamps.
- Integrate Maven
    - Convert it into a Maven project for better dependency management.
- Add Logging
    - Use java.util.logging or Log4j for tracking DB events and errors.
- Unit Testing
    - Add JUnit tests for database operations and edge cases.
- Optional GUI
    - Use JavaFX or Swing for a desktop interface (bonus for presentation).

## 🧑‍💻 Author

Sam Khera

## Contribute

Pull requests are welcome!

If you have new ideas (like audit logging, OTP verification, etc.), feel free to open an issue or PR.
