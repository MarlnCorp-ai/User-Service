package marln.corp.ai.service.marln_user_service.dao;

import marln.corp.ai.service.marln_user_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByReportingManagerId(String managerId);
}