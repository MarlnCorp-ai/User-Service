package marln.corp.ai.service.marln_user_service.assembler;

import marln.corp.ai.service.marln_user_service.dto.EmployeeCsvDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    @Autowired
    private ModelMapper mapper;

    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO)
    {
        return mapper.map(employeeDTO, Employee.class);
    }

    public EmployeeDTO employeeToEmployeeDTO(Employee employee)
    {
        return mapper.map(employee, EmployeeDTO.class);
    }
    public void updateEmployeeEntityFromDto(EmployeeDTO employeeDTO, Employee existingEmployee)
    {
        mapper.map(employeeDTO,existingEmployee);
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employee> employeeList)
    {
        // Map to DTOs without triggering lazy loading
        return employeeList.stream()
                .map(employee -> {
                    EmployeeDTO dto = mapper.map(employee, EmployeeDTO.class);
                    dto.setUser(null); // Explicitly exclude user data
                    return dto;
                })
                .collect(Collectors.toList());

    }
    public Employee employeeCSVDTOToEmployee(EmployeeCsvDTO employeeCsvDTO)
    {
        return mapper.map(employeeCsvDTO, Employee.class);
    }
}
