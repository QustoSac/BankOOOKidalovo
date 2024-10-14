package com.example.bankoookidalovo.bankoookidalovo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/BANK";
    private static final String USER = "danila";
    private static final String PASSWORD = "kappa";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

