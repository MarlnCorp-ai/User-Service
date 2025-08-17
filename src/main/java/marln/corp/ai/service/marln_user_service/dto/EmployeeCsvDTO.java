package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCsvDTO {

    // User fields
    private String email;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String password;
    private String createdBy;
        private String userRole;
    private List<String> userPermissions;
    // Employee fields
    private String employeeId;
    private String designation;
    private LocalDate hireDate;
    private String reportingManagerId;
    private String reportingManagerName;
    private String jobCity;
    private String workLocation;
    private String phoneNumber;
    private String emergencyContact;


}