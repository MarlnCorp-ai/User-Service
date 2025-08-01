package marln.corp.ai.service.marln_user_service.service;

import marln.corp.ai.service.marln_user_service.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    public StudentDTO createStudent(StudentDTO studentDTO);
    public StudentDTO getStudentByRollNo(String rollNo);
    public List<StudentDTO> getStudentsByProgram(String program);
    public StudentDTO updateStudent(String userId, StudentDTO studentDTO);
    public void deleteStudent(String userId);
}
