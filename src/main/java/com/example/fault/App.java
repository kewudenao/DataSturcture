package com.example.fault;

import com.example.fault.db.DatabaseInitializer;
import com.example.fault.model.FaultReportInput;
import com.example.fault.model.ProcessResult;
import com.example.fault.repository.CollisionRepairSignalRepository;
import com.example.fault.repository.FaultHandlingRepository;
import com.example.fault.repository.FaultReportRepository;
import com.example.fault.repository.FaultSignalRepository;
import com.example.fault.service.FaultProcessingService;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws Exception {
        DatabaseInitializer.initialize();

        FaultProcessingService service = new FaultProcessingService(
            new FaultReportRepository(),
            new FaultHandlingRepository(),
            new FaultSignalRepository(),
            new CollisionRepairSignalRepository()
        );

        FaultReportInput input = new FaultReportInput(
            "VIN-001",
            "Series-A",
            "Project-X",
            "Battery",
            "P001",
            LocalDateTime.of(2026, 2, 2, 10, 30)
        );

        ProcessResult first = service.process(input);
        System.out.println("First run:");
        System.out.println(first.toPrettyString());

        ProcessResult second = service.process(input);
        System.out.println("Second run:");
        System.out.println(second.toPrettyString());
    }
}
