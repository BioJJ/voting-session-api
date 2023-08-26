package io.github.biojj.enums;

public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}