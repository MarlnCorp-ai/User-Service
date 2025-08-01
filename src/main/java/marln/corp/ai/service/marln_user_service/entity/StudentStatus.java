package marln.corp.ai.service.marln_user_service.entity;

import lombok.Getter;

@Getter
public enum StudentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    GRADUATED("Graduated"),
    SUSPENDED("Suspended"),
    TRANSFERRED("Transferred");

    private final String displayName;

    StudentStatus(String displayName) {
        this.displayName = displayName;
    }

}