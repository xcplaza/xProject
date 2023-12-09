package karmiel.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseAccessor {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cars";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public String getBrandFromDatabase() throws SQLException {
        return getDataFromDatabase("SELECT name FROM brands ORDER BY RAND() LIMIT 1", "name");
    }

    public String getModelFromDatabase() throws SQLException {
        return getDataFromDatabase("SELECT name FROM models ORDER BY RAND() LIMIT 1", "name");
    }

    private String getDataFromDatabase(String query, String columnName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(columnName);
                    }
                }
            }
        }
        return null;
    }
}
