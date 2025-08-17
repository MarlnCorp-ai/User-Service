package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCsvDTO {

    // User fields
    private String email;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String password;
    private String createdBy;
    private String userRole;
    private List<String> userPermissions;

    // Student fields
    private String studentRollNo;
    private String studentId;
    private String program;
    private Integer yearOfStudy;
    private LocalDate enrollmentDate;
    private String courseId;
    private String academicYear;
    private Integer semester;
    private String departmentId;

}