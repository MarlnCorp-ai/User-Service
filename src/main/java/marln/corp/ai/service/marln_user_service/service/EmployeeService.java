package marln.corp.ai.service.marln_user_service.service;

import jakarta.transaction.Transactional;
import marln.corp.ai.service.marln_user_service.dto.BulkUploadResponseDto;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {

        EmployeeDTO createEmployee(EmployeeDTO dto);
        EmployeeDTO getEmployeeById(String id);

        @Transactional
        List<EmployeeDTO> getEmployeesByDesignation(String designation);

        List<EmployeeDTO> getAllEmployees();
        EmployeeDTO updateEmployee(String id, EmployeeUpdateDTO employeeUpdateDTO);
        void deleteEmployee(String id);
         EmployeeHierarchyDTO getEmployeeHierarchy(String employeeId);
         BulkUploadResponseDto uploadEmployeesFromCsv(MultipartFile file);
}
