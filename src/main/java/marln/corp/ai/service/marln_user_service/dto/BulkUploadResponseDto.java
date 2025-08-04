package marln.corp.ai.service.marln_user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkUploadResponseDto {
    private String message;
    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;
    private List<String> successfulEmails;
    private List<String> failedRecordsList;
    private List<String> errors;
}