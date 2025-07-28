package marln.corp.ai.service.marln_user_service.security;


import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepo.findByEmail(username).orElseThrow(() -> new InvalidCredentialsException("Email not found: " + username));
        return user;
    }
}
