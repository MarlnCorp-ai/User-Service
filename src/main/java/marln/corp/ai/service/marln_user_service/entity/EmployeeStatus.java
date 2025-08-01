package marln.corp.ai.service.marln_user_service.entity;

import lombok.Getter;

@Getter
public enum EmployeeStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    TERMINATED("Terminated"),
    ON_LEAVE("On Leave"),
    SUSPENDED("Suspended");

    private final String displayName;

    EmployeeStatus(String displayName) {
        this.displayName = displayName;
    }

}