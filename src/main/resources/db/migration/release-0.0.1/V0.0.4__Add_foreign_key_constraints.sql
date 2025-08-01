-- Add foreign key constraints
-- Release 0.0.1 - Establish relationships between tables

-- Add foreign key constraint for students table
ALTER TABLE students
ADD CONSTRAINT fk_student_user
FOREIGN KEY (user_id) REFERENCES users(id)
ON DELETE CASCADE;

-- Add foreign key constraint for employees table
ALTER TABLE employees
ADD CONSTRAINT fk_employee_user
FOREIGN KEY (user_id) REFERENCES users(id)
ON DELETE CASCADE;

-- Add foreign key for employee reporting manager (self-referencing)
ALTER TABLE employees
ADD CONSTRAINT fk_employee_manager
FOREIGN KEY (reporting_manager_id) REFERENCES users(id)
ON DELETE SET NULL;

-- Add comments
COMMENT ON CONSTRAINT fk_student_user ON students IS 'Ensures student records are linked to valid users with cascade delete';
COMMENT ON CONSTRAINT fk_employee_user ON employees IS 'Ensures employee records are linked to valid users with cascade delete';
COMMENT ON CONSTRAINT fk_employee_manager ON employees IS 'Self-referencing constraint for reporting manager hierarchy';