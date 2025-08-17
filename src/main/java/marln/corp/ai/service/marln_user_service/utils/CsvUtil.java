package marln.corp.ai.service.marln_user_service.utils;

import marln.corp.ai.service.marln_user_service.dto.StudentCsvDTO;
import marln.corp.ai.service.marln_user_service.dto.EmployeeCsvDTO;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
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
            int lineNumber = 0;

            while ((line = fileReader.readLine()) != null) {
                lineNumber++;
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                String[] data = parseCsvLine(line);
                log.debug("Student CSV Line {}: {} fields", lineNumber, data.length);
                
                if (data.length >= 17) { // Updated minimum required fields including userRole and userPermissions
                    StudentCsvDTO student = StudentCsvDTO.builder()
                            .email(data[0].trim())
                            .userFirstName(data[1].trim())
                            .userMiddleName(data[2].trim().isEmpty() ? null : data[2].trim())
                            .userLastName(data[3].trim())
                            .password(data[4].trim())
                            .createdBy(data[5].trim())
                            .userRole(data[6].trim())
                            .userPermissions(parsePermissions(data[7]))
                            .studentRollNo(data[8].trim())
                            .studentId(data[9].trim())
                            .program(data[10].trim())
                            .yearOfStudy(parseInteger(data[11]))
                            .enrollmentDate(parseDate(data[12]))
                            .courseId(data[13].trim())
                            .academicYear(data[14].trim())
                            .semester(parseInteger(data[15]))
                            .departmentId(data[16].trim())
                            .build();
                    students.add(student);
                } else {
                    log.warn("Student CSV Line {}: Insufficient fields ({}), expected at least 17", lineNumber, data.length);
                }
            }
            log.info("Parsed {} student records from CSV", students.size());
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
            int lineNumber = 0;

            while ((line = fileReader.readLine()) != null) {
                lineNumber++;
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

                String[] data = parseCsvLine(line);
                log.debug("Employee CSV Line {}: {} fields", lineNumber, data.length);
                
                if (data.length >= 17) { // Updated to expect 17 fields including userRole and userPermissions
                    EmployeeCsvDTO employee = EmployeeCsvDTO.builder()
                            .email(data[0].trim())
                            .userFirstName(data[1].trim())
                            .userMiddleName(data[2].trim().isEmpty() ? null : data[2].trim())
                            .userLastName(data[3].trim())
                            .password(data[4].trim())
                            .createdBy(data[5].trim())
                            .userRole(data[6].trim())
                            .userPermissions(parsePermissions(data[7]))
                            .employeeId(data[8].trim())
                            .designation(data[9].trim())
                            .hireDate(parseDate(data[10]))
                            .reportingManagerId(data[11].trim())
                            .reportingManagerName(data[12].trim())
                            .jobCity(data[13].trim())
                            .workLocation(data[14].trim())
                            .phoneNumber(data[15].trim())
                            .emergencyContact(data[16].trim())
                            .build();
                    employees.add(employee);
                } else {
                    log.warn("Employee CSV Line {}: Insufficient fields ({}), expected at least 17", lineNumber, data.length);
                }
            }
            log.info("Parsed {} employee records from CSV", employees.size());
            return employees;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    // Parse CSV line with proper handling of quoted fields
    private static String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        result.add(current.toString());
        return result.toArray(new String[0]);
    }

    // Parse permissions string into List
    private static List<String> parsePermissions(String permissionsStr) {
        if (permissionsStr == null || permissionsStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Remove quotes if present
        String cleanStr = permissionsStr.trim();
        if (cleanStr.startsWith("\"") && cleanStr.endsWith("\"")) {
            cleanStr = cleanStr.substring(1, cleanStr.length() - 1);
        }
        
        // Split by comma and trim each permission
        return Arrays.stream(cleanStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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