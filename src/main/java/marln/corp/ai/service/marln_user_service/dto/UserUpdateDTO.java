package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import marln.corp.ai.service.marln_user_service.entity.UserType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserUpdateDTO {

    @Email(message = "Email address is not valid")
    private String email;
    @Size(min = 2, message = "Username must be min of 2 character")
    private String userFirstName;
    private String userMiddleName;
    @Size(min = 2, message = "Username must be min of 2 character")
    private String userLastName;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
    private Boolean isActive = true;
    private UserType userType;

    private Boolean isDeleted = false;

    private LocalDateTime lastActive;
    @Size(min = 5, max = 20, message = "Password must be minimum of 5 and a max of 20 character")
    private String passwordHash;
    private String userRole;
    private List<String> userPermissions;
}
