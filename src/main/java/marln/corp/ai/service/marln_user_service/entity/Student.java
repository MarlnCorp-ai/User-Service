package marln.corp.ai.service.marln_user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students", indexes = {
        @Index(name = "idx_student_roll_no", columnList = "student_roll_no"),
        @Index(name = "idx_student_id", columnList = "student_id"),
        @Index(name = "idx_program_year", columnList = "program,year_of_study"),
        @Index(name = "idx_course_id", columnList = "course_id"),
        @Index(name = "idx_enrollment_date", columnList = "enrollment_date"),
        @Index(name = "idx_student_status", columnList = "student_status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@ToString(exclude = "user")
public class Student {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Version
    private Long version;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "student_roll_no", unique = true, nullable = false, length = 50)
    private String studentRollNo;

    @Column(name = "student_id", unique = true, nullable = false, length = 50)
    private String studentId;

    @Column(name = "program", nullable = false, length = 100)
    private String program;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "course_id", nullable = false, length = 36)
    private String courseId;

    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @Column(name = "semester")
    private Integer semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status", length = 20)
    private StudentStatus studentStatus = StudentStatus.ACTIVE;

    @Column(name = "department_id", length = 100)
    private String departmentId;

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
    public boolean isGraduated() {
        return studentStatus == StudentStatus.GRADUATED;
    }

    public boolean isActive() {
        return studentStatus == StudentStatus.ACTIVE;
    }

    public String getFullStudentInfo() {
        return studentRollNo + " - " + program;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (studentStatus == null) {
            studentStatus = StudentStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}