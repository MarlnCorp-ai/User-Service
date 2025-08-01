package marln.corp.ai.service.marln_user_service.dao;
import marln.corp.ai.service.marln_user_service.entity.Student;
import marln.corp.ai.service.marln_user_service.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByStudentRollNo(String studentRollNo);
    Optional<Student> findByStudentId(String studentId);
    List<Student> findByProgram(String program);
    List<Student> findByStudentStatus(StudentStatus status);

    @Query("SELECT s FROM Student s JOIN FETCH s.user WHERE s.studentRollNo = ?1")
    Optional<Student> findByStudentRollNoWithUser(String studentRollNo);

    @Query("SELECT s FROM Student s JOIN FETCH s.user WHERE s.program = ?1")
    List<Student> findByProgramWithUser(String program);

    boolean existsByStudentRollNo(String studentRollNo);
    boolean existsByStudentId(String studentId);
}