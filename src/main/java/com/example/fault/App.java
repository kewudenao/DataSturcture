package com.example.fault;

import com.example.fault.db.DatabaseInitializer;
import com.example.fault.model.ApiDataInput;
import com.example.fault.model.ApiProcessResult;
import com.example.fault.model.ApiSignalReading;
import com.example.fault.repository.CollisionRepairSignalRepository;
import com.example.fault.repository.FaultHandlingRepository;
import com.example.fault.repository.FaultReportRepository;
import com.example.fault.repository.FaultSignalRepository;
import com.example.fault.repository.SignalFaultRuleRepository;
import com.example.fault.service.ApiDataProcessingService;
import com.example.fault.service.FaultProcessingService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        DatabaseInitializer.initialize();

        FaultProcessingService faultService = new FaultProcessingService(
            new FaultReportRepository(),
            new FaultHandlingRepository(),
            new FaultSignalRepository(),
            new CollisionRepairSignalRepository()
        );

        ApiDataProcessingService apiService = new ApiDataProcessingService(
            new SignalFaultRuleRepository(),
            faultService
        );

        List<ApiSignalReading> signals = Arrays.asList(
            new ApiSignalReading("SOC", 15.5),
            new ApiSignalReading("PACK_VOLTAGE", 280.0),
            new ApiSignalReading("BRAKE_PRESSURE", 25.0)
        );
        ApiDataInput apiInput = new ApiDataInput(
            "VIN-001",
            "Series-A",
            "Project-X",
            LocalDateTime.of(2026, 2, 2, 10, 30),
            signals
        );

        ApiProcessResult first = apiService.process(apiInput);
        System.out.println("API first run:");
        System.out.println(first.toPrettyString());

        ApiProcessResult second = apiService.process(apiInput);
        System.out.println("API second run:");
        System.out.println(second.toPrettyString());
    }
}
