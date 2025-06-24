package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    @NotEmpty
    private String id;
    @NotEmpty
    private String reportingManagerId;
    @NotEmpty
    private String employeeName;
    @NotEmpty
    private String reportingManagerName;
    @NotEmpty
    private String jobCity;
    @NotEmpty
    private String designation;
}
