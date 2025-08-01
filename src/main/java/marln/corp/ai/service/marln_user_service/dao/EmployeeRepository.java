package marln.corp.ai.service.marln_user_service.dao;

import marln.corp.ai.service.marln_user_service.entity.Employee;
import marln.corp.ai.service.marln_user_service.entity.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByReportingManagerId(String managerId);
    Optional<Employee> findByEmployeeId(String employeeId);
    List<Employee> findByDesignation(String designation);
    List<Employee> findByEmployeeStatus(EmployeeStatus status);

    @Query("SELECT e FROM Employee e JOIN FETCH e.user WHERE e.employeeId = ?1")
    Optional<Employee> findByEmployeeIdWithUser(String employeeId);

    @Query("SELECT e FROM Employee e JOIN FETCH e.user WHERE e.designation = ?1")
    List<Employee> findByDesignationWithUser(String designation);

    boolean existsByEmployeeId(String employeeId);
}