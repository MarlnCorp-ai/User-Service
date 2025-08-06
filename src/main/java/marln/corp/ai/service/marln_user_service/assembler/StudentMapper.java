package marln.corp.ai.service.marln_user_service.assembler;

import com.netflix.discovery.converters.Auto;
import marln.corp.ai.service.marln_user_service.dto.*;
import marln.corp.ai.service.marln_user_service.entity.Employee;
import marln.corp.ai.service.marln_user_service.entity.Student;
import marln.corp.ai.service.marln_user_service.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class StudentMapper {

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserMapper userMapper;

    public Student studentDTOToStudent(StudentDTO studentDTO)
    {
        return mapper.map(studentDTO, Student.class);
    }

    public StudentDTO studentToStudentDTO(Student student)
    {

        return mapper.map(student, StudentDTO.class);
    }

    public void updateStudentEntityFromDto(StudentUpdateDTO studentDTO, Student existingStudent)
    {
        mapper.map(studentDTO,existingStudent);
    }

    public List<StudentDTO> toStudentDTOList(List<Student> studentList)
    {
        // Map to DTOs without triggering lazy loading
        return studentList.stream()
                .map(student -> {
                    StudentDTO dto = mapper.map(student, StudentDTO.class);
                    if(Objects.nonNull(student.getUser())) {
                        UserDTO userDTO = userMapper.userToUserDTO(student.getUser());
                        dto.setUser(userDTO);
                    }
                    else {
                        dto.setUser(null); // Explicitly exclude user data
                    }
                    return dto;
                })
                .collect(Collectors.toList());

    }

    public Student studentCSVDTOToStudent(StudentCsvDTO studentCsvDTO)
    {
        return mapper.map(studentCsvDTO, Student.class);
    }


}
