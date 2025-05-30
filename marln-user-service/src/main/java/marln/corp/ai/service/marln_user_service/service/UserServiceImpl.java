package marln.corp.ai.service.marln_user_service.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import marln.corp.ai.service.marln_user_service.assembler.UserMapper;
import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        User user = userMapper.userDTOToUser(userDTO);
        user.setCreatedBy(request.getHeader("userId"));
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        System.out.println("Inside getAllUserService");
        return userRepository.findAll().stream().map(user-> userMapper.userToUserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new BadRequestException(userId));

        user.setUserFirstName(userDTO.getUserFirstName());
        user.setUserMiddleName(userDTO.getUserMiddleName());
        user.setUserLastName(userDTO.getUserLastName());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setUpdatedAt(LocalDateTime.now());

        return  userMapper.userToUserDTO(userRepository.save(user));
    }
    @Override
    public UserDTO getByUserId(String userId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new BadRequestException(userId));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
