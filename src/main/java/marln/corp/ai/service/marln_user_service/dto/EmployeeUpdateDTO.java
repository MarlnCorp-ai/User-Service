package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import marln.corp.ai.service.marln_user_service.entity.EmployeeStatus;
import marln.corp.ai.service.marln_user_service.entity.EmployeeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeUpdateDTO {


    @Size(min = 3, max = 50, message = "Employee ID must be between 3 and 50 characters")
    private String employeeId;

    @Size(max = 36, message = "Reporting manager ID must not exceed 50 characters")
    private String reportingManagerId;

    @Size(max = 200, message = "Reporting manager name must not exceed 200 characters")
    private String reportingManagerName;

    @Size(max = 100, message = "Job city must not exceed 100 characters")
    private String jobCity;

    @Size(min = 2, max = 100, message = "Designation must be between 2 and 100 characters")
    private String designation;

    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

    private EmployeeType employeeType;

    private EmployeeStatus employeeStatus;

    @Size(max = 100, message = "Work location must not exceed 200 characters")
    private String workLocation;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    private String phoneNumber;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Emergency contact must be 10-15 digits")
    private String emergencyContact;

    @Future(message = "Termination date must be in the future")
    private LocalDate terminationDate;

    @Size(max = 100, message = "Created by must not exceed 100 characters")
    private String createdBy;

    @Size(max = 100, message = "Updated by must not exceed 100 characters")
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Include user information when needed - with nested validation
    private UserDTO user;
}
