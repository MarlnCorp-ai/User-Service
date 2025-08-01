-- Create users table for marln user service
-- Release 0.0.1 - Initial schema creation

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    user_email VARCHAR(255) UNIQUE NOT NULL,
    user_first_name VARCHAR(100) NOT NULL,
    user_middle_name VARCHAR(100),
    user_last_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL CHECK (user_type IN ('STUDENT', 'EMPLOYEE', 'ADMIN')),
    user_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    last_active TIMESTAMP
);

-- Add table comments
COMMENT ON TABLE users IS 'Base user table containing common user information for marln user service';
COMMENT ON COLUMN users.user_type IS 'Type of user: STUDENT, EMPLOYEE, or ADMIN';
COMMENT ON COLUMN users.is_deleted IS 'Soft delete flag for maintaining data integrity';
COMMENT ON COLUMN users.user_created_at IS 'Timestamp when user record was created';