package marln.corp.ai.service.marln_user_service.service;

import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;

import java.util.List;

public interface EmployeeService {

        EmployeeDTO createEmployee(EmployeeDTO dto);
        EmployeeDTO getEmployeeById(String id);
        List<EmployeeDTO> getAllEmployees();
        EmployeeDTO updateEmployee(String id, EmployeeDTO dto);
        void deleteEmployee(String id);
         EmployeeHierarchyDTO getEmployeeHierarchy(String employeeId);
}
