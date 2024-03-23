package com.example.dto.extra.profile;

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
    @Email(message = "Email not valid")
    private String email;
    @NotBlank(message = "Password is empty or null or completed with only spaces")
    private String password;
    private LocalDate birthDate;
    private ProfileStatus status;
}
