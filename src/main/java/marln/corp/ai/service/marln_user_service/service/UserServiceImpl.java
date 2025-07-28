package marln.corp.ai.service.marln_user_service.service;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import marln.corp.ai.service.marln_user_service.assembler.UserMapper;
import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.exception.UserNotFoundException;
import marln.corp.ai.service.marln_user_service.exception.UserAlreadyExistsException;
import marln.corp.ai.service.marln_user_service.exception.ExternalServiceException;
import marln.corp.ai.service.marln_user_service.restcall.RestCall;
import marln.corp.ai.service.marln_user_service.utils.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    RestCall restCall;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        System.out.println("UserDTO : " +userDTO);
        
        // Check if user already exists with the same email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }
        
        userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        User user = userMapper.userDTOToUser(userDTO);
        user.setCreatedBy(request.getHeader("userId"));
        user.setUpdatedBy(request.getHeader("userId"));
        user.setUserRole(userDTO.getUserRole());
        user.setUserPermissions(userDTO.getUserPermissions());
        System.out.println("UserEntity before saving : " +user);
        UserDTO savedUser = userMapper.userToUserDTO(userRepository.save(user));
        System.out.println("UserEntity after saving : " + savedUser);
        
        //Assign roles to user
        try {
            restCall.assignRoles(savedUser.getId(), userDTO.getUserRole(), userDTO.getUserPermissions());
        } catch (Exception ex) {
            throw new ExternalServiceException("marln-rbac-service", "Failed to assign roles to user: " + ex.getMessage(), ex);
        }

        return savedUser;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        System.out.println("Inside getAllUserService");
        return userRepository.findAll().stream().map(user-> userMapper.userToUserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

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

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public void deleteUser(String userId) {
        // Check if user exists before deleting
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }
}
