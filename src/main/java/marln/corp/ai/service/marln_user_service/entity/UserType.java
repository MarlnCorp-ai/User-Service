package marln.corp.ai.service.marln_user_service.entity;

import lombok.Getter;

@Getter
public enum UserType {
    STUDENT("Student"),
    EMPLOYEE("Employee"),
    ADMIN("Administrator");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public static UserType fromString(String value) {
        for (UserType type : UserType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid UserType: " + value);
    }
}