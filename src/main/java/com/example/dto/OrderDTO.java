package com.example.dto;

import com.example.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Integer id;
    private UUID profileId;
    private Double amount;//(total-amount)
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String deliveredAddress;
    private String deliveredContact;
}
