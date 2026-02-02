package com.example.fault;

import com.example.fault.db.Database;
import com.example.fault.db.DatabaseInitializer;
import com.example.fault.model.FaultReportInput;
import com.example.fault.model.FaultSignal;
import com.example.fault.model.HandlingConclusion;
import com.example.fault.model.ProcessResult;
import com.example.fault.repository.CollisionRepairSignalRepository;
import com.example.fault.repository.FaultHandlingRepository;
import com.example.fault.repository.FaultReportRepository;
import com.example.fault.repository.FaultSignalRepository;
import com.example.fault.service.FaultProcessingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FaultProcessingServiceTest {
    private FaultProcessingService service;

    @BeforeAll
    static void initDatabase() throws Exception {
        DatabaseInitializer.initialize();
    }

    @BeforeEach
    void setUp() throws Exception {
        service = new FaultProcessingService(
            new FaultReportRepository(),
            new FaultHandlingRepository(),
            new FaultSignalRepository(),
            new CollisionRepairSignalRepository()
        );
        clearFaultReports();
    }

    private void clearFaultReports() throws Exception {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM fault_report");
        }
    }

    @Test
    void processNonDuplicateReturnsExpectedData() throws Exception {
        FaultReportInput input = new FaultReportInput(
            "VIN-UNIT-1",
            "Series-A",
            "Project-X",
            "Battery",
            "P100",
            LocalDateTime.of(2026, 2, 2, 10, 30)
        );

        ProcessResult result = service.process(input);

        assertFalse(result.isDuplicate());
        assertEquals(2, result.getHandlingConclusions().size());
        assertEquals(2, result.getFaultSignals().size());
        assertEquals(4, result.getCollisionRepairSignals().size());

        List<String> conclusions = result.getHandlingConclusions().stream()
            .map(HandlingConclusion::getConclusion)
            .collect(Collectors.toList());
        assertTrue(conclusions.contains("Check battery pack"));
        assertTrue(conclusions.contains("Inspect BMS calibration"));

        List<String> signals = result.getFaultSignals().stream()
            .map(FaultSignal::getSignalName)
            .collect(Collectors.toList());
        assertTrue(signals.contains("SOC"));
        assertTrue(signals.contains("PACK_VOLTAGE"));
    }

    @Test
    void processDuplicateReturnsDuplicateOnSecondCall() throws Exception {
        FaultReportInput input = new FaultReportInput(
            "VIN-UNIT-2",
            "Series-A",
            "Project-X",
            "Battery",
            "P200",
            LocalDateTime.of(2026, 2, 2, 11, 0)
        );

        ProcessResult first = service.process(input);
        ProcessResult second = service.process(input);

        assertFalse(first.isDuplicate());
        assertTrue(second.isDuplicate());
        assertTrue(second.getHandlingConclusions().isEmpty());
        assertTrue(second.getFaultSignals().isEmpty());
        assertTrue(second.getCollisionRepairSignals().isEmpty());
    }

    @Test
    void processSameVinDifferentTimeNotDuplicate() throws Exception {
        FaultReportInput firstInput = new FaultReportInput(
            "VIN-UNIT-3",
            "Series-A",
            "Project-X",
            "Battery",
            "P300",
            LocalDateTime.of(2026, 2, 2, 12, 0)
        );
        FaultReportInput secondInput = new FaultReportInput(
            "VIN-UNIT-3",
            "Series-A",
            "Project-X",
            "Battery",
            "P300",
            LocalDateTime.of(2026, 2, 2, 12, 1)
        );

        ProcessResult first = service.process(firstInput);
        ProcessResult second = service.process(secondInput);

        assertFalse(first.isDuplicate());
        assertFalse(second.isDuplicate());
    }
}
