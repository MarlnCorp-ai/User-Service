package marln.corp.ai.service.marln_user_service.utils;

import marln.corp.ai.service.marln_user_service.dto.StudentCsvDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeCsvDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    private static final String CSV_TYPE = "text/csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Check if file is CSV
    public static boolean hasCSVFormat(MultipartFile file) {
        return CSV_TYPE.equals(file.getContentType()) ||
                file.getOriginalFilename().endsWith(".csv");
    }

    // Convert CSV to Student DTOs
    public static List<StudentCsvDTO> csvToStudents(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            List<StudentCsvDTO> students = new ArrayList<>();
            String line;
            boolean isHeader = true;

            while ((line = fileReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                String[] data = line.split(",");
                if (data.length >= 12) { // Minimum required fields
                    StudentCsvDTO student = StudentCsvDTO.builder()
                            .email(data[0].trim())
                            .userFirstName(data[1].trim())
                            .userMiddleName(data[2].trim().isEmpty() ? null : data[2].trim())
                            .userLastName(data[3].trim())
                            .password(data[4].trim())
                            .createdBy(data[5].trim())
                            .studentRollNo(data[6].trim())
                            .studentId(data[7].trim())
                            .program(data[8].trim())
                            .yearOfStudy(parseInteger(data[9]))
                            .enrollmentDate(parseDate(data[10]))
                            .courseId(data[11].trim())
                            .academicYear(data.length > 12 ? data[12].trim() : null)
                            .semester(data.length > 13 ? parseInteger(data[13]) : null)
                            .departmentId(data.length > 14 ? data[14].trim() : null)
                            .build();
                    students.add(student);
                }
            }
            return students;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    // Convert CSV to Employee DTOs
    public static List<EmployeeCsvDTO> csvToEmployees(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            List<EmployeeCsvDTO> employees = new ArrayList<>();
            String line;
            boolean isHeader = true;

            while ((line = fileReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                String[] data = line.split(",");
                if (data.length >= 10) { // Minimum required fields
                    EmployeeCsvDTO employee = EmployeeCsvDTO.builder()
                            .email(data[0].trim())
                            .userFirstName(data[1].trim())
                            .userMiddleName(data[2].trim().isEmpty() ? null : data[2].trim())
                            .userLastName(data[3].trim())
                            .password(data[4].trim())
                            .createdBy(data[5].trim())
                            .employeeId(data[6].trim())
                            .designation(data[7].trim())
                            .hireDate(parseDate(data[8]))
                            .reportingManagerId(data.length > 10 ? data[10].trim() : null)
                            .reportingManagerName(data.length > 11 ? data[11].trim() : null)
                            .jobCity(data.length > 12 ? data[12].trim() : null)
                            .workLocation(data.length > 13 ? data[13].trim() : null)
                            .phoneNumber(data.length > 14 ? data[14].trim() : null)
                            .emergencyContact(data.length > 15 ? data[15].trim() : null)
                            .build();
                    employees.add(employee);
                }
            }
            return employees;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    // Helper methods
    private static Integer parseInteger(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static LocalDate parseDate(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : LocalDate.parse(value.trim(), DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    private static BigDecimal parseBigDecimal(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}