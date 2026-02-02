package com.example.fault.repository;

import com.example.fault.db.Database;
import com.example.fault.model.FaultSignal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaultSignalRepository {
    public List<FaultSignal> findSignalsByFaultType(String faultType) throws SQLException {
        String sql = "SELECT id, fault_type, signal_name FROM fault_signal WHERE fault_type = ?";
        List<FaultSignal> results = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, faultType);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(new FaultSignal(
                        resultSet.getLong("id"),
                        resultSet.getString("fault_type"),
                        resultSet.getString("signal_name")));
                }
            }
        }
        return results;
    }
}
