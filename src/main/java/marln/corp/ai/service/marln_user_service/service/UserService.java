package marln.corp.ai.service.marln_user_service.service;

import marln.corp.ai.service.marln_user_service.dto.PasswordChangeDTO;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UserDTO user, String userId);
    UserDTO getByUserId(String userId);
    void deleteUser(String userId);


    String changePassword(PasswordChangeDTO passwordChangeDTO);
}
