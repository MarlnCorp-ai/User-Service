-- Create employees table
-- Release 0.0.1 - Employee entity schema

CREATE TABLE employees (
    user_id VARCHAR(36) PRIMARY KEY,
    employee_id VARCHAR(50) UNIQUE NOT NULL,
    reporting_manager_id VARCHAR(36),
    reporting_manager_name VARCHAR(200),
    job_city VARCHAR(100),
    designation VARCHAR(100) NOT NULL,
    hire_date DATE,
    salary DECIMAL(12,2),
    employee_type VARCHAR(20) DEFAULT 'FULL_TIME' CHECK (employee_type IN ('FULL_TIME', 'PART_TIME', 'CONTRACT', 'INTERN', 'CONSULTANT')),
    employee_status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (employee_status IN ('ACTIVE', 'INACTIVE', 'TERMINATED', 'ON_LEAVE', 'SUSPENDED')),
    work_location VARCHAR(100),
    phone_number VARCHAR(20),
    emergency_contact VARCHAR(20),
    termination_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT DEFAULT 0 NOT NULL
);

-- Add constraints
ALTER TABLE employees ADD CONSTRAINT chk_salary_positive CHECK (salary >= 0);
ALTER TABLE employees ADD CONSTRAINT chk_phone_format CHECK (phone_number ~ '^[+]?[0-9]{10,15}$');
ALTER TABLE employees ADD CONSTRAINT chk_emergency_contact_format CHECK (emergency_contact ~ '^[+]?[0-9]{10,15}$');

-- Add table comments
COMMENT ON TABLE employees IS 'Employee-specific information linked to users table via shared primary key';
COMMENT ON COLUMN employees.user_id IS 'Primary key and foreign key to users.id (shared PK with @MapsId)';
COMMENT ON COLUMN employees.salary IS 'Employee salary with 2 decimal precision';
COMMENT ON COLUMN employees.employee_type IS 'Employment type: full-time, part-time, contract, intern, consultant';
COMMENT ON COLUMN employees.version IS 'Version number for optimistic locking to handle concurrent updates';