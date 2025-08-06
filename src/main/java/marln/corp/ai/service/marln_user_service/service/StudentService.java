package marln.corp.ai.service.marln_user_service.service;

import marln.corp.ai.service.marln_user_service.dto.BulkUploadResponseDto;
import marln.corp.ai.service.marln_user_service.dto.StudentDTO;
import marln.corp.ai.service.marln_user_service.dto.StudentUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    public StudentDTO createStudent(StudentDTO studentDTO);
    public StudentDTO getStudentByRollNo(String rollNo);
    public List<StudentDTO> getStudentsByProgram(String program);
    public StudentDTO updateStudent(String userId, StudentUpdateDTO studentDTO);
    public void deleteStudent(String userId);
    public BulkUploadResponseDto uploadStudentsFromCsv(MultipartFile file);

    List<StudentDTO> getAllStudents();
}
