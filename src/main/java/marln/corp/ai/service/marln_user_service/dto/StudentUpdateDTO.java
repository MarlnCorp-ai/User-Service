package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import marln.corp.ai.service.marln_user_service.entity.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentUpdateDTO {

    @Size(min = 3, max = 50, message = "Student roll number must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Student roll number must contain only uppercase letters and numbers")
    private String studentRollNo;

    @Size(min = 3, max = 50, message = "Student ID must be between 3 and 50 characters")
    private String studentId;

    @Size(min = 2, max = 100, message = "Program must be between 2 and 100 characters")
    private String program;

    @Min(value = 1, message = "Year of study must be at least 1")
    @Max(value = 6, message = "Year of study must not exceed 6")
    private Integer yearOfStudy;

    @PastOrPresent(message = "Enrollment date cannot be in the future")
    private LocalDate enrollmentDate;

    @Size(max = 36, message = "Course ID must not exceed 36 characters")
    private String courseId;

    @Size(max = 20, message = "Academic year must not exceed 20 characters")
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "Academic year must be in format YYYY-YYYY")
    private String academicYear;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 10, message = "Semester must not exceed 10")
    private Integer semester;

    private StudentStatus studentStatus;

    @Future(message = "Graduation date must be in the future")
    private LocalDate graduationDate;

    @Size(max = 100, message = "Specialization must not exceed 100 characters")
    private String specialization;

    @Size(max = 36, message = "Mentor ID must not exceed 36 characters")
    private String mentorId;

    @Size(max = 100, message = "Created by must not exceed 100 characters")
    private String createdBy;

    @Size(max = 100, message = "Updated by must not exceed 100 characters")
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Include user information when needed - with nested validation
    private UserUpdateDTO user;
}
