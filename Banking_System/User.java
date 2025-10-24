package Banking_System;

import java.sql.*;
import java.util.Scanner;

public class User {
    private final Connection connection;
    private final Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Register a new user
    public void register() {
        scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (exists(email)) {
            System.out.println("User already exists!");
            return;
        }

        String query = "INSERT INTO user(full_name, email, password) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
            System.out.println("Registration Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // User login
    public String login() {
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? email : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Check if user exists
    public boolean exists(String email) {
        String query = "SELECT email FROM user WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
