package marln.corp.ai.service.marln_user_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marln.corp.ai.service.marln_user_service.assembler.EmployeeMapper;
import marln.corp.ai.service.marln_user_service.assembler.UserMapper;
import marln.corp.ai.service.marln_user_service.dao.EmployeeRepository;
import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;
import marln.corp.ai.service.marln_user_service.dto.UserDTO;
import marln.corp.ai.service.marln_user_service.entity.Employee;
import marln.corp.ai.service.marln_user_service.entity.User;
import marln.corp.ai.service.marln_user_service.entity.UserType;
import marln.corp.ai.service.marln_user_service.exception.EmployeeNotFoundException;
import marln.corp.ai.service.marln_user_service.exception.ExternalServiceException;
import marln.corp.ai.service.marln_user_service.restcall.RestCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RestCall restCall;



    private EmployeeDTO mapToDTO(Employee emp) {
        return EmployeeDTO.builder()
                .reportingManagerId(emp.getReportingManagerId())
                .reportingManagerName(emp.getReportingManagerName())
                .jobCity(emp.getJobCity())
                .designation(emp.getDesignation())
                .build();
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .reportingManagerId(dto.getReportingManagerId())
                .reportingManagerName(dto.getReportingManagerName())
                .jobCity(dto.getJobCity())
                .designation(dto.getDesignation())
                .build();
    }
//
//    @Override
//    public EmployeeDTO createEmployee(EmployeeDTO dto) {
//        return mapToDTO(repository.save(mapToEntity(dto)));
//    }

    @Transactional
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating employee with ID: {}", employeeDTO.getEmployeeId());

        // Validation
        if (employeeRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Employee already exists with ID: " + employeeDTO.getEmployeeId());
        }

        if (userRepository.existsByEmail(employeeDTO.getUser().getEmail())) {
            throw new IllegalArgumentException("User already exists with email: " + employeeDTO.getUser().getEmail());
        }

        // ===== STEP 1: CREATE AND SAVE USER FIRST =====
        UserDTO userDTO = employeeDTO.getUser();
        userDTO.setUserType(UserType.EMPLOYEE);
        User user = userMapper.userDTOToUser(userDTO);
        user.setId(UUID.randomUUID().toString());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsDeleted(false);

        User savedUser = userRepository.save(user); // First save operation
        log.info("User created with ID: {}", savedUser.getId());

        // ===== STEP 2: CREATE AND SAVE EMPLOYEE SECOND =====
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee.setUserId(savedUser.getId()); // Link to saved user
        employee.setUser(savedUser); // Set relationship for @MapsId

        Employee savedEmployee = employeeRepository.save(employee); // Second save operation
        log.info("Employee created successfully with user ID: {}", savedEmployee.getUserId());
        //Assign roles to user
        try {
            restCall.assignRoles(savedUser.getId(), userDTO.getUserRole(), userDTO.getUserPermissions());
        } catch (Exception ex) {
            userRepository.delete(user);
            employeeRepository.delete(employee);
            throw new ExternalServiceException("marln-rbac-service", "Failed to assign roles to user: " + ex.getMessage(), ex);
        }
        // Return DTO with both user and employee data
        EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(savedEmployee);
        result.setUser(userMapper.userToUserDTO(savedUser));


        return result;
    }

    @Transactional
    @Override
    public EmployeeDTO getEmployeeById(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeIdWithUser(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Transactional
    @Override
    public EmployeeDTO updateEmployee(String userId, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found with user ID: " + userId));

        employeeMapper.updateEmployeeEntityFromDto(employeeDTO, existingEmployee);
        existingEmployee.setUpdatedAt(LocalDateTime.now());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.employeeToEmployeeDTO(updatedEmployee);
    }

    @Transactional
    @Override
    public List<EmployeeDTO> getEmployeesByDesignation(String designation) {
        List<Employee> employees = employeeRepository.findByDesignationWithUser(designation);
        return employeeMapper.toEmployeeDTOList(employees);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void deleteEmployee(String userId) {
        if (!employeeRepository.existsById(userId)) {
            throw new RuntimeException("Employee not found with user ID: " + userId);
        }

        employeeRepository.deleteById(userId);
        // User will be deleted by cascade
        log.info("Employee deleted successfully with user ID: {}", userId);
    }
    @Override
    public EmployeeHierarchyDTO getEmployeeHierarchy(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        return buildHierarchy(employee);
    }

    private EmployeeHierarchyDTO buildHierarchy(Employee employee) {
        EmployeeHierarchyDTO dto = EmployeeHierarchyDTO.builder()
                .designation(employee.getDesignation())
                .jobCity(employee.getJobCity())
                .reportingManagerId(employee.getReportingManagerId())
                .reportingManagerName(employee.getReportingManagerName())
                .build();

        // Upward manager chain
        if (employee.getReportingManagerId() != null) {
            employeeRepository.findByEmployeeId(employee.getReportingManagerId())
                    .ifPresent(manager -> dto.setManager(buildHierarchy(manager)));
        }

        // Downward direct reports
        List<Employee> directReports = employeeRepository.findByReportingManagerId(employee.getEmployeeId());
        if (!directReports.isEmpty()) {
            List<EmployeeHierarchyDTO> reportDTOs = directReports.stream()
                    .map(this::buildHierarchy) // recursively include their reports
                    .toList();
            dto.setDirectReports(reportDTOs);
        }

        return dto;
    }
}
