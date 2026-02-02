package com.example.fault.service;

import com.example.fault.model.CollisionRepairSignal;
import com.example.fault.model.FaultReportInput;
import com.example.fault.model.FaultSignal;
import com.example.fault.model.HandlingConclusion;
import com.example.fault.model.ProcessResult;
import com.example.fault.repository.CollisionRepairSignalRepository;
import com.example.fault.repository.FaultHandlingRepository;
import com.example.fault.repository.FaultReportRepository;
import com.example.fault.repository.FaultSignalRepository;
import java.sql.SQLException;
import java.util.List;

public class FaultProcessingService {
    private final FaultReportRepository faultReportRepository;
    private final FaultHandlingRepository faultHandlingRepository;
    private final FaultSignalRepository faultSignalRepository;
    private final CollisionRepairSignalRepository collisionRepairSignalRepository;

    public FaultProcessingService(FaultReportRepository faultReportRepository,
                                  FaultHandlingRepository faultHandlingRepository,
                                  FaultSignalRepository faultSignalRepository,
                                  CollisionRepairSignalRepository collisionRepairSignalRepository) {
        this.faultReportRepository = faultReportRepository;
        this.faultHandlingRepository = faultHandlingRepository;
        this.faultSignalRepository = faultSignalRepository;
        this.collisionRepairSignalRepository = collisionRepairSignalRepository;
    }

    public ProcessResult process(FaultReportInput input) throws SQLException {
        if (faultReportRepository.isDuplicate(input)) {
            return ProcessResult.duplicate("Duplicate record detected");
        }

        faultReportRepository.save(input);

        List<HandlingConclusion> conclusions = faultHandlingRepository.findConclusions(
            input.getCarSeries(), input.getProject(), input.getFaultType());
        List<FaultSignal> faultSignals = faultSignalRepository.findSignalsByFaultType(input.getFaultType());
        List<CollisionRepairSignal> collisionSignals = collisionRepairSignalRepository.findAll();

        return ProcessResult.success("Record accepted", conclusions, faultSignals, collisionSignals);
    }
}
