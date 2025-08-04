package marln.corp.ai.service.marln_user_service.controller;

import lombok.RequiredArgsConstructor;
import marln.corp.ai.service.marln_user_service.dto.BulkUploadResponseDto;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeHierarchyDTO;
import marln.corp.ai.service.marln_user_service.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marln.corp.ai.service.marln_user_service.dto.EmployeeDTO;
import marln.corp.ai.service.marln_user_service.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String employeeId) {
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/designation/{designation}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDesignation(@PathVariable String designation) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDesignation(designation);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable String userId,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updated = employeeService.updateEmployee(userId, employeeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String userId) {
        employeeService.deleteEmployee(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResponseDto> uploadEmployeesFromCsv(
            @RequestParam("file") MultipartFile file) {

        BulkUploadResponseDto result = employeeService.uploadEmployeesFromCsv(file);
        return ResponseEntity.ok(result);
    }
}