package com.example.fault;

import com.example.fault.db.Database;
import com.example.fault.db.DatabaseInitializer;
import com.example.fault.model.ApiDataInput;
import com.example.fault.model.ApiProcessResult;
import com.example.fault.model.ApiSignalReading;
import com.example.fault.model.ProcessResult;
import com.example.fault.repository.CollisionRepairSignalRepository;
import com.example.fault.repository.FaultHandlingRepository;
import com.example.fault.repository.FaultReportRepository;
import com.example.fault.repository.FaultSignalRepository;
import com.example.fault.repository.SignalFaultRuleRepository;
import com.example.fault.service.ApiDataProcessingService;
import com.example.fault.service.FaultProcessingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiDataProcessingServiceTest {
    private ApiDataProcessingService apiService;

    @BeforeAll
    static void initDatabase() throws Exception {
        DatabaseInitializer.initialize();
    }

    @BeforeEach
    void setUp() throws Exception {
        FaultProcessingService faultService = new FaultProcessingService(
            new FaultReportRepository(),
            new FaultHandlingRepository(),
            new FaultSignalRepository(),
            new CollisionRepairSignalRepository()
        );
        apiService = new ApiDataProcessingService(
            new SignalFaultRuleRepository(),
            faultService
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
    void processMatchesRuleAndMarksFault() throws Exception {
        ApiDataInput input = new ApiDataInput(
            "VIN-API-1",
            "Series-A",
            "Project-X",
            LocalDateTime.of(2026, 2, 2, 12, 30),
            Collections.singletonList(new ApiSignalReading("SOC", 10.0))
        );

        ApiProcessResult result = apiService.process(input);

        assertTrue(result.isFaultDetected());
        assertEquals(1, result.getDetectedFaults().size());
        assertEquals(1, result.getProcessingResults().size());
        assertFalse(result.getProcessingResults().get(0).isDuplicate());
    }

    @Test
    void processNoRuleMatchReturnsNoFault() throws Exception {
        ApiDataInput input = new ApiDataInput(
            "VIN-API-2",
            "Series-A",
            "Project-X",
            LocalDateTime.of(2026, 2, 2, 13, 0),
            Collections.singletonList(new ApiSignalReading("SOC", 80.0))
        );

        ApiProcessResult result = apiService.process(input);

        assertFalse(result.isFaultDetected());
        assertTrue(result.getDetectedFaults().isEmpty());
        assertTrue(result.getProcessingResults().isEmpty());
    }

    @Test
    void processDuplicateMarksDuplicateOnSecondRun() throws Exception {
        ApiDataInput input = new ApiDataInput(
            "VIN-API-3",
            "Series-A",
            "Project-X",
            LocalDateTime.of(2026, 2, 2, 14, 0),
            Collections.singletonList(new ApiSignalReading("PACK_VOLTAGE", 200.0))
        );

        ApiProcessResult first = apiService.process(input);
        ApiProcessResult second = apiService.process(input);

        assertTrue(first.isFaultDetected());
        assertTrue(second.isFaultDetected());

        List<ProcessResult> results = second.getProcessingResults();
        assertEquals(1, results.size());
        assertTrue(results.get(0).isDuplicate());
    }
}
