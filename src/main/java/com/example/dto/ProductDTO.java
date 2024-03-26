package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Integer id;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String description;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private UUID prtId;
    private Double price;
    private Double discountPrice;
    private Integer viewCount;
    private Integer categoryId;
    private List<AttachDTO> attachList;
}
