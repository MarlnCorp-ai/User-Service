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
@Data
@NoArgsConstructor
@ToString
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "user_email"),
        @Index(name = "idx_user_type_active", columnList = "user_type,is_active"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_user_first_name", columnList = "user_first_name")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version
    private Long version;

    @Column(name = "user_email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "user_first_name", nullable = false, length = 100)
    private String userFirstName;

    @Column(name = "user_middle_name", length = 100)
    private String userMiddleName;

    @Column(name = "user_last_name", nullable = false, length = 100)
    private String userLastName;

    @Column(name = "user_password",nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 20)
    private UserType userType; // STUDENT, EMPLOYEE, ADMIN

    @Column(name = "user_created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

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

    // Transient fields for role and permissions (not stored in database)
//    @Transient
//    private String userRole;
//
//    @Transient
//    private List<String> userPermissions;


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
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
