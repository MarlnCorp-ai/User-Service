package marln.corp.ai.service.marln_user_service.assembler;
import jakarta.annotation.PostConstruct;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;
    
    @PostConstruct
    public void initMappings() {
        // DTO → Entity custom mapping
        modelMapper.addMappings(new PropertyMap<UserDTO, User>() {
            @Override
            protected void configure() {
                // Skip fields that shouldn't be set by the client
                skip(destination.getCreatedAt());
                skip(destination.getCreatedBy());
                skip(destination.getUpdatedAt());
                skip(destination.getUpdatedBy());
                skip(destination.getIsDeleted());
                skip(destination.getIsActive());
                skip(destination.getLastActive());
            }
        });

        // Entity → DTO (if you want to hide password in future, you can skip it here)
        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                // By default, everything maps unless explicitly skipped
                // You may skip passwordHash here if you don’t want to expose it
                 skip(destination.getPasswordHash());
            }
        });
    }

    public User userDTOToUser(UserDTO userDTO)
    {

        return modelMapper.map(userDTO,User.class);
    }

    public UserDTO userToUserDTO(User user)
    {
        return modelMapper.map(user,UserDTO.class);
    }
}

