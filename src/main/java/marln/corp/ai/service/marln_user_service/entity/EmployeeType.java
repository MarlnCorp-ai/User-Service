package marln.corp.ai.service.marln_user_service.entity;

import lombok.Getter;

@Getter
public enum EmployeeType {
    FULL_TIME("FULL_TIME"),
    PART_TIME("PART_TIME"),
    CONTRACT("CONTRACT"),
    INTERN("INTERN"),
    CONSULTANT("CONSULTANT");
    private final String displayName;

    EmployeeType(String displayName) {
        this.displayName = displayName;
    }

}