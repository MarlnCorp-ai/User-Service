package marln.corp.ai.service.marln_user_service.dto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRoleRequestDto {

    private String userId;

    private String roleId;

    private List<String> permissionIds;

    private String tenantId;
}
