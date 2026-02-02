package com.example.fault.repository;

import com.example.fault.db.Database;
import com.example.fault.model.RuleOperator;
import com.example.fault.model.SignalFaultRule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignalFaultRuleRepository {
    public List<SignalFaultRule> findBySeriesAndProject(String carSeries, String project) throws SQLException {
        String sql = "SELECT id, car_series, project, signal_name, operator, threshold, " +
            "fault_type, fault_code " +
            "FROM signal_fault_rule WHERE car_series = ? AND project = ?";
        List<SignalFaultRule> results = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, carSeries);
            statement.setString(2, project);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(new SignalFaultRule(
                        resultSet.getLong("id"),
                        resultSet.getString("car_series"),
                        resultSet.getString("project"),
                        resultSet.getString("signal_name"),
                        RuleOperator.fromDb(resultSet.getString("operator")),
                        resultSet.getDouble("threshold"),
                        resultSet.getString("fault_type"),
                        resultSet.getString("fault_code")
                    ));
                }
            }
        }
        return results;
    }
}
