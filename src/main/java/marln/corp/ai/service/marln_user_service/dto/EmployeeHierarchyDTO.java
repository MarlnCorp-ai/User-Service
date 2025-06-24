package marln.corp.ai.service.marln_user_service.dto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeHierarchyDTO {
    private String id;
    private String employeeName;
    private String designation;
    private String jobCity;
    private String reportingManagerId;
    private String reportingManagerName;

    private EmployeeHierarchyDTO manager; // upward chain
    private List<EmployeeHierarchyDTO> directReports; // downward chain
}