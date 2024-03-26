package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAttachDTO {
    private Integer id;
    private Integer productId;
    private UUID attachId;
    private LocalDateTime createdDate;
}
