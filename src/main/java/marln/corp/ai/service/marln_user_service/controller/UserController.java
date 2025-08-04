package marln.corp.ai.service.marln_user_service.controller;


import jakarta.validation.Valid;
import marln.corp.ai.service.marln_user_service.dto.PasswordChangeDTO;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("/updateuser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") String userId)
    {
        return ResponseEntity.ok(userService.updateUser(userDTO, userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Successfully deleted user with id : "+userId);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String uId)
    {
        return ResponseEntity.ok(userService.getByUserId(uId));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody PasswordChangeDTO passwordChangeDTO)
    {
        return  ResponseEntity.ok(userService.changePassword(passwordChangeDTO));
    }
}
