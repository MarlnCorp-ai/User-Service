package marln.corp.ai.service.marln_user_service.service;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import marln.corp.ai.service.marln_user_service.assembler.UserMapper;
import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
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
        userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        User user = userMapper.userDTOToUser(userDTO);
        user.setCreatedBy(request.getHeader("userId"));
        user.setUpdatedBy(request.getHeader("userId"));
        System.out.println("UserEntity before saving : " +user);
        UserDTO savedUser = userMapper.userToUserDTO(userRepository.save(user));
        System.out.println("UserEntity after saving : " + savedUser);
        //Assign roles to user
        //Exception handling needs to be added here
         restCall.assignRoles(savedUser.getId(), userDTO.getUserRole(), userDTO.getUserPermissions());

        return savedUser;
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
