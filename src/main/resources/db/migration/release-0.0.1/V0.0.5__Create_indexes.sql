-- Create performance indexes
-- Release 0.0.1 - Database optimization indexes

-- Users table indexes (matching your entity @Index annotations)
CREATE INDEX idx_user_email ON users(user_email);
CREATE INDEX idx_user_type_active ON users(user_type, is_active);
CREATE INDEX idx_created_at ON users(user_created_at);
CREATE INDEX idx_user_first_name ON users(user_first_name);

-- Students table indexes (matching your entity @Index annotations)
CREATE INDEX idx_student_roll_no ON students(student_roll_no);
CREATE INDEX idx_student_id ON students(student_id);
CREATE INDEX idx_program_year ON students(program, year_of_study);
CREATE INDEX idx_course_id ON students(course_id);
CREATE INDEX idx_enrollment_date ON students(enrollment_date);
CREATE INDEX idx_student_status ON students(student_status);

-- Employees table indexes (matching your entity @Index annotations)
CREATE INDEX idx_employee_id ON employees(employee_id);
CREATE INDEX idx_designation ON employees(designation);
CREATE INDEX idx_reporting_manager ON employees(reporting_manager_id);
CREATE INDEX idx_hire_date ON employees(hire_date);
CREATE INDEX idx_employee_status ON employees(employee_status);

-- Additional composite indexes for common queries
CREATE INDEX idx_users_active_type_email ON users(is_active, user_type, user_email) WHERE is_deleted = FALSE;
CREATE INDEX idx_students_active_program ON students(student_status, program) WHERE student_status = 'ACTIVE';
CREATE INDEX idx_employees_active_dept ON employees(employee_status, job_city) WHERE employee_status = 'ACTIVE';

-- Add index comments
COMMENT ON INDEX idx_user_email IS 'Unique index for fast user lookup by email';
COMMENT ON INDEX idx_user_type_active IS 'Composite index for filtering users by type and active status';
COMMENT ON INDEX idx_student_roll_no IS 'Unique index for student roll number lookup';
COMMENT ON INDEX idx_employee_id IS 'Unique index for employee ID lookup';mv