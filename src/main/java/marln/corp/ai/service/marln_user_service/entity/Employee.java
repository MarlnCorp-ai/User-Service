package marln.corp.ai.service.marln_user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees", indexes = {
        @Index(name = "idx_employee_id", columnList = "employee_id"),
        @Index(name = "idx_designation", columnList = "designation"),
        @Index(name = "idx_reporting_manager", columnList = "reporting_manager_id"),
        @Index(name = "idx_hire_date", columnList = "hire_date"),
        @Index(name = "idx_employee_status", columnList = "employee_status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
public class Employee {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Version
    private Long version;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "employee_id", unique = true, nullable = false, length = 50)
    private String employeeId;

    @Column(name = "reporting_manager_id", length = 36)
    private String reportingManagerId;

    @Column(name = "reporting_manager_name", length = 200)
    private String reportingManagerName;

    @Column(name = "job_city", length = 100)
    private String jobCity;

    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", length = 20)
    private EmployeeType employeeType = EmployeeType.FULL_TIME;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", length = 20)
    private EmployeeStatus employeeStatus = EmployeeStatus.ACTIVE;

    @Column(name = "work_location", length = 100)
    private String workLocation;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "emergency_contact", length = 20)
    private String emergencyContact;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    // Utility methods
    public boolean isActive() {
        return employeeStatus == EmployeeStatus.ACTIVE;
    }

    public boolean isTerminated() {
        return employeeStatus == EmployeeStatus.TERMINATED;
    }

    public String getFullEmployeeInfo() {
        return employeeId + " - " + designation;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (employeeType == null) {
            employeeType = EmployeeType.FULL_TIME;
        }
        if (employeeStatus == null) {
            employeeStatus = EmployeeStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}