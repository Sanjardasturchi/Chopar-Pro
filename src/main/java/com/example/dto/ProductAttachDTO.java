package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
public class ProductAttachDTO {
    private Integer id;
    private Integer productId;
    private UUID attachId;
    private LocalDateTime createdDate;
}
