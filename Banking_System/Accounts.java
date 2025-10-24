package Banking_System;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private final Connection connection;
    private final Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Open new bank account
    public long openAccount(String email) {
        if (exists(email)) throw new RuntimeException("Account already exists!");

        scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Initial Deposit: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Security PIN (4 digits): ");
        String pin = scanner.nextLine();

        String query = "INSERT INTO accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            long accountNumber = generateAccountNumber();
            ps.setLong(1, accountNumber);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setDouble(4, balance);
            ps.setString(5, pin);
            ps.executeUpdate();
            return accountNumber;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Generate sequential account numbers
    private long generateAccountNumber() throws SQLException {
        String query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getLong(1) + 1 : 10000100;
        }
    }

    public boolean exists(String email) {
        String query = "SELECT 1 FROM accounts WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getAccountNumber(String email) {
        String query = "SELECT account_number FROM accounts WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Account not found!");
    }
}
