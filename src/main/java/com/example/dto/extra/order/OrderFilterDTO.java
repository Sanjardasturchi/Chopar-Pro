package com.example.dto.extra.order;

import com.example.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class OrderFilterDTO {
    private Integer productId;
    private UUID profileId;
    private OrderStatus status;
    private LocalDate orderDate;
}
