package com.example.fault.repository;

import com.example.fault.db.Database;
import com.example.fault.model.CollisionRepairSignal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollisionRepairSignalRepository {
    public List<CollisionRepairSignal> findAll() throws SQLException {
        String sql = "SELECT id, signal_type, signal_name FROM collision_repair_signal";
        List<CollisionRepairSignal> results = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                results.add(new CollisionRepairSignal(
                    resultSet.getLong("id"),
                    resultSet.getString("signal_type"),
                    resultSet.getString("signal_name")));
            }
        }
        return results;
    }
}
