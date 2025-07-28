package marln.corp.ai.service.marln_user_service.service;

import lombok.RequiredArgsConstructor;
import marln.corp.ai.service.marln_user_service.dao.EmployeeRepository;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;
import marln.corp.ai.service.marln_user_service.entity.Employee;
import marln.corp.ai.service.marln_user_service.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private EmployeeDTO mapToDTO(Employee emp) {
        return EmployeeDTO.builder()
                .id(emp.getId())
                .employeeName(emp.getEmployeeName())
                .reportingManagerId(emp.getReportingManagerId())
                .reportingManagerName(emp.getReportingManagerName())
                .jobCity(emp.getJobCity())
                .designation(emp.getDesignation())
                .build();
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .id(dto.getId())
                .employeeName(dto.getEmployeeName())
                .reportingManagerId(dto.getReportingManagerId())
                .reportingManagerName(dto.getReportingManagerName())
                .jobCity(dto.getJobCity())
                .designation(dto.getDesignation())
                .build();
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        return mapToDTO(repository.save(mapToEntity(dto)));
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(String id, EmployeeDTO dto) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        emp.setEmployeeName(dto.getEmployeeName());
        emp.setReportingManagerId(dto.getReportingManagerId());
        emp.setReportingManagerName(dto.getReportingManagerName());
        emp.setJobCity(dto.getJobCity());
        emp.setDesignation(dto.getDesignation());

        return mapToDTO(repository.save(emp));
    }

    @Override
    public void deleteEmployee(String id) {
        // Check if employee exists before deleting
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public EmployeeHierarchyDTO getEmployeeHierarchy(String employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        return buildHierarchy(employee);
    }

    private EmployeeHierarchyDTO buildHierarchy(Employee employee) {
        EmployeeHierarchyDTO dto = EmployeeHierarchyDTO.builder()
                .id(employee.getId())
                .employeeName(employee.getEmployeeName())
                .designation(employee.getDesignation())
                .jobCity(employee.getJobCity())
                .reportingManagerId(employee.getReportingManagerId())
                .reportingManagerName(employee.getReportingManagerName())
                .build();

        // Upward manager chain
        if (employee.getReportingManagerId() != null) {
            repository.findById(employee.getReportingManagerId())
                    .ifPresent(manager -> dto.setManager(buildHierarchy(manager)));
        }

        // Downward direct reports
        List<Employee> directReports = repository.findByReportingManagerId(employee.getId());
        if (!directReports.isEmpty()) {
            List<EmployeeHierarchyDTO> reportDTOs = directReports.stream()
                    .map(this::buildHierarchy) // recursively include their reports
                    .toList();
            dto.setDirectReports(reportDTOs);
        }

        return dto;
    }
}
