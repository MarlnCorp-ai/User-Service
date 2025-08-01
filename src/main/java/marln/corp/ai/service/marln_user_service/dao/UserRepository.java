package marln.corp.ai.service.marln_user_service.dao;

import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String userEmail);
    List<User> findByUserType(UserType userType);
    boolean existsByEmail(String email);

    Optional<User> findByIdAndIsDeletedFalse(String id);
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.isActive = true")
    List<User> findAllActiveUsers();
}
