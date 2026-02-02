package com.example.fault.service;

import com.example.fault.model.ApiDataInput;
import com.example.fault.model.ApiProcessResult;
import com.example.fault.model.ApiSignalReading;
import com.example.fault.model.DetectedFault;
import com.example.fault.model.FaultReportInput;
import com.example.fault.model.ProcessResult;
import com.example.fault.model.SignalFaultRule;
import com.example.fault.repository.SignalFaultRuleRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiDataProcessingService {
    private final SignalFaultRuleRepository ruleRepository;
    private final FaultProcessingService faultProcessingService;

    public ApiDataProcessingService(SignalFaultRuleRepository ruleRepository,
                                    FaultProcessingService faultProcessingService) {
        this.ruleRepository = ruleRepository;
        this.faultProcessingService = faultProcessingService;
    }

    public ApiProcessResult process(ApiDataInput input) throws SQLException {
        if (input.getSignals() == null || input.getSignals().isEmpty()) {
            return ApiProcessResult.noFault("No signal data");
        }

        List<SignalFaultRule> rules = ruleRepository.findBySeriesAndProject(
            input.getCarSeries(), input.getProject());
        if (rules.isEmpty()) {
            return ApiProcessResult.noFault("No rules configured");
        }

        Map<String, List<SignalFaultRule>> rulesBySignal = groupRulesBySignal(rules);
        List<DetectedFault> detectedFaults = new ArrayList<>();
        Set<String> dedupKeys = new HashSet<>();

        for (ApiSignalReading reading : input.getSignals()) {
            List<SignalFaultRule> signalRules = rulesBySignal.get(reading.getSignalName());
            if (signalRules == null) {
                continue;
            }
            for (SignalFaultRule rule : signalRules) {
                if (rule.getOperator().matches(reading.getValue(), rule.getThreshold())) {
                    String dedupKey = rule.getFaultType() + "|" + rule.getFaultCode();
                    if (dedupKeys.add(dedupKey)) {
                        detectedFaults.add(new DetectedFault(
                            rule.getId(),
                            reading.getSignalName(),
                            reading.getValue(),
                            rule.getOperator(),
                            rule.getThreshold(),
                            rule.getFaultType(),
                            rule.getFaultCode()
                        ));
                    }
                }
            }
        }

        if (detectedFaults.isEmpty()) {
            return ApiProcessResult.noFault("No faults triggered");
        }

        List<ProcessResult> processingResults = new ArrayList<>();
        for (DetectedFault fault : detectedFaults) {
            FaultReportInput report = new FaultReportInput(
                input.getVin(),
                input.getCarSeries(),
                input.getProject(),
                fault.getFaultType(),
                fault.getFaultCode(),
                input.getEventTime()
            );
            processingResults.add(faultProcessingService.process(report));
        }

        return ApiProcessResult.withFault(
            "Faults detected: " + detectedFaults.size(),
            detectedFaults,
            processingResults
        );
    }

    private Map<String, List<SignalFaultRule>> groupRulesBySignal(List<SignalFaultRule> rules) {
        Map<String, List<SignalFaultRule>> grouped = new HashMap<>();
        for (SignalFaultRule rule : rules) {
            grouped.computeIfAbsent(rule.getSignalName(), key -> new ArrayList<>()).add(rule);
        }
        return grouped;
    }
}
