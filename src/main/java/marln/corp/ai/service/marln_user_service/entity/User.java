package marln.corp.ai.service.marln_user_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_roll_no", unique = true, nullable = false)
    private String userRollNo;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_first_name", nullable = false)
    private String userFirstName;

    @Column(name = "user_middle_name")
    private String userMiddleName;

    @Column(name = "user_last_name", nullable = false)
    private String userLastName;

    @Column(name = "user_password",nullable = false)
    private String passwordHash;

    @Column(name = "user_created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "department_id", nullable = false)
    private String departmentId;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "last_active")
    private LocalDateTime lastActive;

    @Column(name = "course_id", nullable = false)
    private String courseId;

    // Transient fields for role and permissions (not stored in database)
    @Transient
    private String userRole;

    @Transient
    private List<String> userPermissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }


    @Override
    public String getUsername() {
        return this.email;
    }
}
