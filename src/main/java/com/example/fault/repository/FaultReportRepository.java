package com.example.fault.repository;

import com.example.fault.db.Database;
import com.example.fault.model.FaultReportInput;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FaultReportRepository {
    public boolean isDuplicate(FaultReportInput input) throws SQLException {
        String sql = "SELECT COUNT(1) FROM fault_report " +
            "WHERE vin = ? AND fault_code = ? AND event_time = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, input.getVin());
            statement.setString(2, input.getFaultCode());
            statement.setTimestamp(3, Timestamp.valueOf(input.getEventTime()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void save(FaultReportInput input) throws SQLException {
        String sql = "INSERT INTO fault_report " +
            "(vin, car_series, project, fault_type, fault_code, event_time) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, input.getVin());
            statement.setString(2, input.getCarSeries());
            statement.setString(3, input.getProject());
            statement.setString(4, input.getFaultType());
            statement.setString(5, input.getFaultCode());
            statement.setTimestamp(6, Timestamp.valueOf(input.getEventTime()));
            statement.executeUpdate();
        }
    }
}
