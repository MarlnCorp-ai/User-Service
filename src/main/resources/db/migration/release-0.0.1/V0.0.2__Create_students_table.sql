-- Create students table
-- Release 0.0.1 - Student entity schema

CREATE TABLE students (
    user_id VARCHAR(36) PRIMARY KEY,
    student_roll_no VARCHAR(50) UNIQUE NOT NULL,
    student_id VARCHAR(50) UNIQUE NOT NULL,
    program VARCHAR(100) NOT NULL,
    year_of_study INTEGER,
    enrollment_date DATE,
    course_id VARCHAR(36) NOT NULL,
    academic_year VARCHAR(20),
    semester INTEGER,
    student_status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (student_status IN ('ACTIVE', 'INACTIVE', 'GRADUATED', 'SUSPENDED', 'TRANSFERRED')),
    department_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT DEFAULT 0 NOT NULL
);

-- Add constraints
ALTER TABLE students ADD CONSTRAINT chk_year_of_study CHECK (year_of_study >= 1 AND year_of_study <= 6);
ALTER TABLE students ADD CONSTRAINT chk_semester CHECK (semester >= 1 AND semester <= 10);
ALTER TABLE students ADD CONSTRAINT chk_academic_year_format CHECK (academic_year ~ '^[0-9]{4}-[0-9]{4}$');

-- Add table comments
COMMENT ON TABLE students IS 'Student-specific information linked to users table via shared primary key';
COMMENT ON COLUMN students.user_id IS 'Primary key and foreign key to users.id (shared PK with @MapsId)';
COMMENT ON COLUMN students.student_roll_no IS 'Unique student roll number for academic identification';
COMMENT ON COLUMN students.student_status IS 'Current status of the student enrollment';
COMMENT ON COLUMN students.version IS 'Version number for optimistic locking to handle concurrent updates';