package com.example.dto.extra;

import com.example.enums.ProfileStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreateProfileByAdminDTO {
    @NotBlank(message = "Name is empty or null or completed with only spaces")
    private String name;
    @Email(message = "Email not valid", regexp = "^[\\\\w-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "Password is empty or null or completed with only spaces")
    private String password;
    @NotBlank(message = "Birth date is empty or null or completed with only spaces")
    private LocalDate birthDate;
    @NotBlank(message = "Status is empty or null or completed with only spaces")
    private ProfileStatus status;
}
