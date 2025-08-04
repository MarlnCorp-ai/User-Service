package marln.corp.ai.service.marln_user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeDTO {
    private String emailId;
    private String existingPassword;
    private String newPassword;
}
