package marln.corp.ai.service.marln_user_service.entity;

import lombok.Getter;

@Getter
public enum EmployeeType {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    CONTRACT("Contract"),
    INTERN("Intern"),
    CONSULTANT("Consultant");

    private final String displayName;

    EmployeeType(String displayName) {
        this.displayName = displayName;
    }

}