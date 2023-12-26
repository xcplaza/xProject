package karmiel.service;

import java.sql.SQLException;

public interface DatabaseAccessorInterface {
    String getBrandFromDatabase() throws SQLException;
    String getModelFromDatabase() throws SQLException;
}

