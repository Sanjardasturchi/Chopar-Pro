package com.example.dto.extra.region;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRegionDTO {
    private Integer orderNumber;
    @NotBlank(message = "nameUz is empty or null or completed with only spaces")
    private String nameUz;
    @NotBlank(message = "nameRu is empty or null or completed with only spaces")
    private String nameRu;
    @NotBlank(message = "nameEn is empty or null or completed with only spaces")
    private String nameEn;
}
