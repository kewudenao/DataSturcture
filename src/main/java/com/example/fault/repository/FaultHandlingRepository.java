package com.example.fault.repository;

import com.example.fault.db.Database;
import com.example.fault.model.HandlingConclusion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaultHandlingRepository {
    public List<HandlingConclusion> findConclusions(String carSeries, String project, String faultType)
        throws SQLException {
        String sql = "SELECT id, car_series, project, fault_type, conclusion " +
            "FROM fault_handling WHERE car_series = ? AND project = ? AND fault_type = ?";
        List<HandlingConclusion> results = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carSeries);
            statement.setString(2, project);
            statement.setString(3, faultType);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(new HandlingConclusion(
                        resultSet.getLong("id"),
                        resultSet.getString("car_series"),
                        resultSet.getString("project"),
                        resultSet.getString("fault_type"),
                        resultSet.getString("conclusion")));
                }
            }
        }
        return results;
    }
}
