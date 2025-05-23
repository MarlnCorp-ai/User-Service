package marln.corp.ai.service.marln_user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequestDTO {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 5, max = 200, message = "Password must be minimum of 5 and a max of 20 character")
    private String password;

}
