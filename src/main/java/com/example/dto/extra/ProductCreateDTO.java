package com.example.dto.extra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductCreateDTO {
    @NotNull(message = "OrderNumber is null")
    private Integer orderNumber;
    @NotBlank(message = "NameUz is empty or null or completed with only spaces")
    private String nameUz;
    @NotBlank(message = "NameRu is empty or null or completed with only spaces")
    private String nameRu;
    @NotBlank(message = "NameEn is empty or null or completed with only spaces")
    private String nameEn;
    @NotBlank(message = "DescriptionUz is empty or null or completed with only spaces")
    private String descriptionUz;
    @NotBlank(message = "DescriptionRu is empty or null or completed with only spaces")
    private String descriptionRu;
    @NotBlank(message = "DescriptionEn is empty or null or completed with only spaces")
    private String descriptionEn;
    @NotNull(message = "Price is null")
    private Double price;
    @NotNull(message = "AttachList is or null")
    private List<String> attachList;
    @NotNull(message = "CategoryId is null")
    private Integer categoryId;
}
