package marln.corp.ai.service.marln_user_service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {


    private String user_id;

    private String email;

    private String userName;

    private String passwordHash;

    private LocalDateTime createdAt = LocalDateTime.now();
}
