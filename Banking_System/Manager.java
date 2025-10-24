package Banking_System;

import java.sql.*;
import java.util.Scanner;

public class Manager {
    private final Connection connection;
    private final Scanner scanner;

    public Manager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Credit money to account
    public void creditMoney(long accountNumber) throws SQLException {
        double amount = getAmount();
        String pin = getPin();

        try {
            connection.setAutoCommit(false);
            if (isValidPin(accountNumber, pin)) {
                PreparedStatement ps = connection.prepareStatement(
                        "UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
                ps.setDouble(1, amount);
                ps.setLong(2, accountNumber);
                ps.executeUpdate();
                connection.commit();
                System.out.println("Rs." + amount + " credited successfully!");
            } else System.out.println("Invalid PIN!");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Debit money from account
    public void debitMoney(long accountNumber) throws SQLException {
        double amount = getAmount();
        String pin = getPin();

        try {
            connection.setAutoCommit(false);
            if (isValidPin(accountNumber, pin)) {
                double currentBalance = getBalance(accountNumber);
                if (amount <= currentBalance) {
                    PreparedStatement ps = connection.prepareStatement(
                            "UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                    ps.setDouble(1, amount);
                    ps.setLong(2, accountNumber);
                    ps.executeUpdate();
                    connection.commit();
                    System.out.println("Rs." + amount + " debited successfully!");
                } else System.out.println("Insufficient Balance!");
            } else System.out.println("Invalid PIN!");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Transfer between accounts
    public void transferMoney(long senderAccount) throws SQLException {
        System.out.print("Receiver Account Number: ");
        long receiverAccount = scanner.nextLong();
        double amount = getAmount();
        String pin = getPin();

        try {
            connection.setAutoCommit(false);
            if (isValidPin(senderAccount, pin)) {
                double balance = getBalance(senderAccount);
                if (amount <= balance) {
                    PreparedStatement debit = connection.prepareStatement(
                            "UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                    PreparedStatement credit = connection.prepareStatement(
                            "UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
                    debit.setDouble(1, amount);
                    debit.setLong(2, senderAccount);
                    credit.setDouble(1, amount);
                    credit.setLong(2, receiverAccount);
                    debit.executeUpdate();
                    credit.executeUpdate();
                    connection.commit();
                    System.out.println("Rs." + amount + " transferred successfully!");
                } else System.out.println("Insufficient Balance!");
            } else System.out.println("Invalid PIN!");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Show current balance
    public void showBalance(long accountNumber) throws SQLException {
        String pin = getPin();
        if (isValidPin(accountNumber, pin)) {
            double balance = getBalance(accountNumber);
            System.out.println("Current Balance: Rs." + balance);
        } else System.out.println("Invalid PIN!");
    }

    // Helper Methods
    private double getAmount() {
        System.out.print("Enter Amount: ");
        return scanner.nextDouble();
    }

    private String getPin() {
        scanner.nextLine();
        System.out.print("Enter Security PIN: ");
        return scanner.nextLine();
    }

    private boolean isValidPin(long accountNumber, String pin) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT 1 FROM accounts WHERE account_number = ? AND security_pin = ?");
        ps.setLong(1, accountNumber);
        ps.setString(2, pin);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private double getBalance(long accountNumber) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT balance FROM accounts WHERE account_number = ?");
        ps.setLong(1, accountNumber);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getDouble(1) : 0.0;
    }
}
