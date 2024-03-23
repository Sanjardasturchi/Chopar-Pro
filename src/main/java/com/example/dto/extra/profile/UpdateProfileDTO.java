package com.example.dto.extra.profile;

import com.example.enums.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProfileDTO {
    @NotBlank(message = "Name is empty or null or completed with only spaces")
    private String name;
    private ProfileStatus status;
}
