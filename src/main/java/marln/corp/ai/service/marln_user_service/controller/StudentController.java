package marln.corp.ai.service.marln_user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marln.corp.ai.service.marln_user_service.dto.BulkUploadResponseDto;
import marln.corp.ai.service.marln_user_service.dto.StudentDTO;
import marln.corp.ai.service.marln_user_service.dto.StudentUpdateDTO;
import marln.corp.ai.service.marln_user_service.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/roll/{rollNo}")
    public ResponseEntity<StudentDTO> getStudentByRollNo(@PathVariable String rollNo) {
        StudentDTO student = studentService.getStudentByRollNo(rollNo);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents()
    {
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        return ResponseEntity.ok(studentDTOList);
    }

    @GetMapping("/program/{program}")
    public ResponseEntity<List<StudentDTO>> getStudentsByProgram(@PathVariable String program) {
        List<StudentDTO> students = studentService.getStudentsByProgram(program);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable String userId,
            @Valid @RequestBody StudentUpdateDTO studentUpdateDTO) {
        StudentDTO updated = studentService.updateStudent(userId, studentUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String userId) {
        studentService.deleteStudent(userId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResponseDto> uploadStudentsFromCsv(
            @RequestParam("file") MultipartFile file) {

        BulkUploadResponseDto result = studentService.uploadStudentsFromCsv(file);
        return ResponseEntity.ok(result);
    }
}