-- Signal fault rule table and seed data
CREATE TABLE IF NOT EXISTS signal_fault_rule (
    id IDENTITY PRIMARY KEY,
    car_series VARCHAR(64) NOT NULL,
    project VARCHAR(64) NOT NULL,
    signal_name VARCHAR(128) NOT NULL,
    operator VARCHAR(8) NOT NULL,
    threshold DOUBLE NOT NULL,
    fault_type VARCHAR(64) NOT NULL,
    fault_code VARCHAR(64) NOT NULL
);

INSERT INTO signal_fault_rule
    (car_series, project, signal_name, operator, threshold, fault_type, fault_code)
VALUES
    ('Series-A', 'Project-X', 'SOC', 'LT', 20, 'Battery', 'P_LOW');

INSERT INTO signal_fault_rule
    (car_series, project, signal_name, operator, threshold, fault_type, fault_code)
VALUES
    ('Series-A', 'Project-X', 'PACK_VOLTAGE', 'LT', 300, 'Battery', 'P_VOLT_LOW');

INSERT INTO signal_fault_rule
    (car_series, project, signal_name, operator, threshold, fault_type, fault_code)
VALUES
    ('Series-B', 'Project-Y', 'BRAKE_PRESSURE', 'LT', 15, 'Brake', 'B_PRESS_LOW');
