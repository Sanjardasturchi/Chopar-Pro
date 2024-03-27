package com.example.dto;

import com.example.entity.OrderBucketEntity;
import com.example.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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
    private List<OrderBucketDTO> productList;
}
