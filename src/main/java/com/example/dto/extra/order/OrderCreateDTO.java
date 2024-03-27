package com.example.dto.extra.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderCreateDTO {
    private List<ProductOrderDTO> productList;
    private String deliveredAddress;
    private String deliveredContact;
}
