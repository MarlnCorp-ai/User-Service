package marln.corp.ai.service.marln_user_service.controller;

import jakarta.validation.Valid;
import marln.corp.ai.service.marln_user_service.dto.LoginRequestDTO;
import marln.corp.ai.service.marln_user_service.dto.LoginResponseDTO;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.security.JwtTokenHelper;
import marln.corp.ai.service.marln_user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> createToken(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        System.out.println("Inside login");
        authenticate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        UserDetails userDetails =  userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
        System.out.println("userEmail : " + userDetails.getUsername());
        System.out.println("userPassword : " + userDetails.getPassword());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        // Cast UserDetails to User to get the ID
        User user = (User) userDetails;
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setId(user.getId());
        loginResponseDTO.setToken(token);
        return new ResponseEntity<LoginResponseDTO>(loginResponseDTO, HttpStatus.OK
        );
    }

    private void authenticate(String email, String password) {

        System.out.println("inside authenticate");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

}
