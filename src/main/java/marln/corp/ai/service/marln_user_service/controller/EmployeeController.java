package marln.corp.ai.service.marln_user_service.controller;

import lombok.RequiredArgsConstructor;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;
import marln.corp.ai.service.marln_user_service.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public EmployeeDTO create(@RequestBody EmployeeDTO dto) {
        return service.createEmployee(dto);
    }

    @GetMapping("/{id}")
    public EmployeeDTO get(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable String id, @RequestBody EmployeeDTO dto) {
        return service.updateEmployee(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteEmployee(id);
    }

    @GetMapping("/{id}/hierarchy")
    public EmployeeHierarchyDTO getHierarchy(@PathVariable String id) {
        return service.getEmployeeHierarchy(id);
    }
}