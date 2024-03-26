package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderBucketDTO {
    private Integer id;
    private UUID productId;
    private Integer orderId;
    private Double amount;
    private Double price;
    private LocalDateTime createdDate;
}
