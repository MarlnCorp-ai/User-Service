package marln.corp.ai.service.marln_user_service.mapper;

import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    public User userDTOToUser(UserDTO userDTO)
    {

        return modelMapper.map(userDTO,User.class);
    }

    public UserDTO userToUserDTO(User user)
    {
        return modelMapper.map(user,UserDTO.class);
    }
}
