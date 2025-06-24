package marln.corp.ai.service.marln_user_service.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private String id;

    private String userRollNo;
    @NotEmpty
    private String userRole;

    @Email(message = "Email address is not valid")
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 2, message = "Username must be min of 2 character")
    private String userFirstName;

    private String userMiddleName;

    @NotEmpty
    @Size(min = 2, message = "Username must be min of 2 character")
    private String userLastName;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
    private Boolean isActive = true;

    private Boolean isDeleted = false;

    private LocalDateTime lastActive;


    @NotEmpty
    @Size(min = 5, max = 20, message = "Password must be minimum of 5 and a max of 20 character")
    private String passwordHash;
    @NotEmpty
    private String departmentId;
    @NotEmpty
    private List<String> userPermissions;
}
