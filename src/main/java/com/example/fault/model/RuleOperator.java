package com.example.fault.model;

public enum RuleOperator {
    GT,
    GTE,
    LT,
    LTE,
    EQ,
    NE;

    public boolean matches(double value, double threshold) {
        switch (this) {
            case GT:
                return value > threshold;
            case GTE:
                return value >= threshold;
            case LT:
                return value < threshold;
            case LTE:
                return value <= threshold;
            case EQ:
                return Double.compare(value, threshold) == 0;
            case NE:
                return Double.compare(value, threshold) != 0;
            default:
                return false;
        }
    }

    public static RuleOperator fromDb(String operator) {
        return RuleOperator.valueOf(operator);
    }
}
