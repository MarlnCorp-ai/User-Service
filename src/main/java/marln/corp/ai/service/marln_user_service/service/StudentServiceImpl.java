package marln.corp.ai.service.marln_user_service.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marln.corp.ai.service.marln_user_service.assembler.StudentMapper;
import marln.corp.ai.service.marln_user_service.assembler.UserMapper;
import marln.corp.ai.service.marln_user_service.dao.StudentRepository;
import marln.corp.ai.service.marln_user_service.dao.UserRepository;
import marln.corp.ai.service.marln_user_service.dto.*;
import marln.corp.ai.service.marln_user_service.entity.*;
import marln.corp.ai.service.marln_user_service.exception.ExternalServiceException;
import marln.corp.ai.service.marln_user_service.exception.StudentNotFoundException;
import marln.corp.ai.service.marln_user_service.exception.UserNotFoundException;
import marln.corp.ai.service.marln_user_service.restcall.RestCall;
import marln.corp.ai.service.marln_user_service.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RestCall restCall;
    @Autowired
    HttpServletRequest request;

    @Transactional
    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        String loggedInUser = request.getHeader("userId");
        log.info("Creating student with roll number: {}", studentDTO.getStudentRollNo());

        // Validation
        if (studentRepository.existsByStudentRollNo(studentDTO.getStudentRollNo())) {
            throw new IllegalArgumentException("Student already exists with roll number: " + studentDTO.getStudentRollNo());
        }

        if (userRepository.existsByEmail(studentDTO.getUser().getEmail())) {
            throw new IllegalArgumentException("User already exists with email: " + studentDTO.getUser().getEmail());
        }

        // ===== STEP 1: CREATE AND SAVE USER FIRST =====
        UserDTO userDTO = studentDTO.getUser();
        User user = userMapper.userDTOToUser(userDTO);
        user.setUserType(UserType.STUDENT);
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setCreatedBy(loggedInUser);
        user.setUpdatedBy(loggedInUser);

        User savedUser = userRepository.save(user); // First save operation
        log.info("User created with ID: {}", savedUser.getId());

        // ===== STEP 2: CREATE AND SAVE STUDENT SECOND =====
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        student.setUserId(savedUser.getId()); // Link to saved user
        student.setCreatedBy(loggedInUser);
        student.setUpdatedBy(loggedInUser);
        student.setUser(savedUser); // Set relationship for @MapsId
        student.setCreatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(student); // Second save operation
        log.info("Student created successfully with user ID: {}", savedStudent.getUserId());
        //Assign roles to user
        try {
            restCall.assignRoles(savedUser.getId(), userDTO.getUserRole(), userDTO.getUserPermissions());
        } catch (Exception ex) {
            log.info("Inside exception while calling rbac-service");
            userRepository.delete(user);
            studentRepository.delete(student);
            throw new ExternalServiceException("marln-rbac-service", "Failed to assign roles to user: " + ex.getMessage(), ex);
        }
        // Return DTO with both user and student data
        StudentDTO result = studentMapper.studentToStudentDTO(savedStudent);
        result.setUser(userMapper.userToUserDTO(savedUser));

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public StudentDTO getStudentByRollNo(String rollNo) {
        Student student = studentRepository.findByStudentRollNoWithUser(rollNo)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with roll number: " + rollNo));
        return studentMapper.studentToStudentDTO(student);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDTO> getStudentsByProgram(String program) {
        List<Student> students = studentRepository.findByProgramWithUser(program);
        return studentMapper.toStudentDTOList(students);
    }

    @Transactional
    @Override
    public StudentDTO updateStudent(String userId, StudentUpdateDTO studentDTO) {
        log.info("Update request body {}",studentDTO.toString());
        Student existingStudent = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with user ID: " + userId));

        studentMapper.updateStudentEntityFromDto(studentDTO, existingStudent);
        existingStudent.setUpdatedAt(LocalDateTime.now());
        log.info("Existing Student after updated values : " + existingStudent.toString());

        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.studentToStudentDTO(updatedStudent);
    }

    @Transactional
    @Override
    public void deleteStudent(String userId) {
        if (!studentRepository.existsById(userId)) {
            throw new StudentNotFoundException("Student not found for deletion with user ID: " + userId);
        }

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found for deletion with user ID: " + userId);
        }
        studentRepository.deleteById(userId);
        userRepository.deleteById(userId);
        // User will be deleted by cascade
        log.info("Student deleted successfully with user ID: {}", userId);
    }

    @Override
    @Transactional
    public BulkUploadResponseDto uploadStudentsFromCsv(MultipartFile file) {
        log.info("Starting CSV upload for students, file: {}", file.getOriginalFilename());

        // Validate file format
        if (!CsvUtil.hasCSVFormat(file)) {
            throw new IllegalArgumentException("File must be a CSV format");
        }

        List<String> successfulEmails = new ArrayList<>();
        List<String> failedRecords = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try {
            // Parse CSV to DTOs
            List<StudentCsvDTO> csvStudents = CsvUtil.csvToStudents(file.getInputStream());

            for (StudentCsvDTO csvStudent : csvStudents) {
                try {
                    // ===== STEP 1: CREATE AND SAVE USER FIRST =====
                    if (userRepository.existsByEmail(csvStudent.getEmail())) {
                        failedRecords.add(csvStudent.getEmail());
                        errors.add("User already exists with email: " + csvStudent.getEmail());
                        continue;
                    }

                    if (studentRepository.existsByStudentRollNo(csvStudent.getStudentRollNo())) {
                        failedRecords.add(csvStudent.getEmail());
                        errors.add("Student already exists with roll number: " + csvStudent.getStudentRollNo());
                        continue;
                    }

                    // Create User entity
                    User user = new User();
                    user.setEmail(csvStudent.getEmail());
                    user.setUserFirstName(csvStudent.getUserFirstName());
                    user.setUserMiddleName(csvStudent.getUserMiddleName());
                    user.setUserLastName(csvStudent.getUserLastName());
                    user.setPasswordHash(passwordEncoder.encode(csvStudent.getPassword()));
                    user.setUserType(UserType.STUDENT);
                    user.setCreatedAt(LocalDateTime.now());
                    user.setCreatedBy(csvStudent.getCreatedBy());
                    user.setUpdatedBy(csvStudent.getCreatedBy());
                    user.setIsActive(true);
                    user.setIsDeleted(false);

                    User savedUser = userRepository.save(user); // First save operation

                    // ===== STEP 2: CREATE AND SAVE STUDENT SECOND =====
                    Student student = studentMapper.studentCSVDTOToStudent(csvStudent);
                    student.setUserId(savedUser.getId()); // Link to saved user
                    student.setUser(savedUser); // Set relationship for @MapsId
                    student.setCreatedAt(LocalDateTime.now());

                    studentRepository.save(student); // Second save operation

                    successfulEmails.add(csvStudent.getEmail());
                    //Assign roles to user
                    try {
                        restCall.assignRoles(savedUser.getId(), csvStudent.getUserRole(), csvStudent.getUserPermissions());
                    } catch (Exception ex) {
                        userRepository.delete(user);
                        studentRepository.delete(student);
                        throw new ExternalServiceException("marln-rbac-service", "Failed to assign roles to user: " + ex.getMessage(), ex);
                    }

                    log.info("Successfully created student: {}", csvStudent.getEmail());

                } catch (Exception e) {
                    failedRecords.add(csvStudent.getEmail());
                    errors.add("Error processing " + csvStudent.getEmail() + ": " + e.getMessage());
                    log.error("Failed to create student: {}, Error: {}", csvStudent.getEmail(), e.getMessage());
                }
            }

            return BulkUploadResponseDto.builder()
                    .message("CSV upload completed")
                    .totalRecords(csvStudents.size())
                    .successfulRecords(successfulEmails.size())
                    .failedRecords(failedRecords.size())
                    .successfulEmails(successfulEmails)
                    .failedRecordsList(failedRecords)
                    .errors(errors)
                    .build();

        } catch (Exception e) {
            log.error("Failed to process CSV file: {}", e.getMessage());
            throw new RuntimeException("Failed to process CSV file: " + e.getMessage());
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        log.info("Inside getAllStudents");
        List<Student>studentList = studentRepository.findAll();
        if(!studentList.isEmpty())
        {
            throw new StudentNotFoundException("No students Enrolled");
        }
        log.info("Student list is not empty");
        return studentMapper.toStudentDTOList(studentList);
    }
}