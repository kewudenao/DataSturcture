package com.example.fault.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {
    private DatabaseInitializer() {
    }

    public static void initialize() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS fault_report (" +
                "id IDENTITY PRIMARY KEY," +
                "vin VARCHAR(32) NOT NULL," +
                "car_series VARCHAR(64) NOT NULL," +
                "project VARCHAR(64) NOT NULL," +
                "fault_type VARCHAR(64) NOT NULL," +
                "fault_code VARCHAR(64) NOT NULL," +
                "event_time TIMESTAMP NOT NULL" +
                ")");

            statement.execute("CREATE TABLE IF NOT EXISTS fault_handling (" +
                "id IDENTITY PRIMARY KEY," +
                "car_series VARCHAR(64) NOT NULL," +
                "project VARCHAR(64) NOT NULL," +
                "fault_type VARCHAR(64) NOT NULL," +
                "conclusion VARCHAR(255) NOT NULL" +
                ")");

            statement.execute("CREATE TABLE IF NOT EXISTS fault_signal (" +
                "id IDENTITY PRIMARY KEY," +
                "fault_type VARCHAR(64) NOT NULL," +
                "signal_name VARCHAR(128) NOT NULL" +
                ")");

            statement.execute("CREATE TABLE IF NOT EXISTS collision_repair_signal (" +
                "id IDENTITY PRIMARY KEY," +
                "signal_type VARCHAR(32) NOT NULL," +
                "signal_name VARCHAR(128) NOT NULL" +
                ")");
        }

        seedHandlingData();
        seedFaultSignals();
        seedCollisionRepairSignals();
        seedSignalFaultRulesFromScript();
    }

    private static void seedHandlingData() throws SQLException {
        if (!isTableEmpty("fault_handling")) {
            return;
        }

        String sql = "INSERT INTO fault_handling " +
            "(car_series, project, fault_type, conclusion) VALUES (?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            insertHandling(statement, "Series-A", "Project-X", "Battery", "Check battery pack");
            insertHandling(statement, "Series-A", "Project-X", "Battery", "Inspect BMS calibration");
            insertHandling(statement, "Series-B", "Project-Y", "Brake", "Replace brake pads");
        }
    }

    private static void insertHandling(PreparedStatement statement, String carSeries, String project,
                                       String faultType, String conclusion) throws SQLException {
        statement.setString(1, carSeries);
        statement.setString(2, project);
        statement.setString(3, faultType);
        statement.setString(4, conclusion);
        statement.executeUpdate();
    }

    private static void seedFaultSignals() throws SQLException {
        if (!isTableEmpty("fault_signal")) {
            return;
        }

        String sql = "INSERT INTO fault_signal (fault_type, signal_name) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            insertFaultSignal(statement, "Battery", "SOC");
            insertFaultSignal(statement, "Battery", "PACK_VOLTAGE");
            insertFaultSignal(statement, "Brake", "BRAKE_PRESSURE");
        }
    }

    private static void insertFaultSignal(PreparedStatement statement, String faultType, String signalName)
        throws SQLException {
        statement.setString(1, faultType);
        statement.setString(2, signalName);
        statement.executeUpdate();
    }

    private static void seedCollisionRepairSignals() throws SQLException {
        if (!isTableEmpty("collision_repair_signal")) {
            return;
        }

        String sql = "INSERT INTO collision_repair_signal (signal_type, signal_name) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            insertCollisionSignal(statement, "collision", "AIRBAG_TRIGGERED");
            insertCollisionSignal(statement, "collision", "CRASH_SENSOR");
            insertCollisionSignal(statement, "repair", "SERVICE_MODE");
            insertCollisionSignal(statement, "repair", "REPAIR_CONFIRMATION");
        }
    }

    private static void insertCollisionSignal(PreparedStatement statement, String type, String name)
        throws SQLException {
        statement.setString(1, type);
        statement.setString(2, name);
        statement.executeUpdate();
    }

    private static void seedSignalFaultRulesFromScript() throws SQLException {
        if (!shouldInitializeSignalFaultRules()) {
            return;
        }
        runSqlScript("db/init_signal_fault_rule.sql");
    }

    private static boolean isTableEmpty(String tableName) throws SQLException {
        String sql = "SELECT COUNT(1) FROM " + tableName;
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        }
        return true;
    }

    private static boolean shouldInitializeSignalFaultRules() throws SQLException {
        try {
            return isTableEmpty("signal_fault_rule");
        } catch (SQLException ex) {
            return true;
        }
    }

    private static void runSqlScript(String resourcePath) throws SQLException {
        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader()
            .getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("SQL script not found: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                        continue;
                    }
                    builder.append(line).append('\n');
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read SQL script: " + resourcePath, ex);
        }

        String[] statements = builder.toString().split(";");
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : statements) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        }
    }
}
