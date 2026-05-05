package net.engineeringdigest.journalApp.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String name;      // ✅ added
    private String email;     // ✅ instead of username
    private String password;
    private String role;
}

