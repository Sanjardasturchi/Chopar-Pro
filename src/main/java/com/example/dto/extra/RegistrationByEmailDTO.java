package com.example.dto.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationByEmailDTO {
    @NotBlank(message = "Name is empty or null or completed with only spaces")
    private String name;
    @Email(message = "Email not valid",regexp = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "Password is empty or null or completed with only spaces")
    private String password;
    private LocalDate birthDate;
}

