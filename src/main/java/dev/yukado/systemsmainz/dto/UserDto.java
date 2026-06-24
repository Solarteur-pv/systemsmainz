package dev.yukado.systemsmainz.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserDto {
    private String email;
    private String password;
    private String role;


    public UserDto(String email, String password, String role) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
