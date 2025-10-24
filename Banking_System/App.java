package Banking_System;

import java.sql.*;
import java.util.Scanner;

public class App {
    // Create Accounts table if not exists
    private static final String ACCOUNTS_TABLE = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number INT PRIMARY KEY NOT NULL,
                full_name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                balance DECIMAL(10,2) NOT NULL,
                security_pin CHAR(4) NOT NULL
            );
            """;

    // Create User table if not exists
    private static final String USER_TABLE = """
            CREATE TABLE IF NOT EXISTS user (
                full_name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL PRIMARY KEY,
                password VARCHAR(255) NOT NULL
            );
            """;

    public static void main(String[] args) {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             Scanner sc = new Scanner(System.in)) {

            // Initialize tables
            stmt.executeUpdate(ACCOUNTS_TABLE);
            stmt.executeUpdate(USER_TABLE);

            User user = new User(conn, sc);
            Accounts accounts = new Accounts(conn, sc);
            Manager manager = new Manager(conn, sc);

            while (true) {
                System.out.println("WELCOME TO BANKING SYSTEM");
                System.out.println("1️. Register");
                System.out.println("2️. Login");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> user.register();
                    case 2 -> handleLoginFlow(user, accounts, manager, sc);
                    case 3 -> {
                        System.out.println("\n👋 Thank you for using the Banking System!");
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid choice! Try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Handles user login and account actions
    private static void handleLoginFlow(User user, Accounts accounts, Manager manager, Scanner sc) throws SQLException {
        String email = user.login();
        if (email == null) {
            System.out.println("Incorrect Email or Password!");
            return;
        }

        System.out.println("Logged in successfully!");

        long accountNumber;
        if (!accounts.exists(email)) {
            System.out.println("1. Open a New Account");
            System.out.println("2️. Exit");
            if (sc.nextInt() == 1) {
                accountNumber = accounts.openAccount(email);
                System.out.println("Account Created! Your Account Number: " + accountNumber);
            } else return;
        }

        accountNumber = accounts.getAccountNumber(email);
        showAccountMenu(manager, accountNumber, sc);
    }

    // Sub-menu for account operations
    private static void showAccountMenu(Manager manager, long accountNumber, Scanner sc) throws SQLException {
        int option;
        do {
            System.out.println("Account Menu:");
            System.out.println("1️. Debit Money");
            System.out.println("2️. Credit Money");
            System.out.println("3️. Transfer Money");
            System.out.println("4️. Check Balance");
            System.out.println("5️. Log Out");
            System.out.print("Enter choice: ");
            option = sc.nextInt();

            switch (option) {
                case 1 -> manager.debitMoney(accountNumber);
                case 2 -> manager.creditMoney(accountNumber);
                case 3 -> manager.transferMoney(accountNumber);
                case 4 -> manager.showBalance(accountNumber);
                case 5 -> System.out.println("Logged Out Successfully.");
                default -> System.out.println("Invalid Option!");
            }
        } while (option != 5);
    }
}
